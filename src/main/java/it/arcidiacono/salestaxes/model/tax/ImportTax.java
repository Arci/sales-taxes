package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

/**
 * Represent a {@linkplain SalesTax} of the 5% for all imported goods.
 */
public class ImportTax extends RoundedSalesTax {

	static final Integer INCREMENT = 5;

	@Override
	public BigDecimal getTaxAmount(BigDecimal price) {
		return getTaxAmount(price, INCREMENT);
	}

}
