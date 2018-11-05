package com.belonk.commons.util.crypt.password;

/**
 * A password encoder that does nothing.
 * Useful for testing where working with plain text passwords may be preferred.
 *
 * @author Keith Donald
 * @since 1.2
 */
public final class NoOpPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    /**
     * Get the singleton {@link NoOpPasswordEncoder}.
     */
    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

    private static final PasswordEncoder INSTANCE = new NoOpPasswordEncoder();

    private NoOpPasswordEncoder() {
    }

}