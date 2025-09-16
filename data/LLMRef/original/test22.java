public class test22 {

    @Test
    	void getRunningServicesReturnsServices() {
    		String id = "123";
    		DockerCliComposePsResponse psResponse = new DockerCliComposePsResponse(id, "name", "redis", "running");
    		Map<String, ExposedPort> exposedPorts = Collections.emptyMap();
    		Config config = new Config("redis", Map.of("spring", "boot"), exposedPorts, List.of("a=b"));
    		NetworkSettings networkSettings = null;
    		HostConfig hostConfig = null;
    		DockerCliInspectResponse inspectResponse = new DockerCliInspectResponse(id, config, networkSettings,
    				hostConfig);
    		willReturn(List.of(psResponse)).given(this.cli).run(new DockerCliCommand.ComposePs());
    		willReturn(List.of(inspectResponse)).given(this.cli).run(new DockerCliCommand.Inspect(List.of(id)));
    		DefaultDockerCompose compose = new DefaultDockerCompose(this.cli, HOST);
    		List<RunningService> runningServices = compose.getRunningServices();
    		assertThat(runningServices).hasSize(1);
    		RunningService runningService = runningServices.get(0);
    		assertThat(runningService.name()).isEqualTo("name");
    		assertThat(runningService.image()).hasToString("docker.io/library/redis");
    		assertThat(runningService.host()).isEqualTo(HOST);
    		assertThat(runningService.ports().getAll()).isEmpty();
    		assertThat(runningService.env()).containsExactly(entry("a", "b"));
    		assertThat(runningService.labels()).containsExactly(entry("spring", "boot"));
    	}
}
