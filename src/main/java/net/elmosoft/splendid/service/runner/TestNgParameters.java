package net.elmosoft.splendid.service.runner;

public class TestNgParameters {

	private final static TestNgParameters instance = new TestNgParameters();

	public final static TestNgParameters getInstance() {
		return instance;
	}

	private String[] suites;

	private String[] groups;

	private String proxyPort;

	private int threadCount;

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setSuites(String[] suites) {
		this.suites = suites;
	}

	public String[] getSuites() {
		return suites;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public String[] getGroups() {
		return groups;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
