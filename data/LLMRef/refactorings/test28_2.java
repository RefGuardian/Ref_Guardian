public class test28 {

    private DockerCommand getDockerComposeCommand(ProcessRunner processRunner) {
        DockerCliComposeVersionResponse response = deserializeDockerComposeVersionResponse(processRunner, "docker", "compose", "version", "--format", "json");
        logger.trace(LogMessage.format("Using Docker Compose %s", response.version()));
        return new DockerCommand(response.version(), List.of("docker", "compose"));
    }

    private DockerCliComposeVersionResponse deserializeDockerComposeVersionResponse(ProcessRunner processRunner, String... command) {
        try {
            return DockerJson.deserialize(processRunner.run(command), DockerCliComposeVersionResponse.class);
        } catch (ProcessExitException ex) {
            throw new DockerProcessStartException(
                    "Unable to start 'docker-compose' process or use 'docker compose'. Is docker correctly installed?",
                    ex);
        }
    }
}
