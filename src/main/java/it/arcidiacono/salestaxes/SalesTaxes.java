package it.arcidiacono.salestaxes;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import it.arcidiacono.salestaxes.model.TaxedPurchase;
import it.arcidiacono.salestaxes.model.tax.BasicSalesTax;
import it.arcidiacono.salestaxes.model.tax.ImportTax;
import it.arcidiacono.salestaxes.model.tax.SalesTax;
import it.arcidiacono.salestaxes.parser.BasketParser;
import it.arcidiacono.salestaxes.parser.SimpleParser;
import lombok.NoArgsConstructor;

/**
 * Implement the SalesTaxes problem using files.
 */
@NoArgsConstructor
public class SalesTaxes {

	Map<String, String> categories = new HashMap<>();

	public void read(InputStream categories) throws IOException {
		// TODO read categories to the hash set
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
		for (Purchase purchase : shoppingBasket.getPurchases()) {
			Product product = purchase.getProduct();
			BigDecimal tax = BigDecimal.ZERO;

			if (product.isImported()) {
				SalesTax importTax = new ImportTax();
				BigDecimal taxAmount = importTax.getTaxAmount(purchase.getPrice());
				tax = tax.add(taxAmount);
			}

			if (isBasicTaxApplicable(product)) {
				SalesTax basicTax = new BasicSalesTax();
				BigDecimal taxAmount = basicTax.getTaxAmount(purchase.getPrice());
				tax = tax.add(taxAmount);
			}

			if (tax.compareTo(BigDecimal.ZERO) < 0) {
				receipt.addPurchase(purchase);
			} else {
				receipt.addPurchase(TaxedPurchase.of(purchase, tax));
			}

		}
		return receipt;
	}

	private boolean isBasicTaxApplicable(Product product) {
		if (!categories.containsKey(product.getName())) {
			return false;
		}
		String category = categories.get(product.getName());
		return category.equalsIgnoreCase("books") || category.equalsIgnoreCase("food") || category.equalsIgnoreCase("medical products");
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
