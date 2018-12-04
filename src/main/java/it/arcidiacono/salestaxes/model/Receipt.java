package it.arcidiacono.salestaxes.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Receipt {

	private List<Purchase> purchases;

	private BigDecimal salesTaxes;

	private BigDecimal total;

	public Receipt() {
		purchases = new ArrayList<>();
		salesTaxes = BigDecimal.ZERO;
		total = BigDecimal.ZERO;
	}

	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
		this.total = this.total.add(purchase.getPrice());
	}

	public void addPurchase(TaxedPurchase purchase) {
		this.purchases.add(purchase);
		this.total = this.total.add(purchase.getTaxedPrice());
		this.salesTaxes = this.salesTaxes.add(purchase.getSalesTax());
	}

	public Collection<Purchase> getPurchases() {
		return new ArrayList<>(purchases);
	}

}
