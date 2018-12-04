package it.arcidiacono.salestaxes.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import it.arcidiacono.salestaxes.model.tax.BasicSalesTax;
import it.arcidiacono.salestaxes.model.tax.SalesTax;

@TestInstance(Lifecycle.PER_CLASS)
public class TaxTest {

	private SalesTax tax;

	@BeforeAll
	public void setUp() {
		tax = new BasicSalesTax();
	}

	@Test
	public void exactTest() {
		BigDecimal price = new BigDecimal("10");
		BigDecimal expectedTax = new BigDecimal("1.00");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

	@Test
	public void roundingTest() {
		BigDecimal price = new BigDecimal("14.99");
		BigDecimal expectedTax = new BigDecimal("1.50");
		assertThat(tax.getTaxAmount(price), equalTo(expectedTax));
	}

}
