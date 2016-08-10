package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class BuildInformationHelper {

	private static final Properties properties = initProperties();

	private static Properties initProperties() {
		InputStream inputStream = BuildInformationHelper.class.getResourceAsStream("/build-info.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read properties file", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return properties;
	}

	/** Hide default constructor. */
	private BuildInformationHelper() {
	}

	/**
	 * Gets the Git SHA-1.
	 * 
	 * @return A {@code String} with the Git SHA-1.
	 */
	public static String getGitSha1() {
		String gitSha = properties.getProperty("git-sha-1");
		// The Heroku slug compiler deletes the .git directory from the working copy before the build process starts. 
		if ("${buildNumber}".equals(gitSha)) {
			return System.getenv("SOURCE_VERSION");
		} else {
			return gitSha;
		}
	}

	public static String getBuildTimestamp() {
		return properties.getProperty("build-timestamp");
	}

	public static String getVersion() {
		return properties.getProperty("version");
	}

}
