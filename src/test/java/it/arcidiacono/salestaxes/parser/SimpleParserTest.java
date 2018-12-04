package it.arcidiacono.salestaxes.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.ShoppingBasket;

@TestInstance(Lifecycle.PER_CLASS)
public class SimpleParserTest {

	private static final String ASSETS_DIRECTORY = "baskets";

	private SimpleParser parser;

	@BeforeAll
	public void setup() {
		parser = new SimpleParser();
	}

	@Test
	@DisplayName("Should not break if file is empty")
	public void testEmptyFile() throws IOException {
		Path raw = getResource("empty.txt");
		ShoppingBasket basket = parser.parse(raw);

		assertThat(basket.getPurchases(), hasSize(0));
	}

	@Test
	@DisplayName("Should ignre unparsable lines")
	public void testIgnoreEmptyLinesFile() throws IOException {
		Path raw = getResource("wrong.txt");
		ShoppingBasket basket = parser.parse(raw);

		assertThat(basket.getPurchases(), hasSize(0));
	}

	@Test
	@DisplayName("Should skip empty lines")
	public void testNewLineFile() throws IOException {
		Path raw = getResource("new_line.txt");
		ShoppingBasket basket = parser.parse(raw);

		assertThat(basket.getPurchases(), hasSize(1));
		assertThat(basket.getPurchases(), contains(Purchase.of(7, Product.of("bottle of perfume", false), new BigDecimal("18.99"))));
	}

	@Test
	@DisplayName("Should correctly recognize imported products")
	public void testImportedProductsFile() throws IOException {
		Path raw = getResource("imported.txt");
		ShoppingBasket basket = parser.parse(raw);

		assertThat(basket.getPurchases(), hasSize(5));
		assertThat(basket.getPurchases(), contains(
				Purchase.of(1, Product.of("box of chocolates", true), new BigDecimal("10.00")),
				Purchase.of(4, Product.of("bottle of perfume", true), new BigDecimal("47.50")),
				Purchase.of(1, Product.of("bottle of perfume", true), new BigDecimal("27.99")),
				Purchase.of(7, Product.of("bottle of perfume", false), new BigDecimal("18.99")),
				Purchase.of(1, Product.of("box of chocolates", true), new BigDecimal("11.25"))));
	}

	private Path getResource(String name) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource(ASSETS_DIRECTORY + File.separator + name);
		if (resource == null) {
			throw new IOException("resource: " + name + " cannot be found");
		}
		return Path.of(resource.getPath());
	}

}
