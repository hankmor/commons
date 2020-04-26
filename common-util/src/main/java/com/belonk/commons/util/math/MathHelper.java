package com.belonk.commons.util.math;

import com.belonk.commons.util.asserts.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by sun on 2020/4/26.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public final class MathHelper {
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
	 * Methods
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend) {
		return divide(divisor, dividend, 2, RoundingMode.HALF_UP);
	}

	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend, int scale) {
		return divide(divisor, dividend, scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend, RoundingMode roundingMode) {
		return divide(divisor, dividend, 0, roundingMode);
	}

	public static BigDecimal divide(BigDecimal divisor, BigDecimal dividend, int scale, RoundingMode roundingMode) {
		Assert.notNull(divisor, "The divisor must not be null.");
		Assert.notNull(dividend, "The dividend must not be null.");
		Assert.isTrue(BigDecimal.ZERO.compareTo(dividend) <= 0, "The dividend must great then zero");
		return divisor.divide(dividend, scale, roundingMode);
	}
}