package com.belonk.commons.util.random;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created by sun on 2016/3/17.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public class RandomStringHelper extends RandomStringUtils {
    //~ Static fields/initializers =====================================================================================
    private static final Logger LOG = LoggerFactory.getLogger(RandomStringHelper.class);

    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================

    public static void main(String[] args) {
        int count = 6;
        String s = RandomStringHelper.random(count, true, true);
        System.out.println("random : " + s);
        s = RandomStringHelper.randomAscii(count);
        System.out.println("randomAscii : " + s);
        s = RandomStringHelper.randomNumeric(count);
        System.out.println("randomNumeric : " + s);
        s = RandomStringHelper.randomAlphabetic(count);
        System.out.println("randomAlphabetic : " + s);
        s = RandomStringHelper.randomAlphanumeric(count);
        System.out.println("randomAlphanumeric : " + s);
    }
}
