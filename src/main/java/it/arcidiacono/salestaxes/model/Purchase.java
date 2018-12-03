package it.arcidiacono.salestaxes.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Purchase {

	@NonNull
	private Integer quantity;

	@NonNull
	private Product product;

	@NonNull
	private BigDecimal price;

}
