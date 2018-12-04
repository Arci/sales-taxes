package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

public class BasicSalesTax implements SalesTax {

	static final Integer INCREMENT = 10;

	@Override
	public BigDecimal getTaxAmount(BigDecimal price) {
		return getTaxAmount(price, INCREMENT);
	}

}
