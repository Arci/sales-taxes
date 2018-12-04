package it.arcidiacono.salestaxes.model.basket;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;

@Data
public class Purchase {

	private final Integer quantity;

	private final Product product;

	private final BigDecimal price;

	protected Purchase(@NonNull Integer quantity, @NonNull Product product, @NonNull BigDecimal price) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.price = price;
	}

	public static Purchase of(@NonNull Integer quantity, @NonNull Product product, @NonNull BigDecimal price) {
		return new Purchase(quantity, product, price);
	}

}
