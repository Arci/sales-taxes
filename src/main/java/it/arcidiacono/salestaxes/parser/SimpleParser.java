package it.arcidiacono.salestaxes.parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import it.arcidiacono.salestaxes.model.basket.Product;
import it.arcidiacono.salestaxes.model.basket.Purchase;
import it.arcidiacono.salestaxes.model.basket.ShoppingBasket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleParser implements BasketParser {

	private static final String IMPORTED = "imported ";

	private static final Pattern PATTERN = Pattern.compile("(?<quantity>\\d+)\\s+(?<product>.+)\\s+at\\s+(?<price>\\S+)");

	@Override
	public ShoppingBasket parse(Path file) throws IOException {
		ShoppingBasket basket = new ShoppingBasket();
		log.info("parsing file: {}", file);
		try (Stream<String> stream = Files.lines(file)) {
			stream.forEach(line -> {
				if (StringUtils.isBlank(line)) {
					return;
				}

				Matcher matcher = PATTERN.matcher(line);
				if (matcher.find()) {
					String name = matcher.group("product");
					boolean imported = isImported(name);
					if (imported) {
						name = fixProductName(name);
					}
					Product product = Product.of(name, imported);

					Integer quantity = Integer.valueOf(matcher.group("quantity"));
					BigDecimal price = new BigDecimal(matcher.group("price"));
					Purchase purchase = Purchase.of(quantity, product, price);

					basket.addPurchase(purchase);
				} else {
					log.warn("pattern does not math the line: '{}'", line);
				}
			});
		}

		return basket;
	}

	private String fixProductName(String name) {
		return name.replace(IMPORTED, "");

	}

	private boolean isImported(String product) {
		return product.contains(IMPORTED);
	}

}
