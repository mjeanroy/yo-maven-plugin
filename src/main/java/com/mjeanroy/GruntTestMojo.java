package com.mjeanroy;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "test", defaultPhase = LifecyclePhase.TEST)
public class GruntTestMojo extends AbstractExecMojo {

	@Parameter(defaultValue = "test", required = true)
	private String testArgs;

	@Parameter(defaultValue = "${maven.test.skip}", required = false)
	private boolean skipTests;

	public void execute() throws MojoExecutionException {
		if (!skipTests) {
			logAndExecute("grunt", testArgs);
		} else {
			getLog().info("Tests are skipped");
		}
	}
}
