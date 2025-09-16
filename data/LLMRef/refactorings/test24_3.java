public class test24 {

    private static final String PROFILES_YAML = "profiles.yaml";

    @Test
    void shouldWorkWithProfiles(@TempDir Path tempDir) throws IOException {
        // Profile 1 contains redis1 and redis3
        // Profile 2 contains redis2 and redis3
        File composeFile = createComposeFile(tempDir, PROFILES_YAML).toFile();
        DefaultDockerCompose dockerComposeWithProfile1 = createDockerComposeWithProfile(tempDir, composeFile, "1");
        DefaultDockerCompose dockerComposeWithProfile2 = createDockerComposeWithProfile(tempDir, composeFile, "2");
        DefaultDockerCompose dockerComposeWithAllProfiles = createDockerComposeWithProfile(tempDir, composeFile, "1", "2");
        dockerComposeWithAllProfiles.up(LogLevel.DEBUG);
        try {
            List<RunningService> runningServicesProfile1 = dockerComposeWithProfile1.getRunningServices();
            assertThatContainsService(runningServicesProfile1, "redis1");
            assertThatDoesNotContainService(runningServicesProfile1, "redis2");
            assertThatContainsService(runningServicesProfile1, "redis3");

            List<RunningService> runningServicesProfile2 = dockerComposeWithProfile2.getRunningServices();
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
        } finally {
            dockerComposeWithAllProfiles.down(Duration.ofSeconds(10));
        }
    }

    private DefaultDockerCompose createDockerComposeWithProfile(Path tempDir, File composeFile, String... profiles) {
        return new DefaultDockerCompose(new DockerCli(tempDir.toFile(),
                new DockerComposeOptions(DockerComposeFile.of(composeFile), Set.of(profiles), Collections.emptyList())),
                null);
    }

    private File createComposeFile(Path tempDir, String fileName) {
        return tempDir.resolve(fileName).toFile();
    }
}
