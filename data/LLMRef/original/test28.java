public class test28 {

    private DockerCommand getDockerComposeCommand(ProcessRunner processRunner) {
    			try {
    				DockerCliComposeVersionResponse response = DockerJson.deserialize(
    						processRunner.run("docker", "compose", "version", "--format", "json"),
    						DockerCliComposeVersionResponse.class);
    				logger.trace(LogMessage.format("Using Docker Compose %s", response.version()));
    				return new DockerCommand(response.version(), List.of("docker", "compose"));
    			}
    			catch (ProcessExitException ex) {
    				// Ignore and try docker-compose
    			}
    			try {
    				DockerCliComposeVersionResponse response = DockerJson.deserialize(
    						processRunner.run("docker-compose", "version", "--format", "json"),
    						DockerCliComposeVersionResponse.class);
    				logger.trace(LogMessage.format("Using docker-compose %s", response.version()));
    				return new DockerCommand(response.version(), List.of("docker-compose"));
    			}
    			catch (ProcessStartException ex) {
    				throw new DockerProcessStartException(
    						"Unable to start 'docker-compose' process or use 'docker compose'. Is docker correctly installed?",
    						ex);
    			}
    		}
}
