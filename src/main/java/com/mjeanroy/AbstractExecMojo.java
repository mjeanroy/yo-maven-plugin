/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mjeanroy;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

public abstract class AbstractExecMojo extends AbstractMojo {

	@Parameter(defaultValue = "${os.name}", readonly = true)
	protected String osName;

	@Parameter(defaultValue = "false", readonly = true)
	protected boolean color;

	@Parameter(defaultValue = "${project.basedir}", readonly = true)
	protected File gruntDirectory;

	// For tests
	private Executor executor;

	protected void npmInstall() throws MojoExecutionException {
		logAndExecute("npm", "install");
	}

	protected void logAndExecute(String command, String... args) throws MojoExecutionException {
		String cmd = command + " ";
		for (String arg : args) {
			cmd += arg + " ";
		}

		if (!color) {
			cmd += "--no-color";
		}

		cmd = cmd.trim();
		getLog().info(cmd);
		executeCommand(cmd);
	}

	protected void executeCommand(String command) throws MojoExecutionException {
		try {
			if (isWindows()) {
				command = "cmd /c " + command;
			}

			CommandLine cmdLine = CommandLine.parse(command);
			Executor executor = getExecutor();
			executor.setWorkingDirectory(gruntDirectory);
			executor.execute(cmdLine);
		}
		catch (IOException e) {
			throw new MojoExecutionException("Error during : " + command, e);
		}
	}

	protected boolean isWindows() {
		return osName != null && osName.startsWith("Windows");
	}

	// For tests
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	private Executor getExecutor() {
		return executor != null ? executor : new DefaultExecutor();
	}
}
