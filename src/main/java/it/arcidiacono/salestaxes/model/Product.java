package it.arcidiacono.salestaxes.model;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Product {

	@NonNull
	private String name;

	@NonNull
	private String category;
	
	private boolean imported;
	
}
