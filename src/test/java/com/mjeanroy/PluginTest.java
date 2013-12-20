package com.mjeanroy;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.Executor;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PluginTest extends AbstractMojoTestCase {

	@Test
	public void testCleanGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-clean/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntCleanMojo myMojo = (GruntCleanMojo) lookupMojo("clean", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();
		verifyCommand(executor, "grunt", "clean", "--no-color");
	}

	@Test
	public void testLintGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-lint/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntLintMojo myMojo = (GruntLintMojo) lookupMojo("lint", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();
		verifyCommand(executor, "grunt", "lint", "--no-color");
	}

	@Test
	public void testDependenciesGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-dependencies/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntDependenciesMojo myMojo = (GruntDependenciesMojo) lookupMojo("install", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();

		ArgumentCaptor<CommandLine> cmdCaptor = ArgumentCaptor.forClass(CommandLine.class);
		verify(executor, times(2)).execute(cmdCaptor.capture());

		List<CommandLine> cmd = cmdCaptor.getAllValues();
		verifyCommandArguments(cmd.get(0), "npm", "install", "--no-color");
		verifyCommandArguments(cmd.get(1), "bower", "install", "--no-color");
	}

	@Test
	public void testTestGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-test/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntTestMojo myMojo = (GruntTestMojo) lookupMojo("test", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();
		verifyCommand(executor, "grunt", "test", "--no-color");
	}

	@Test
	public void testSkipTestGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-skip-test/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntTestMojo myMojo = (GruntTestMojo) lookupMojo("test", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();
		verify(executor, never()).execute(any(CommandLine.class));
	}

	@Test
	public void testBuildGoal() throws Exception {
		File pom = getTestFile("src/test/resources/goal-build/pom.xml");
		assertNotNull(pom);
		assertTrue(pom.exists());

		Executor executor = mock(Executor.class);
		GruntBuildMojo myMojo = (GruntBuildMojo) lookupMojo("build", pom);
		assertThat(myMojo).isNotNull();

		myMojo.setExecutor(executor);
		myMojo.execute();
		verifyCommand(executor, "grunt", "build", "--no-color");
	}

	private void verifyCommand(Executor executor, String command, String... args) throws Exception {
		ArgumentCaptor<CommandLine> cmdCaptor = ArgumentCaptor.forClass(CommandLine.class);
		verify(executor).execute(cmdCaptor.capture());
		verifyCommandArguments(cmdCaptor.getValue(), command, args);
	}

	private void verifyCommandArguments(CommandLine cmd, String command, String... args) throws Exception {
		assertThat(cmd.getExecutable()).isEqualTo(command);
		assertThat(cmd.getArguments()).containsExactly(args);
	}
}
