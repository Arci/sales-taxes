package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represent a {@linkplain SalesTax} which value is rounded to the nearest 0.05.
 */
public abstract class RoundedSalesTax implements SalesTax {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	private static final BigDecimal ROUNDING = new BigDecimal("0.05");

	protected BigDecimal getTaxAmount(BigDecimal price, Integer increment) {
		return round(price.multiply(new BigDecimal(increment)).divide(ONE_HUNDRED));
	}

	private BigDecimal round(BigDecimal price) {
		return round(price, ROUNDING, RoundingMode.UP);
	}

	private BigDecimal round(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		if (increment.signum() == 0) {
			return value;
		}
		return value.divide(increment, 0, roundingMode).multiply(increment);
	}

}
