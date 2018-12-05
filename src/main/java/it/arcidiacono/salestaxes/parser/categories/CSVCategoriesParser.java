package it.arcidiacono.salestaxes.parser.categories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Parses a categories CSV file in the following format:
 *
 * <pre>
 * product,category
 * book,books
 * music CD,leisure
 * chocolate bar,food
 * box of chocolates,food
 * bottle of perfume,food
 * packet of headache pills,medical products
 *
 * <pre/>
 */
@Slf4j
public class CSVCategoriesParser implements CategoriesParser {

	private static final String HEADER = "product,category";

	@Override
	public Map<String, String> parse(InputStream stream) throws IOException {
		Map<String, String> categories = new HashMap<>();

		List<String> lines = readLines(stream);
		lines.forEach(line -> {
			if (StringUtils.isBlank(line) || StringUtils.equalsIgnoreCase(line, HEADER)) {
				return;
			}

			String[] parts = line.split("\\s*,\\s*");
			if (parts.length == 2) {
				String product = StringUtils.trimToEmpty(parts[0]);
				String category = StringUtils.trimToEmpty(parts[1]);
				if (product != null && category != null) {
					categories.put(product, category);
				} else {
					log.warn("product or category are empty: '{}'", line);
				}
			} else {
				log.warn("line has more or less than 2 columns: '{}'", line);
			}
		});

		return categories;
	}

	private List<String> readLines(InputStream stream) throws IOException {
		List<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			list = br.lines().collect(Collectors.toList());
		}
		return list;
	}

}
