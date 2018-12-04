package it.arcidiacono.salestaxes.model.basket;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data(staticConstructor = "of")
@EqualsAndHashCode(exclude = { "imported" })
public class Product {

	@NonNull
	private final String name;

	// @NonNull
	// private String category;

	@NonNull
	private final Boolean imported;

}
