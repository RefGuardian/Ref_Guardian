public class test28 {

    private DockerCommand getDockerComposeCommand(ProcessRunner processRunner) {
        DockerCliComposeVersionResponse response1 = getComposeVersionResponse(processRunner, "docker", "compose");
        logger.trace(LogMessage.format("Using Docker Compose %s", response1.version()));
        return new DockerCommand(response1.version(), List.of("docker", "compose"));
    }

    private DockerCommand getComposeVersionResponse(ProcessRunner processRunner, String... commands) {
        try {
            DockerCliComposeVersionResponse response = DockerJson.deserialize(
                    processRunner.run(commands[0], commands),
                    DockerCliComposeVersionResponse.class);
            return response;
        } catch (ProcessExitException ex) {
            // Ignore and try docker-compose
            return getComposeVersionResponse(processRunner, "docker-compose");
        } catch (ProcessStartException ex) {
            throw new DockerProcessStartException(
                    "Unable to start 'docker-compose' process or use 'docker compose'. Is docker correctly installed?",
                    ex);
        }
    }
}
