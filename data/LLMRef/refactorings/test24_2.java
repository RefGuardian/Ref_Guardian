public class test24 {

    @Test
    	void shouldWorkWithProfiles(@TempDir Path tempDir) throws IOException {
    		// Profile 1 contains redis1 and redis3
    		// Profile 2 contains redis2 and redis3
    		File composeFile = createComposeFile(tempDir, "profiles.yaml").toFile();
    		DefaultDockerCompose dockerComposeWithAllProfiles = new DefaultDockerCompose(new DockerCli(tempDir.toFile(),
    				new DockerComposeOptions(DockerComposeFile.of(composeFile), Set.of("1", "2"), Collections.emptyList())),
    				null);
    		dockerComposeWithAllProfiles.up(LogLevel.DEBUG);
    		try {
    			List<RunningService> runningServicesProfile1 = dockerComposeWithAllProfiles.getRunningServices();
    			assertThatContainsService(runningServicesProfile1, "redis1");
    			assertThatDoesNotContainService(runningServicesProfile1, "redis2");
    			assertThatContainsService(runningServicesProfile1, "redis3");
    
    			List<RunningService> runningServicesProfile2 = dockerComposeWithAllProfiles.getRunningServices();
    			assertThatDoesNotContainService(runningServicesProfile2, "redis1");
    			assertThatContainsService(runningServicesProfile2, "redis2");
    			assertThatContainsService(runningServicesProfile2, "redis3");
    
    			// Assert that redis3 is started only once and is shared between profile 1 and
    			// profile 2
    			assertThat(dockerComposeWithAllProfiles.getRunningServices()).hasSize(3);
    			RunningService redis3Profile1 = findService(runningServicesProfile1, "redis3");
    			RunningService redis3Profile2 = findService(runningServicesProfile2, "redis3");
    			assertThat(redis3Profile1).isNotNull();
    			assertThat(redis3Profile2).isNotNull();
    			assertThat(redis3Profile1.name()).isEqualTo(redis3Profile2.name());
    		}
    		finally {
    			dockerComposeWithAllProfiles.down(Duration.ofSeconds(10));
    		}
    	}
}
