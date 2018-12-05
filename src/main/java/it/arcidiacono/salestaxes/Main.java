package it.arcidiacono.salestaxes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import it.arcidiacono.salestaxes.model.Receipt;
import it.arcidiacono.salestaxes.model.ShoppingBasket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	private static final String BASKET_OPTION = "b";

	private static final String CATEGORIES_OPTION = "c";

	private static final String EXCLUDE_OPTION = "e";

	private static final String OUTPUT_OPTION = "o";

	public static void main(String[] args) {
		Options options = defineOptions();

		CommandLineParser parser = new DefaultParser();
		CommandLine commandLine = null;
		try {
			commandLine = parser.parse(options, args);
		} catch (ParseException e) {
			log.error("unable to parse arguments: {}", e.getMessage());
			help(options);
			System.exit(0);
		}

		InputStream basket = null;
		InputStream categories = null;
		try {
			basket = getOptionStream(commandLine, BASKET_OPTION);
			categories = getOptionStream(commandLine, CATEGORIES_OPTION);
		} catch (IOException e) {
			log.error("cannot read input files", e);
			System.exit(0);
		}
		List<String> excludedCategories = getOptionsList(commandLine, EXCLUDE_OPTION);
		log.debug("excluded categories: {}", StringUtils.join(excludedCategories, ","));

		Path outputLocation = getOptionPath(commandLine, OUTPUT_OPTION);
		log.debug("output arg: {}", outputLocation);
		if (Files.notExists(outputLocation)) {
			log.warn("output file does not exists, will be created", outputLocation);
			try {
				outputLocation = Files.createFile(outputLocation);
			} catch (IOException e) {
				log.error("cannot create output file: {}", outputLocation, e);
				System.exit(0);
			}
		}

		try {
			String receipt = buildReceipt(basket, categories, excludedCategories);
			writeRecipt(outputLocation, receipt);
		} catch (IOException e) {
			log.error("an error occurred calculating the receipt: {}", e.getMessage(), e);
			System.exit(0);
		}
	}

	private static Options defineOptions() {
		Option categories = Option.builder(CATEGORIES_OPTION)
				.required()
				.hasArg()
				.longOpt("categories")
				.desc("the categories file path")
				.build();

		Option basket = Option.builder(BASKET_OPTION)
				.required()
				.hasArg()
				.longOpt("basket")
				.desc("the shopping basket file path")
				.build();

		Option excluded = Option.builder(EXCLUDE_OPTION)
				.hasArgs()
				.longOpt("exclude")
				.desc("categories excluded from base tax")
				.build();

		Option output = Option.builder(OUTPUT_OPTION)
				.required()
				.hasArg()
				.longOpt("output")
				.desc("the file to which output the receipt")
				.build();

		final Options options = new Options();
		options.addOption(categories);
		options.addOption(basket);
		options.addOption(excluded);
		options.addOption(output);
		return options;
	}

	private static void help(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("SalesTaxes", options);
	}

	private static Path getOptionPath(CommandLine commandLine, String option) {
		return Paths.get(commandLine.getOptionValue(option));
	}

	private static List<String> getOptionsList(CommandLine commandLine, String option) {
		String[] optionValues = commandLine.getOptionValues(option);
		if (optionValues != null) {
			return Arrays.asList(optionValues);
		}
		return Collections.emptyList();
	}

	private static InputStream getOptionStream(CommandLine commandLine, String option) throws FileNotFoundException {
		Path path = getOptionPath(commandLine, option);
		return new FileInputStream(path.toFile());
	}

	private static String buildReceipt(InputStream basket, InputStream categories, List<String> excludedCategories) throws IOException {
		SalesTaxes salesTaxes = new SalesTaxes(excludedCategories);
		salesTaxes.read(categories);
		ShoppingBasket shoppingBasket = salesTaxes.parse(basket);
		Receipt receipt = salesTaxes.buildReceipt(shoppingBasket);
		return salesTaxes.write(receipt);
	}

	private static void writeRecipt(Path outputLocation, String receipt) throws IOException {
		Files.write(outputLocation, receipt.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
	}

}
