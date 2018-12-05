package it.arcidiacono.salestaxes.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public abstract class ResourceBasedTest {

	protected InputStream getResourceStream(String directory, String name) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		String path = directory + File.separator + name;
		InputStream stream = classLoader.getResourceAsStream(path);
		if (stream == null) {
			throw new IOException("resource: '" + path + "' cannot be found");
		}
		return stream;
	}

	protected String getResource(String directory, String name) throws IOException {
		InputStream stream = getResourceStream(directory, name);
		return IOUtils.toString(stream, StandardCharsets.UTF_8);
	}

}
