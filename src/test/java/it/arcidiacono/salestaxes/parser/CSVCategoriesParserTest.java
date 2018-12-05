package it.arcidiacono.salestaxes.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import it.arcidiacono.salestaxes.parser.categories.CSVCategoriesParser;
import it.arcidiacono.salestaxes.parser.categories.CategoriesParser;

@TestInstance(Lifecycle.PER_CLASS)
public class CSVCategoriesParserTest {

	private static final String ASSETS_DIRECTORY = "categories";

	private CategoriesParser parser;

	@BeforeAll
	public void setup() {
		parser = new CSVCategoriesParser();
	}

	@Test
	@DisplayName("Should not break if file is empty")
	public void testEmptyFile() throws IOException {
		InputStream stream = getResource("empty.csv");
		Map<String, String> categories = parser.parse(stream);

		assertTrue(categories.isEmpty());
	}

	@Test
	@DisplayName("Should work even without the header")
	public void testNoHeader() throws IOException {
		InputStream stream = getResource("no_header.csv");
		Map<String, String> categories = parser.parse(stream);

		assertThat(categories.keySet(), hasSize(1));
		assertThat(categories.get("bottle of perfume"), equalTo("leisure"));
	}

	@Test
	@DisplayName("Should ignore unparsable lines")
	public void testIgnoreUnparsableLinesFile() throws IOException {
		InputStream stream = getResource("missing_columns.csv");
		Map<String, String> categories = parser.parse(stream);

		assertThat(categories.keySet(), hasSize(0));
	}

	@Test
	@DisplayName("Should trim spaces")
	public void testTrimSpaces() throws IOException {
		InputStream stream = getResource("spaced.csv");
		Map<String, String> categories = parser.parse(stream);

		assertThat(categories.keySet(), hasSize(2));
		assertThat(categories.get("book"), equalTo("books"));
		assertThat(categories.get("music CD"), equalTo("leisure"));
	}

	@Test
	@DisplayName("Should correctly recognize all categories")
	public void testImportCategoriesFile() throws IOException {
		InputStream stream = getResource("categories.csv");
		Map<String, String> categories = parser.parse(stream);

		assertThat(categories.keySet(), hasSize(6));
		assertThat(categories.get("book"), equalTo("books"));
		assertThat(categories.get("music CD"), equalTo("leisure"));
		assertThat(categories.get("chocolate bar"), equalTo("food"));
		assertThat(categories.get("box of chocolates"), equalTo("food"));
		assertThat(categories.get("bottle of perfume"), equalTo("leisure"));
		assertThat(categories.get("packet of headache pills"), equalTo("medical products"));
	}

	private InputStream getResource(String name) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(ASSETS_DIRECTORY + File.separator + name);
		if (stream == null) {
			throw new IOException("resource: " + name + " cannot be found");
		}
		return stream;
	}

}
