package it.arcidiacono.salestaxes.parser.basket;

import java.io.IOException;
import java.io.InputStream;

import it.arcidiacono.salestaxes.model.ShoppingBasket;

/**
 *
 */
public interface BasketParser {

	/**
	 * Parses a data source into a {@linkplain ShoppingBasket}.
	 *
	 * @param  stream
	 *                     the stream containing the raw representation
	 * @return
	 * 					an instance of {@linkplain ShoppingBasket}
	 * @throws IOException
	 *                     if an error occur dealing with the file
	 */
	ShoppingBasket parse(InputStream stream) throws IOException;

}
