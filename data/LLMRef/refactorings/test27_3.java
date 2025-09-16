public class test27 {

    private List<String> createCommand(Type type) {
        return switch (type) {
            case DOCKER -> createDockerCommand();
            case DOCKER_COMPOSE -> createDockerComposeCommand();
        };
    }

    private List<String> createDockerCommand() {
        return new ArrayList<>(this.dockerCommands.get(Type.DOCKER).command());
    }

    private List<String> createDockerComposeCommand() {
        List<String> result = new ArrayList<>(this.dockerCommands.get(Type.DOCKER_COMPOSE).command());
        DockerComposeFile composeFile = this.dockerComposeOptions.composeFile();
        if (composeFile != null) {
            for (File file : composeFile.getFiles()) {
                result.add("--file");
                result.add(file.getPath());
            }
        }
        result.add("--ansi");
        result.add("never");
        Set<String> activeProfiles = this.dockerComposeOptions.activeProfiles();
        if (!CollectionUtils.isEmpty(activeProfiles)) {
            for (String profile : activeProfiles) {
                result.add("--profile");
                result.add(profile);
            }
        }
        List<String> arguments = this.dockerComposeOptions.arguments();
        if (!CollectionUtils.isEmpty(arguments)) {
            result.addAll(arguments);
        }
        return result;
    }
}
