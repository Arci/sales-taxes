package it.arcidiacono.salestaxes.parser.categories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import it.arcidiacono.salestaxes.model.ShoppingBasket;

/**
 *
 */
public interface CategoriesParser {

	/**
	 * Parses a data source into a map of string where keys are the products and values its category.
	 *
	 * @param  stream
	 *                     the stream containing the raw representation
	 * @return
	 * 					an instance of {@linkplain ShoppingBasket}
	 * @throws IOException
	 *                     if an error occur dealing with the file
	 */
	Map<String, String> parse(InputStream stream) throws IOException;

}
