public class test28 {

    private DockerCommand getDockerComposeCommand(ProcessRunner processRunner) {
        DockerCliComposeVersionResponse response = deserializeDockerComposeVersionResponse(processRunner, "docker", "compose");
        return new DockerCommand(response.version(), List.of("docker", "compose"));
    }

    private DockerCliComposeVersionResponse deserializeDockerComposeVersionResponse(ProcessRunner processRunner, String... args) {
        try {
            return DockerJson.deserialize(
                processRunner.run(args),
                DockerCliComposeVersionResponse.class);
        } catch (ProcessStartException ex) {
            throw new DockerProcessStartException(
                "Unable to start 'docker-compose' process or use 'docker compose'. Is docker correctly installed?",
                ex);
        }
    }

}
