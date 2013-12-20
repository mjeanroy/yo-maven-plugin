package com.mjeanroy;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "lint", defaultPhase = LifecyclePhase.COMPILE)
public class GruntLintMojo extends AbstractExecMojo {

	@Parameter(defaultValue = "lint", required = true)
	private String lintArgs;

	public void execute() throws MojoExecutionException {
		logAndExecute("grunt", lintArgs);
	}
}
