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

/**
 * Test the three problems stated in the problem description
 */
public class SalesTaxesProblemsTest extends ResourceBasedTest {

	private static final String ASSETS_DIRECTORY = "problem";

	private SalesTaxes saleTaxes;

	@BeforeEach
	public void setUp() throws IOException {
		saleTaxes = new SalesTaxes();
		saleTaxes.setExcludedCategories(Arrays.asList("food", "books", "medical"));
		saleTaxes.read(getResourceStream(ASSETS_DIRECTORY, "categories.csv"));
	}

	@Test
	@DisplayName("Should solve problem 1")
	public void testProblem1() throws IOException {
		ShoppingBasket basket = saleTaxes.parse(getResourceStream(ASSETS_DIRECTORY, "basket_1.txt"));
		Receipt receipt = saleTaxes.buildReceipt(basket);

		assertThat(saleTaxes.write(receipt), equalTo(getResource(ASSETS_DIRECTORY, "result_1.txt")));
	}

	@Test
	@DisplayName("Should solve problem 2")
	public void testProblem2() throws IOException {
		ShoppingBasket basket = saleTaxes.parse(getResourceStream(ASSETS_DIRECTORY, "basket_2.txt"));
		Receipt receipt = saleTaxes.buildReceipt(basket);

		assertThat(saleTaxes.write(receipt), equalTo(getResource(ASSETS_DIRECTORY, "result_2.txt")));
	}

	@Test
	@DisplayName("Should solve problem 3")
	public void testProblem3() throws IOException {
		ShoppingBasket basket = saleTaxes.parse(getResourceStream(ASSETS_DIRECTORY, "basket_3.txt"));
		Receipt receipt = saleTaxes.buildReceipt(basket);

		assertThat(saleTaxes.write(receipt), equalTo(getResource(ASSETS_DIRECTORY, "result_3.txt")));
	}
}
