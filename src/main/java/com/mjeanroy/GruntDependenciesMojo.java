package com.mjeanroy;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INITIALIZE)
public class GruntDependenciesMojo extends AbstractExecMojo {

	@Parameter(defaultValue = "install", required = false)
	private String bowerArgs;

	@Parameter(defaultValue = "true", required = false)
	private boolean bower;

	public void execute() throws MojoExecutionException {
		npmInstall();

		if (bower && bowerArgs != null) {
			logAndExecute("bower", bowerArgs);
		}
	}
}
