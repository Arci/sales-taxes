package it.arcidiacono.salestaxes.tax;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.arcidiacono.salestaxes.model.tax.BasicSalesTax;
import it.arcidiacono.salestaxes.model.tax.ImportTax;
import it.arcidiacono.salestaxes.model.tax.SalesTax;

public class TaxTest {

	@Test
	@DisplayName("It should get a tax of zero for a price of zero")
	public void zeroTest() {
		SalesTax tax = new BasicSalesTax();
		BigDecimal price = new BigDecimal("0");

		BigDecimal expectedTax = new BigDecimal("0.00");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

	@Test
	@DisplayName("It should (at least) not break with negative numbers")
	public void negativeTest() {
		SalesTax tax = new BasicSalesTax();
		BigDecimal price = new BigDecimal("-10");

		BigDecimal expectedTax = new BigDecimal("-1.00");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

	@Test
	@DisplayName("It should calculate a tax amount when no rounding is needed")
	public void exactTest() {
		SalesTax tax = new BasicSalesTax();
		BigDecimal price = new BigDecimal("10");

		BigDecimal expectedTax = new BigDecimal("1.00");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

	@Test
	@DisplayName("It should give the tax amount rounded to the next 0.05")
	public void roundingTest() {
		SalesTax tax = new BasicSalesTax();
		BigDecimal price = new BigDecimal("14.99");

		BigDecimal expectedTax = new BigDecimal("1.50");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

	@Test
	@DisplayName("Different taxes should apply different rates to the same price")
	public void importTax() {
		SalesTax basicTax = new BasicSalesTax();
		SalesTax importTax = new ImportTax();
		BigDecimal price = new BigDecimal("10");

		BigDecimal expectedBasicTax = new BigDecimal("1.00");
		BigDecimal expectedImportTax = new BigDecimal("0.50");
		assertThat(basicTax.getTaxAmount(price), equalTo(expectedBasicTax));
		assertThat(importTax.getTaxAmount(price), equalTo(expectedImportTax));
	}

}
