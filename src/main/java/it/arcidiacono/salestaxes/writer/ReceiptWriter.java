package it.arcidiacono.salestaxes.writer;

import it.arcidiacono.salestaxes.model.Receipt;

public interface ReceiptWriter {

	/**
	 * Gives the string representation of a {@linkplain Receipt}.
	 *
	 * @param  receipt
	 *                 the receipt to format
	 * @return
	 * 				the string representation of the given {@linkplain Receipt}.
	 */
	String write(Receipt receipt);

}
