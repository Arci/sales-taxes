package it.arcidiacono.salestaxes;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.arcidiacono.salestaxes.parser.basket.SimpleParser;
import it.arcidiacono.salestaxes.parser.categories.CSVCategoriesParser;
import it.arcidiacono.salestaxes.parser.categories.CategoriesParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implement the SalesTaxes problem using files.
 */
@Slf4j
@NoArgsConstructor
public class SalesTaxes {

	// TODO move to cli param
	private static final List<String> EXCLUDED = Arrays.asList("books", "food", "medical");

	private Map<String, String> categories = new HashMap<>();

	public void read(InputStream categories) throws IOException {
		CategoriesParser parser = new CSVCategoriesParser();
		this.categories = parser.parse(categories);
	}

	/**
	 * Parses the given input stream using the {@linkplain SimpleParser}.
	 *
	 * @param  basket
	 *                     the basket stream to parse
	 * @return
	 * 					an instance of {@linkplain ShoppingBasket}
	 * @throws IOException
	 *                     if any error occur handling the stream
	 */
	public ShoppingBasket parse(InputStream basket) throws IOException {
		BasketParser parser = new SimpleParser();
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

			if (tax.compareTo(BigDecimal.ZERO) < 0) {
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
		if (!categories.containsKey(product.getName())) {
			log.warn("product: '{}' has no declared category", product);
			return false;
		}
		String category = categories.get(product.getName());
		log.debug("product: '{}' category is: '{}'", product, category);
		return EXCLUDED.stream().noneMatch(excluded -> StringUtils.containsIgnoreCase(category, excluded));
	}

	/**
	 * Format the given {@linkplain Receipt} in a printable fashion.
	 *
	 * @param  receipt
	 *                 the receipt to print
	 * @return
	 * 				the string representation of the receipt
	 */
	public String format(Receipt receipt) {
		// TODO as for the parser, a format interface and a simple formatter
		return receipt.toString();
	}

}
