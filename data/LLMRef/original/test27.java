public class test27 {

    private List<String> createCommand(Type type) {
    		return switch (type) {
    			case DOCKER -> new ArrayList<>(this.dockerCommands.get(type).command());
    			case DOCKER_COMPOSE -> {
    				List<String> result = new ArrayList<>(this.dockerCommands.get(type).command());
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
    				yield result;
    			}
    		};
    	}
}
