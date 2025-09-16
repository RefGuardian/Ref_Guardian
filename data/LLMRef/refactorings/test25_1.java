public class test25 {

	@Test
	void runLifecycle() throws IOException {
		File composeFile = createComposeFile("redis-compose.yaml");
		String projectName = UUID.randomUUID().toString();
		DockerCli cli = new DockerCli(null, new DockerComposeOptions(DockerComposeFile.of(composeFile),
				Collections.emptySet(), List.of("--project-name=" + projectName)));
		try {
			verifyNoRunningServices(cli);
			List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
			assertThat(ps).isEmpty();
			DockerCliComposeConfigResponse config = listConfigAndVerifyRedis(cli);
			assertThat(config.services()).containsOnlyKeys("redis");
			assertThat(config.name()).isEqualTo(projectName);
			runComposUp(cli);
			List<DockerCliInspectResponse> inspect = runPsAndInspect(cli);
			verifyStoppedServices(cli);
			runAndVerifyStart(cli);
			verifyServiceAndClean(cli);

		} finally {
			quietComposeDown(cli);
		}
	}

	private void verifyNoRunningServices(DockerCli cli) {
		List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
		assertThat(ps).isEmpty();
	}

	private DockerCliComposeConfigResponse listConfigAndVerifyRedis(DockerCli cli) {
		DockerCliComposeConfigResponse config = cli.run(new ComposeConfig());
		return config;
	}

	private void runComposUp(DockerCli cli) {
		cli.run(new ComposeUp(LogLevel.INFO, Collections.emptyList()));
	}

	private List<DockerCliInspectResponse> runPsAndInspect(DockerCli cli) {
		List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
		assertThat(ps).hasSize(1);
		String id = ps.get(0).id();
		return cli.run(new Inspect(List.of(id)));
	}

	private void verifyStoppedServices(DockerCli cli) {
		cli.run(new ComposeStop(Duration.ofSeconds(10), Collections.emptyList()));
		List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
		assertThat(ps).isEmpty();
	}

	private void runAndVerifyStart(DockerCli cli) {
		cli.run(new ComposeStart(LogLevel.INFO, Collections.emptyList()));
		List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
		assertThat(ps).hasSize(1);
		cli.run(new ComposeDown(Duration.ofSeconds(10), Collections.emptyList()));
	}

	private void verifyServiceAndClean(DockerCli cli) {
		List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
		assertThat(ps).isEmpty();
	}

}
