package net.elmosoft.splendid.browser;

public enum Browsers {
	FIREFOX("FIREFOX"),
	CHROME("CHROME"),
	ANDROID("ANDROID"),
	IOS("IOS");

	private String type;

	private Browsers(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
