package it.arcidiacono.salestaxes.model.basket;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Purchase {

	@NonNull
	private Integer quantity;

	@NonNull
	private Product product;

	@NonNull
	private BigDecimal price;

}
