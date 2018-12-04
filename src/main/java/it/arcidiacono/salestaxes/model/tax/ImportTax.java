package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

public class ImportTax implements SalesTax {

	static final Integer INCREMENT = 5;

	@Override
	public BigDecimal getTaxAmount(BigDecimal price) {
		return getTaxAmount(price, INCREMENT);
	}

}
