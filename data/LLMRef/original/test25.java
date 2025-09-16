public class test25 {

    @Test
    	void runLifecycle() throws IOException {
    		File composeFile = createComposeFile("redis-compose.yaml");
    		String projectName = UUID.randomUUID().toString();
    		DockerCli cli = new DockerCli(null, new DockerComposeOptions(DockerComposeFile.of(composeFile),
    				Collections.emptySet(), List.of("--project-name=" + projectName)));
    		try {
    			// Verify that no services are running (this is a fresh compose project)
    			List<DockerCliComposePsResponse> ps = cli.run(new ComposePs());
    			assertThat(ps).isEmpty();
    			// List the config and verify that redis is there
    			DockerCliComposeConfigResponse config = cli.run(new ComposeConfig());
    			assertThat(config.services()).containsOnlyKeys("redis");
    			assertThat(config.name()).isEqualTo(projectName);
    			// Run up
    			cli.run(new ComposeUp(LogLevel.INFO, Collections.emptyList()));
    			// Run ps and use id to run inspect on the id
    			ps = cli.run(new ComposePs());
    			assertThat(ps).hasSize(1);
    			String id = ps.get(0).id();
    			List<DockerCliInspectResponse> inspect = cli.run(new Inspect(List.of(id)));
    			assertThat(inspect).isNotEmpty();
    			assertThat(inspect.get(0).id()).startsWith(id);
    			// Run stop, then run ps and verify the services are stopped
    			cli.run(new ComposeStop(Duration.ofSeconds(10), Collections.emptyList()));
    			ps = cli.run(new ComposePs());
    			assertThat(ps).isEmpty();
    			// Run start, verify service is there, then run down and verify they are gone
    			cli.run(new ComposeStart(LogLevel.INFO, Collections.emptyList()));
    			ps = cli.run(new ComposePs());
    			assertThat(ps).hasSize(1);
    			cli.run(new ComposeDown(Duration.ofSeconds(10), Collections.emptyList()));
    			ps = cli.run(new ComposePs());
    			assertThat(ps).isEmpty();
    		}
    		finally {
    			// Clean up in any case
    			quietComposeDown(cli);
    		}
    	}
}
