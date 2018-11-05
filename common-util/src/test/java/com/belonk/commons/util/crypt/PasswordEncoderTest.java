package com.belonk.commons.util.crypt;

import com.belonk.commons.util.crypt.bcrypt.BCryptPasswordEncoder;
import com.belonk.commons.util.crypt.password.Md5PasswordEncoder;
import com.belonk.commons.util.crypt.password.PasswordEncoder;
import com.belonk.commons.util.crypt.password.SHAPasswordEncoder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sun on 2018/11/5.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class PasswordEncoderTest {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private String pwd = "123456";
    private PasswordEncoder encoder = new SHAPasswordEncoder();
    private String secure = "abc";

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Test
    public void test1() throws Exception {
        String s = encoder.encode(pwd);
        // 29e4699c535c9db46a98a82c7eb81833c75edb96b1462c91895add8ce3727e42ae7ac742058c7c5f
        System.out.println(s);
        boolean match = encoder.matches(pwd, s);
        Assert.assertTrue(match);

        encoder = new SHAPasswordEncoder(secure);
        s = encoder.encode(pwd);
        // 3bedef7a39e1d825bad8820f4d3ee8a5e4f83977d3fc580b408ece7f7f968e5ae2257eaf9b6f4679
        System.out.println(s);
        match = encoder.matches(pwd, s);
        Assert.assertTrue(match);

        encoder = new Md5PasswordEncoder(secure);
        s = encoder.encode(pwd);
        // 63fc3b741964e0f8330d527e46c4f66064ca86a7025c4f0f
        System.out.println(s);
        match = encoder.matches(pwd, s);
        Assert.assertTrue(match);

        encoder = new BCryptPasswordEncoder();
        s = encoder.encode(pwd);
        // $2a$10$QGi516Fk.f8VcberqCtUmuj6kL0Urxu78mOMMPkVaTh9fj7Soja7O
        System.out.println(s);
        match = encoder.matches(pwd, s);
        Assert.assertTrue(match);
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
     
     
     
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}