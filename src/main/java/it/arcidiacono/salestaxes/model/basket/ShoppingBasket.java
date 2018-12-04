package it.arcidiacono.salestaxes.model.basket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingBasket {

	private List<Purchase> purchasess;

	public ShoppingBasket() {
		purchasess = new ArrayList<>();
	}

	public void addPurchase(Purchase purchase) {
		this.purchasess.add(purchase);
	}

	public Collection<Purchase> getPurchases() {
		return new ArrayList<>(purchasess);
	}

}
