package com.belonk.commons.util.crypt.password;

/**
 * A {@code PasswordEncoder} implementation that uses SHA-256 hashing with 1024 iterations and a
 * random 8-byte random salt value. It uses an additional system-wide secret value to provide additional protection.
 * <p>
 * The digest algorithm is invoked on the concatenated bytes of the salt, secret and password.
 * <p>
 * If you are developing a new system, {@link com.belonk.commons.util.crypt.bcrypt.BCryptPasswordEncoder} is
 * a better choice both in terms of security and interoperability with other languages.
 *
 * @author Keith Donald
 * @author Luke Taylor
 * @since 1.2
 */
public final class SHAPasswordEncoder extends BasePasswordEncoder {

    /**
     * Constructs a standard password encoder with no additional secret value.
     */
    public SHAPasswordEncoder() {
        this("");
    }

    /**
     * Constructs a standard password encoder with a secret value which is also included in the
     * password hash.
     *
     * @param secret the secret key used in the encoding process (should not be shared)
     */
    public SHAPasswordEncoder(CharSequence secret) {
        super("SHA-256", secret);
    }
}