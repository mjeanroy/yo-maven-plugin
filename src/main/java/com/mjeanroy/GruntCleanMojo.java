package com.mjeanroy;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
public class GruntCleanMojo extends AbstractExecMojo {

	@Parameter(defaultValue = "clean", required = true)
	private String cleanArgs;

	public void execute() throws MojoExecutionException {
		logAndExecute("grunt", cleanArgs);
	}

}
