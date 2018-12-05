package it.arcidiacono.salestaxes.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import it.arcidiacono.salestaxes.parser.basket.BasketParser;
import it.arcidiacono.salestaxes.parser.basket.TextualBasketParser;
import it.arcidiacono.salestaxes.util.ResourceBasedTest;

@TestInstance(Lifecycle.PER_CLASS)
public class TextualBasketParserTest extends ResourceBasedTest {

	private static final String ASSETS_DIRECTORY = "baskets";

	private BasketParser parser;

	@BeforeAll
	public void setup() {
		parser = new TextualBasketParser();
	}

	@Test
	@DisplayName("Should not break if file is empty")
	public void testEmptyFile() throws IOException {
		InputStream stream = getResourceStream(ASSETS_DIRECTORY, "empty.txt");
		ShoppingBasket basket = parser.parse(stream);

		assertThat(basket.getPurchases(), hasSize(0));
	}

	@Test
	@DisplayName("Should ignore unparsable lines")
	public void testIgnoreEmptyLinesFile() throws IOException {
		InputStream stream = getResourceStream(ASSETS_DIRECTORY, "wrong.txt");
		ShoppingBasket basket = parser.parse(stream);

		assertThat(basket.getPurchases(), hasSize(0));
	}

	@Test
	@DisplayName("Should skip empty lines")
	public void testNewLineFile() throws IOException {
		InputStream stream = getResourceStream(ASSETS_DIRECTORY, "new_line.txt");
		ShoppingBasket basket = parser.parse(stream);

		assertThat(basket.getPurchases(), hasSize(1));
		assertThat(basket.getPurchases(), contains(Purchase.of(7, Product.of("bottle of perfume", false), new BigDecimal("18.99"))));
	}

	@Test
	@DisplayName("Should correctly recognize imported products")
	public void testImportedProductsFile() throws IOException {
		InputStream stream = getResourceStream(ASSETS_DIRECTORY, "imported.txt");
		ShoppingBasket basket = parser.parse(stream);

		assertThat(basket.getPurchases(), hasSize(5));
		assertThat(basket.getPurchases(), contains(
				Purchase.of(1, Product.of("imported box of chocolates", true), new BigDecimal("10.00")),
				Purchase.of(4, Product.of("imported bottle of perfume", true), new BigDecimal("47.50")),
				Purchase.of(1, Product.of("bottle of imported perfume", true), new BigDecimal("27.99")),
				Purchase.of(7, Product.of("bottle of perfume", false), new BigDecimal("18.99")),
				Purchase.of(1, Product.of("box of imported chocolates", true), new BigDecimal("11.25"))));
	}

}
