package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface SalesTax {

	static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	static final BigDecimal ROUNDING = new BigDecimal("0.05");

	BigDecimal getTaxAmount(BigDecimal price);

	default BigDecimal getTaxAmount(BigDecimal price, Integer increment) {
		return round(price.multiply(new BigDecimal(increment)).divide(ONE_HUNDRED));
	}

	default BigDecimal round(BigDecimal price) {
		return round(price, ROUNDING, RoundingMode.UP);
	}

	default BigDecimal round(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		if (increment.signum() == 0) {
			return value;
		}
		return value.divide(increment, 0, roundingMode).multiply(increment);
	}

}
