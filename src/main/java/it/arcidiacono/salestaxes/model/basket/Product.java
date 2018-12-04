package it.arcidiacono.salestaxes.model.basket;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Product {

	@NonNull
	private String name;

	@NonNull
	private String category;

	@NonNull
	private Boolean imported;

	public static Product of(String name, String category) {
		return of(name, category, Boolean.FALSE);
	}

}
