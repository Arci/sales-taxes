package it.arcidiacono.salestaxes;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import it.arcidiacono.salestaxes.model.TaxedPurchase;
import it.arcidiacono.salestaxes.model.tax.BasicSalesTax;
import it.arcidiacono.salestaxes.model.tax.ImportTax;
import it.arcidiacono.salestaxes.model.tax.SalesTax;
import it.arcidiacono.salestaxes.parser.basket.BasketParser;
import it.arcidiacono.salestaxes.parser.basket.TextualBasketParser;
import it.arcidiacono.salestaxes.parser.categories.CSVCategoriesParser;
import it.arcidiacono.salestaxes.parser.categories.CategoriesParser;
import it.arcidiacono.salestaxes.writer.ReceiptWriter;
import it.arcidiacono.salestaxes.writer.TexualReceiptWriter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Implement the SalesTaxes problem using files.
 */
@Slf4j
@NoArgsConstructor
public class SalesTaxes {

	private static final String IMPORTED = "imported ";

	@Setter
	private List<String> excludedCategories = new ArrayList<>();

	@Setter
	private Map<String, String> categories = new HashMap<>();

	public SalesTaxes(List<String> excludedCategories) {
		this.excludedCategories = excludedCategories;
	}

	public void read(InputStream categories) throws IOException {
		CategoriesParser parser = new CSVCategoriesParser();
		this.categories = parser.parse(categories);
	}

	/**
	 * Parses the given input stream using the {@linkplain TextualBasketParser}.
	 *
	 * @param  basket
	 *                     the basket stream to parse
	 * @return
	 * 					an instance of {@linkplain ShoppingBasket}
	 * @throws IOException
	 *                     if any error occur handling the stream
	 */
	public ShoppingBasket parse(InputStream basket) throws IOException {
		BasketParser parser = new TextualBasketParser();
		return parser.parse(basket);
	}

	/**
	 * Build an instance of {@linkplain Receipt} based on the given {@linkplain ShoppingBasket}.
	 *
	 * @param  shoppingBasket
	 *                        the shopping basket to evaluate
	 * @return
	 * 						the resulting {@linkplain Receipt}
	 */
	public Receipt buildReceipt(ShoppingBasket shoppingBasket) {
		Receipt receipt = new Receipt();
		log.debug("creating receipt for {} purchases", shoppingBasket.getPurchases().size());
		for (Purchase purchase : shoppingBasket.getPurchases()) {
			Product product = purchase.getProduct();
			BigDecimal tax = BigDecimal.ZERO;

			if (product.isImported()) {
				SalesTax importTax = new ImportTax();
				BigDecimal taxAmount = importTax.getTaxAmount(purchase.getPrice());
				tax = tax.add(taxAmount);
				log.info("product: '{}' is imported, added import tax of: {}", product, taxAmount);
			}

			if (isBasicTaxApplicable(product)) {
				SalesTax basicTax = new BasicSalesTax();
				BigDecimal taxAmount = basicTax.getTaxAmount(purchase.getPrice());
				tax = tax.add(taxAmount);
				log.info("product: '{}' has applicable base tax, added import tax of: {}", product, taxAmount);
			}

			if (tax.compareTo(BigDecimal.ZERO) == 0) {
				receipt.addPurchase(purchase);
				log.debug("added untaxed purchase: {}", purchase);
			} else {
				TaxedPurchase taxedPurchase = TaxedPurchase.of(purchase, tax);
				receipt.addPurchase(taxedPurchase);
				log.debug("added taxed purchase: {}", taxedPurchase);
			}

		}
		return receipt;
	}

	private boolean isBasicTaxApplicable(Product product) {
		if (excludedCategories.isEmpty()) {
			log.info("no categories are excluded from basic tax");
			return true;
		}
		String productName = fixProductName(product.getName());
		Optional<String> category = seekProductCategory(productName);
		if (!category.isPresent()) {
			log.warn("product: '{}' has no declared category", product);
			return true;
		}
		log.debug("product: '{}' category is: '{}'", product, category.get());
		return excludedCategories.stream().noneMatch(excluded -> StringUtils.containsIgnoreCase(category.get(), excluded));
	}

	/**
	 * Format the given {@linkplain Receipt} in a printable fashion.
	 *
	 * @param  receipt
	 *                 the receipt to print
	 * @return
	 * 				the string representation of the receipt
	 */
	public String write(Receipt receipt) {
		ReceiptWriter writer = new TexualReceiptWriter();
		return writer.write(receipt);
	}

	private String fixProductName(String name) {
		return name.replace(IMPORTED, "");
	}

	private Optional<String> seekProductCategory(String product) {
		for (Entry<String, String> entry : categories.entrySet()) {
			String name = entry.getKey();
			if (StringUtils.containsIgnoreCase(name, product)) {
				return Optional.of(entry.getValue());
			}
		}
		return Optional.empty();
	}

}
