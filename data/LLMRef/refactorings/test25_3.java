public class test25 {

    @Test
    	void runLifecycle() throws IOException {
    		File composeFile = createComposeFile("redis-compose.yaml");
    		String projectName = UUID.randomUUID().toString();
    		DockerCli cli = new DockerCli(null, new DockerComposeOptions(DockerComposeFile.of(composeFile),
    				Collections.emptySet(), List.of("--project-name=" + projectName)));
    		try {
    			verifyServicesNotRunning(cli);
    			verifyRedisIsConfigured(cli, projectName);
    			startServices(cli);
    			String id = inspectService(cli);
    			verifyServicesAreStopped(cli);
    			startServicesAndVerify(cli);
    			stopServicesAndVerify(cli);
    		}
    		finally {
    			quietComposeDown(cli);
    		}
    	}

    private void verifyServicesNotRunning(DockerCli cli) {
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        assertThat(ps).isEmpty();
    }

    private void verifyRedisIsConfigured(DockerCli cli, String projectName) {
        DockerCliComposeConfigResponse config = cli.run(new ComposeConfig());
        assertThat(config.services()).containsOnlyKeys("redis");
        assertThat(config.name()).isEqualTo(projectName);
    }

    private void startServices(DockerCli cli) {
        cli.run(new ComposeUp(LogLevel.INFO, Collections.emptyList()));
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        assertThat(ps).hasSize(1);
    }

    private String inspectService(DockerCli cli) {
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        String id = ps.get(0).id();
        List<DockerCliInspectResponse> inspect = cli.run(new Inspect(List.of(id)));
        assertThat(inspect).isNotEmpty();
        assertThat(inspect.get(0).id()).startsWith(id);
        return id;
    }

    private void verifyServicesAreStopped(DockerCli cli) {
        cli.run(new ComposeStop(Duration.ofSeconds(10), Collections.emptyList()));
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        assertThat(ps).isEmpty();
    }

    private void startServicesAndVerify(DockerCli cli) {
        cli.run(new ComposeStart(LogLevel.INFO, Collections.emptyList()));
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        assertThat(ps).hasSize(1);
    }

    private void stopServicesAndVerify(DockerCli cli) {
        cli.run(new ComposeDown(Duration.ofSeconds(10), Collections.emptyList()));
        List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
        assertThat(ps).isEmpty();
    }
}
