package jp.co.internous.mushrooms.model.form;

import java.io.Serializable;

public class SearchForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private int category;
	private String keywords;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
	
}
