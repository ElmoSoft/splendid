package net.elmosoft.splendid.service.runner;

public class BuildResult {

	public static final int SUCCESS = 0;

	public static final int FAILED = 1;

	private static int exitResult = FAILED;

	public static int getExitResult() {
		return exitResult;
	}

	public static void setExitResult(int exitCode) {
		exitResult = exitCode;
	}

}