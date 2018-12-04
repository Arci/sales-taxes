package it.arcidiacono.salestaxes.model.tax;

import java.math.BigDecimal;

/**
 * Represent a SalesTax.
 */
public interface SalesTax {

	/**
	 * Get the amount of tax to be applied to the given price.
	 *
	 * @param  price
	 *               the price for which calculate the tax
	 * @return
	 * 				the tax amount to be applied
	 */
	BigDecimal getTaxAmount(BigDecimal price);

}
