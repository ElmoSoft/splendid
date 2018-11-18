package net.elmosoft.splendid.config;

public class GlobalConfig {

	private volatile static GlobalConfig instance;

	private GlobalConfig() {
	}

	public static GlobalConfig getInstance() {
		if (instance == null) {
			synchronized (GlobalConfig.class) {
				if (instance == null) {
					instance = new GlobalConfig();
				}
			}
		}
		return instance;
	}

	private String timestamp;
	private String backOfficeAppURL;
	private String clientAppURL;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getBackOfficeAppURL() {
		return backOfficeAppURL;
	}

	public void setBackOfficeAppURL(String backOfficeAppURL) {
		this.backOfficeAppURL = backOfficeAppURL;
	}

	public String getClientAppURL() {
		return clientAppURL;
	}

	public void setClientAppURL(String clientAppURL) {
		this.clientAppURL = clientAppURL;
	}
}
