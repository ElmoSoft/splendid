package net.elmosoft.splendid.browser;

public enum Browsers {
	FF("FF"),
	CHROME("CHROME");

	private String type;

	private Browsers(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
