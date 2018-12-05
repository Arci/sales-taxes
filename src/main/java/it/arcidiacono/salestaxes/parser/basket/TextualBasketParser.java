package it.arcidiacono.salestaxes.parser.basket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import lombok.extern.slf4j.Slf4j;

/**
 * Parses a {@linkplain ShoppingBasket} in the format of the problem statement, as follows:
 *
 * <pre>
 * 1 imported bottle of perfume: 32.19
 * 1 bottle of perfume: 20.89
 * 1 packet of headache pills: 9.75
 * 1 imported box of chocolates: 11.85
 *
 * <pre/>
 */
@Slf4j
public class TextualBasketParser implements BasketParser {

	private static final String IMPORTED = "imported ";

	private static final Pattern PATTERN = Pattern.compile("(?<quantity>\\d+)\\s+(?<product>.+)\\s+at\\s+(?<price>\\S+)");

	@Override
	public ShoppingBasket parse(InputStream stream) throws IOException {
		ShoppingBasket basket = new ShoppingBasket();

		List<String> lines = readLines(stream);
		lines.forEach(line -> {
			if (StringUtils.isBlank(line)) {
				return;
			}

			Matcher matcher = PATTERN.matcher(line);
			if (matcher.find()) {
				String name = matcher.group("product");
				boolean imported = isImported(name);
				Product product = Product.of(name, imported);

				Integer quantity = Integer.valueOf(matcher.group("quantity"));
				BigDecimal price = new BigDecimal(matcher.group("price"));
				Purchase purchase = Purchase.of(quantity, product, price);

				basket.addPurchase(purchase);
			} else {
				log.warn("pattern does not math the line: '{}'", line);
			}
		});

		return basket;
	}

	private List<String> readLines(InputStream stream) throws IOException {
		List<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			list = br.lines().collect(Collectors.toList());
		}
		return list;
	}

	private boolean isImported(String product) {
		return product.contains(IMPORTED);
	}

}
