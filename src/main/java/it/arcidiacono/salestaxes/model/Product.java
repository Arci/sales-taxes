package it.arcidiacono.salestaxes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data(staticConstructor = "of")
@EqualsAndHashCode(exclude = { "imported" })
public class Product {

	@NonNull
	private final String name;

	private final boolean imported;

	@Override
	public String toString() {
		return name;
	}

}
