package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

public class BasicSalesTax implements SalesTax {

	static final Integer INCREMENT = 10;

	@Override
	public BigDecimal applyTax(BigDecimal price) {
		return applyTax(price, INCREMENT);
	}

}
