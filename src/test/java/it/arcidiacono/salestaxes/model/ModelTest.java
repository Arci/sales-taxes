package it.arcidiacono.salestaxes.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import lombok.NonNull;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ModelTest {

	@Test
	@DisplayName("Equals of Product should behave correctly")
	public void testProductEquals() {
		EqualsVerifier.forClass(Product.class)
				.withIgnoredAnnotations(NonNull.class)
				.withIgnoredFields("imported")
				.suppress(Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	@DisplayName("Equals of Purchase should behave correctly")
	public void testPurchaseEquals() {
		EqualsVerifier.forClass(Purchase.class)
				.withIgnoredAnnotations(NonNull.class)
				.suppress(Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	@DisplayName("Equals of TaxedPurchase should behave correctly")
	public void testTaxedPurchaseEquals() {
		EqualsVerifier.forClass(TaxedPurchase.class)
				.withRedefinedSuperclass()
				.withIgnoredAnnotations(NonNull.class)
				.suppress(Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	@DisplayName("ShoppingBasket should return immutable copies of the basket")
	public void testBasketImmutability() {
		ShoppingBasket basket = new ShoppingBasket();
		Product product = Product.of("box of chocolates", true);

		Purchase purchase = Purchase.of(1, product, new BigDecimal("10.00"));
		basket.addPurchase(purchase);

		Collection<Purchase> purchases = basket.getPurchases();
		assertThat(purchases, hasSize(1));

		purchase = Purchase.of(4, product, new BigDecimal("12.00"));
		basket.addPurchase(purchase);
		assertThat(purchases, hasSize(1));
		assertThat(basket.getPurchases(), hasSize(2));
	}

}
