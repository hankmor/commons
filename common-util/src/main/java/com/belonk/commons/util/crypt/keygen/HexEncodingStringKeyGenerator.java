package com.belonk.commons.util.crypt.keygen;

import com.belonk.commons.util.crypt.Hex;

/**
 * A StringKeyGenerator that generates hex-encoded String keys.
 * Delegates to a {@link BytesKeyGenerator} for the actual key generation.
 *
 * @author Keith Donald
 */
final class HexEncodingStringKeyGenerator implements StringKeyGenerator {

    private final BytesKeyGenerator keyGenerator;

    public HexEncodingStringKeyGenerator(BytesKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public String generateKey() {
        return new String(Hex.encode(keyGenerator.generateKey()));
    }
}