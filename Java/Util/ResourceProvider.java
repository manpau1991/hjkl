package de.uni_passau.sep.portalgolf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.uni_passau.sep.portalgolf.SystemConstants;

import static de.uni_passau.sep.portalgolf.SystemConstants.*;

/**
 * This class provides transparent access to resources of this applications.
 * Resources can be loaded using their name and are available as File or
 * Streams.
 */
public class ResourceProvider {

	/**
	 * The path to the base of the resources
	 */
	private final File basePath;

	/**
	 * Default Constructor sets the base path and creates the directory if
	 * necessary
	 */
	public ResourceProvider() {
		String homepath = System.getProperty("user.home");
		this.basePath = new File(homepath + System.getProperty("file.separator") + RESOURCE_FOLDER);
		if (!this.basePath.exists()) {
			this.basePath.mkdir();
		}
	}

	/**
	 * Provides a resource as File. Uses external resources a internal Jar File
	 * resource cannot be loaded as File
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as File
	 * @return The resource as File
	 */
	public File getResourceAsFile(String resource) {
		return getExternalResourceAsFile(resource);
	}

	/**
	 * Provides a resource as InputStream. Uses external resources if available
	 * and internal only if no external is available.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as InputStream
	 * @return The resource as InputStream
	 */
	public InputStream getResourceAsInputStream(String resource) {
		return getExternalResourceAsInputStream(resource) != null ? getExternalResourceAsInputStream(resource)
				: getLocalResourceAsInputStream(resource);
	}

	/**
	 * Provides a resource as File. Uses external resources as internal Jar File
	 * resource cannot be written to.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as OutputStream
	 * @return The resource as OutputStream
	 */
	public OutputStream getResourceAsOutputStream(String resource) {
		return getExternalResourceAsOutputStream(resource);
	}

	/**
	 * Provides a local resource as InputStream.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as InputStream
	 * @return The resource as InputStream
	 */
	public InputStream getLocalResourceAsInputStream(String resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null!");
		}
		return getClass().getResourceAsStream(resource);
	}

	/**
	 * Provides a external resource as File.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as File
	 * @return The resource as File
	 */
	public File getExternalResourceAsFile(String resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null!");
		}
		return new File(this.basePath.getAbsolutePath() + System.getProperty("file.separator") + resource);
	}

	/**
	 * Provides a external resource as InputStream.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as InputStream
	 * @return The resource as InputStream
	 */
	public InputStream getExternalResourceAsInputStream(String resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null!");
		}
		try {
			return new FileInputStream(getResourceAsFile(resource));
		} catch (FileNotFoundException e) {
			Logger.getLogger(SystemConstants.LOGGER).log(Level.FINE, "Requested non-existant file: " + e.getMessage(),
					e);
			return null;
		}
	}

	/**
	 * Provides a external resource as OutputStream. If the resource does not
	 * exist, it is created.
	 * 
	 * @param resource
	 *            The name of the resource to be loaded as OutputStream
	 * @return The resource as OutputStream
	 */
	public OutputStream getExternalResourceAsOutputStream(String resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null!");
		}
		File output = getResourceAsFile(resource);
		if (!output.exists()) {
			try {
				if (!output.getParentFile().exists()) {
					output.getParentFile().mkdirs();
				}
				output.createNewFile();
			} catch (IOException e) {
				Logger.getLogger(SystemConstants.LOGGER).log(Level.WARNING,
						e.getClass() + " happend: " + e.getMessage(), e);
				return null;
			}
		}
		try {
			return new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			Logger.getLogger(SystemConstants.LOGGER).log(Level.FINE, "Requested non-existant file: " + e.getMessage(),
					e);
			return null;
		}
	}
}
