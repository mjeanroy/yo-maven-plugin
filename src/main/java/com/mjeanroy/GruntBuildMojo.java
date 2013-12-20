package com.mjeanroy;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "build", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class GruntBuildMojo extends AbstractExecMojo {

	@Parameter(defaultValue = "build", required = true)
	private String buildArgs;

	public void execute() throws MojoExecutionException {
		logAndExecute("grunt", buildArgs);
	}
}
