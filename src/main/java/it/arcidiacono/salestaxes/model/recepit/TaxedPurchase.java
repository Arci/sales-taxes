package it.arcidiacono.salestaxes.model.recepit;

import java.math.BigDecimal;

import it.arcidiacono.salestaxes.model.basket.Purchase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
public class TaxedPurchase extends Purchase {

	@NonNull
	private BigDecimal salesTax;
	
	@NonNull
	private BigDecimal taxedPrice;

	public TaxedPurchase() {
		super();
		salesTax = BigDecimal.ZERO;
	}

}
