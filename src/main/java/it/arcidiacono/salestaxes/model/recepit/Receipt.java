package it.arcidiacono.salestaxes.model.recepit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.arcidiacono.salestaxes.model.basket.Purchase;

public class Receipt {

	private List<Purchase> purchases;

	private BigDecimal salesTaxes;

	private BigDecimal total;

	public Receipt() {
		purchases = new ArrayList<Purchase>();
		salesTaxes = BigDecimal.ZERO;
		total = BigDecimal.ZERO;
	}

	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
		this.total.add(purchase.getPrice());
	}

	public void addPurchase(TaxedPurchase purchase) {
		this.purchases.add(purchase);
		this.total.add(purchase.getTaxedPrice());
		salesTaxes.add(purchase.getSalesTax());
	}

	public Collection<Purchase> getPurchases() {
		return new ArrayList<Purchase>(purchases);
	}

}
