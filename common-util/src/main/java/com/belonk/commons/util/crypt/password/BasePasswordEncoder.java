package com.belonk.commons.util.crypt.password;

import com.belonk.commons.util.crypt.Hex;
import com.belonk.commons.util.crypt.Utf8;
import com.belonk.commons.util.crypt.keygen.BytesKeyGenerator;
import com.belonk.commons.util.crypt.keygen.KeyGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.belonk.commons.util.crypt.util.EncodingUtils.concatenate;
import static com.belonk.commons.util.crypt.util.EncodingUtils.subArray;

/**
 * Created by sun on 2018/11/5.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.2
 */
public abstract class BasePasswordEncoder implements PasswordEncoder {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private static Logger log = LoggerFactory.getLogger(BasePasswordEncoder.class);

    private static final int DEFAULT_ITERATIONS = 1024;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private final Digester digester;

    private final byte[] secret;

    private final BytesKeyGenerator saltGenerator;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    protected BasePasswordEncoder(String algorithm, CharSequence secret) {
        this.digester = new Digester(algorithm, DEFAULT_ITERATIONS);
        this.secret = Utf8.encode(secret);
        this.saltGenerator = KeyGenerators.secureRandom();
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Override
    public String encode(CharSequence rawPassword) {
        return encode(rawPassword, saltGenerator.generateKey());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = decode(encodedPassword);
        byte[] salt = subArray(digested, 0, saltGenerator.getKeyLength());
        return matches(digested, digest(rawPassword, salt));
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Protected Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


    
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Property accessors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private String encode(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digest(rawPassword, salt);
        return new String(Hex.encode(digest));
    }

    private byte[] digest(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digester.digest(concatenate(salt, secret, Utf8.encode(rawPassword)));
        return concatenate(salt, digest);
    }

    private byte[] decode(CharSequence encodedPassword) {
        return Hex.decode(encodedPassword);
    }

    /**
     * Constant time comparison to prevent against timing attacks.
     */
    private boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expected.length; i++) {
            result |= expected[i] ^ actual[i];
        }
        return result == 0;
    }
     
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}