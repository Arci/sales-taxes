package it.arcidiacono.salestaxes.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TaxedPurchase extends Purchase {

	private final BigDecimal salesTax;

	private final BigDecimal taxedPrice;

	protected TaxedPurchase(@NonNull Integer quantity, @NonNull Product product, @NonNull BigDecimal price, @NonNull BigDecimal salesTax) {
		super(quantity, product, price);
		this.salesTax = salesTax;
		this.taxedPrice = price.add(salesTax);
	}

	public static TaxedPurchase of(Purchase purchase, @NonNull BigDecimal salesTax) {
		return new TaxedPurchase(purchase.getQuantity(), purchase.getProduct(), purchase.getPrice(), salesTax);
	}

	public static TaxedPurchase of(@NonNull Integer quantity, @NonNull Product product, @NonNull BigDecimal price, @NonNull BigDecimal salesTax) {
		return new TaxedPurchase(quantity, product, price, salesTax);
	}

	@Override
	public BigDecimal getPrice() {
		return this.taxedPrice;
	}

}
