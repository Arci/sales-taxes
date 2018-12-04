package it.arcidiacono.salestaxes.model.tax;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Categories {

	private Set<String> categories;

	public Categories() {
		categories = new HashSet<String>();
	}

	public void addCategory(String category) {
		this.categories.add(category);
	}

	public Collection<String> categoriesgetCategories() {
		return new HashSet<String>(categories);
	}

}
