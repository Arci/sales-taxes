package it.arcidiacono.salestaxes.writer;

import org.apache.commons.lang3.StringUtils;

import it.arcidiacono.salestaxes.model.Purchase;
import it.arcidiacono.salestaxes.model.Receipt;

/**
 * Writes a {@linkplain Receipt} in the format of the problem statement, as follows:
 *
 * <pre>
 * 1 imported bottle of perfume: 32.19
 * 1 bottle of perfume: 20.89
 * 1 packet of headache pills: 9.75
 * 1 imported box of chocolates: 11.85
 * Sales Taxes: 6.70
 * Total: 74.68
 *
 * <pre/>
 */
public class TexualReceiptWriter implements ReceiptWriter {

	@Override
	public String write(Receipt receipt) {
		if (receipt == null) {
			return StringUtils.EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		for (Purchase purchase : receipt.getPurchases()) {
			builder.append(purchase.getQuantity())
					.append(" ")
					.append(purchase.getProduct().getName())
					.append(": ")
					.append(purchase.getPrice())
					.append("\n");

		}
		builder.append("Sales Taxes: ")
				.append(receipt.getSalesTaxes())
				.append("\nTotal: ")
				.append(receipt.getTotal());
		return builder.toString();
	}

}
