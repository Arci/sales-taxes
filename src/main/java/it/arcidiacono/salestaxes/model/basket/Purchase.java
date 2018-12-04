package it.arcidiacono.salestaxes.model.basket;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Purchase {

	@NonNull
	private final Integer quantity;

	@NonNull
	private final Product product;

	@NonNull
	private final BigDecimal price;

}
