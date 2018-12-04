package it.arcidiacono.salestaxes.parser;

import java.io.IOException;
import java.nio.file.Path;

import it.arcidiacono.salestaxes.model.ShoppingBasket;

/**
 *
 */
public interface BasketParser {

	/**
	 * Parses a data source into a {@linkplain ShoppingBasket}.
	 *
	 * @param  file
	 *                     the file that contains the raw basket representation
	 * @return
	 * 					an instance of {@linkplain ShoppingBasket}
	 * @throws IOException
	 *                     if an error occur dealing with the file
	 */
	ShoppingBasket parse(Path file) throws IOException;

}
