package it.arcidiacono.salestaxes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import it.arcidiacono.salestaxes.model.Category;
import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import it.arcidiacono.salestaxes.parser.BasketParser;
import it.arcidiacono.salestaxes.parser.SimpleParser;
import lombok.NoArgsConstructor;

/**
 * Implement the SalesTaxes problem using files.
 */
@NoArgsConstructor
public class SalesTaxes {

	Set<Category> categories = new HashSet<>();

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
		// TODO the actual logic to implement the problem
		return null;
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
		return "test";
	}

}
