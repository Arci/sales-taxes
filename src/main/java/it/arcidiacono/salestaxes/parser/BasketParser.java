package it.arcidiacono.salestaxes.parser;

import java.io.IOException;

import it.arcidiacono.salestaxes.model.basket.ShoppingBasket;

public interface BasketParser {

	public ShoppingBasket parse(String file) throws IOException;

}
