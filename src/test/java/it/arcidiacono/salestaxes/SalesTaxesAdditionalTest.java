package it.arcidiacono.salestaxes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import it.arcidiacono.salestaxes.util.ResourceBasedTest;

public class SalesTaxesAdditionalTest extends ResourceBasedTest {

	private static final String ASSETS_DIRECTORY = "additional";

	private SalesTaxes saleTaxes;

	@BeforeEach
	public void setUp() throws IOException {
		saleTaxes = new SalesTaxes();
	}

	@Test
	@DisplayName("Should treat all product as eligible since no category is given")
	public void testNoCategories() throws IOException {
		ShoppingBasket basket = saleTaxes.parse(getResourceStream(ASSETS_DIRECTORY, "basket.txt"));
		Receipt receipt = saleTaxes.buildReceipt(basket);

		assertThat(saleTaxes.write(receipt), equalTo(getResource(ASSETS_DIRECTORY, "result.txt")));
	}

	@Test
	@DisplayName("Should treat all product as eligible since excluded categories does not match")
	public void testNoExclusion() throws IOException {
		saleTaxes.setExcludedCategories(Arrays.asList("test", "goofy"));
		ShoppingBasket basket = saleTaxes.parse(getResourceStream(ASSETS_DIRECTORY, "basket.txt"));
		Receipt receipt = saleTaxes.buildReceipt(basket);

		assertThat(saleTaxes.write(receipt), equalTo(getResource(ASSETS_DIRECTORY, "result.txt")));
	}

}
