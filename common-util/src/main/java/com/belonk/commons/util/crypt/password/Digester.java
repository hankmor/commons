package com.belonk.commons.util.crypt.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper for working with the MessageDigest API.
 * <p>
 * Performs the configured number of iterations of the hashing algorithm per digest to aid
 * in protecting against brute force attacks.
 *
 * @author Keith Donald
 * @author Luke Taylor
 * @since 1.2
 */
final class Digester {

    private final String algorithm;

    private final int iterations;

    /**
     * Create a new Digester.
     *
     * @param algorithm  the digest algorithm; for example, "SHA-1" or "SHA-256".
     * @param iterations the number of times to apply the digest algorithm to the input
     */
    public Digester(String algorithm, int iterations) {
        // eagerly validate the algorithm
        createDigest(algorithm);
        this.algorithm = algorithm;
        this.iterations = iterations;
    }

    public byte[] digest(byte[] value) {
        MessageDigest messageDigest = createDigest(algorithm);
        for (int i = 0; i < iterations; i++) {
            value = messageDigest.digest(value);
        }
        return value;
    }

    private static MessageDigest createDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such hashing algorithm", e);
        }
    }
}