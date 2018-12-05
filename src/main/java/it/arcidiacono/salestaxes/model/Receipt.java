package it.arcidiacono.salestaxes.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Receipt {

	private List<Purchase> purchases;

	@Getter
	private BigDecimal salesTaxes;

	@Getter
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
		addPurchase((Purchase) purchase);
		this.salesTaxes = this.salesTaxes.add(purchase.getSalesTax());
	}

	public Collection<Purchase> getPurchases() {
		return new ArrayList<>(purchases);
	}

}
