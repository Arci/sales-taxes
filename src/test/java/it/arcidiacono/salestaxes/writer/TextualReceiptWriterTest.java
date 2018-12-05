package it.arcidiacono.salestaxes.writer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.TaxedPurchase;

@TestInstance(Lifecycle.PER_CLASS)
public class TextualReceiptWriterTest {

	private static final String ASSETS_DIRECTORY = "receipts";

	private ReceiptWriter writer;

	@BeforeAll
	public void setup() {
		writer = new TexualReceiptWriter();
	}

	@Test
	@DisplayName("Should not break if receipt is null")
	public void testNullReceipt() {
		assertThat(writer.write(null), isEmptyOrNullString());
	}

	@Test
	@DisplayName("Should handle no purchase receipt")
	public void testNoPurchase() throws IOException {
		Receipt receipt = new Receipt();

		String output = writer.write(receipt);
		assertThat(output, equalTo(getResource("no_purchase.txt")));
	}

	@Test
	@DisplayName("Should handle non taxed purchases")
	public void testNonTaxedPurchases() throws IOException {
		Receipt receipt = new Receipt();
		receipt.addPurchase(Purchase.of(1, Product.of("book", false), new BigDecimal("12.49")));
		receipt.addPurchase(Purchase.of(1, Product.of("chocolate bar", false), new BigDecimal("0.85")));

		String output = writer.write(receipt);
		assertThat(output, equalTo(getResource("no_tax.txt")));
	}

	@Test
	@DisplayName("Should handle also taxed purchases")
	public void testTasexPurchases() throws IOException {
		Receipt receipt = new Receipt();
		receipt.addPurchase(Purchase.of(1, Product.of("book", false), new BigDecimal("12.49")));
		receipt.addPurchase(TaxedPurchase.of(1, Product.of("music CD", false), new BigDecimal("14.99"), new BigDecimal("1.5")));
		receipt.addPurchase(TaxedPurchase.of(Purchase.of(1, Product.of("bottle of perfume", true), new BigDecimal("47.50")), new BigDecimal("7.15")));

		String output = writer.write(receipt);
		assertThat(output, equalTo(getResource("taxed.txt")));
	}

	private String getResource(String name) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(ASSETS_DIRECTORY + File.separator + name);
		if (stream == null) {
			throw new IOException("resource: " + name + " cannot be found");
		}
		return IOUtils.toString(stream, StandardCharsets.UTF_8);
	}
}
