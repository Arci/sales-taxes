package it.arcidiacono.salestaxes.parser;

import java.io.IOException;

import it.arcidiacono.salestaxes.model.ShoppingBasket;

public interface BasketParser {

	public ShoppingBasket parse(String file) throws IOException;
	
}
