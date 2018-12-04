package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

/**
 * Represent a {@linkplain SalesTax} for the 10% on all applicable goods.
 */
public class BasicSalesTax extends RoundedSalesTax {

	static final Integer INCREMENT = 10;

	@Override
	public BigDecimal getTaxAmount(BigDecimal price) {
		return getTaxAmount(price, INCREMENT);
	}

}
