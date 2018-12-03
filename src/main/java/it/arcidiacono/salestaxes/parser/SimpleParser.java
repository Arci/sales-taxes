package it.arcidiacono.salestaxes.parser;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.arcidiacono.salestaxes.model.Product;
import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.ShoppingBasket;

public class SimpleParser implements BasketParser {

	private static final Logger logger = LoggerFactory.getLogger(SimpleParser.class);

	private static final Pattern PATTERN = Pattern.compile("(?<quantity>\\d+)\\s+(?<import>imported)?\\s*(?<product>.+)\\s+at\\s+(?<price>\\S+)");

	public ShoppingBasket parse(String file) throws IOException {
		ShoppingBasket basket = new ShoppingBasket();
		
		Path path = Paths.get(file);
		logger.info("parsing file at: {}", path);
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach((line) -> {
				Matcher matcher = PATTERN.matcher(line);
				if (matcher.find()) {
					// TODO category
					Product product = Product.of(matcher.group("product"), null);

					Integer quantity = Integer.valueOf(matcher.group("quantity"));
					BigDecimal price = new BigDecimal(matcher.group("price"));
					Purchase purchase = Purchase.of(quantity, product, price);
					
					basket.addPurchase(purchase);
				} else {
					logger.warn("pattern does not math the line: {}", line);
				}
			});
		}
		
		return basket;
	}

}
