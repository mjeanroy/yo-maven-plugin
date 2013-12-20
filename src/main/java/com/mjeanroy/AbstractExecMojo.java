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
