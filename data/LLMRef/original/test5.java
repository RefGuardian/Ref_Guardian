public class test5 {

    private Process startApplication() throws Exception {
    		File workingDirectory = getWorkingDirectory();
    		File serverPortFile = new File(this.outputLocation, "server.port");
    		serverPortFile.delete();
    		File archive = new File("build/spring-boot-server-tests-app/build/libs/spring-boot-server-tests-app-"
    				+ this.application.getContainer() + "." + this.application.getPackaging());
    		List<String> arguments = new ArrayList<>();
    		arguments.add(System.getProperty("java.home") + "/bin/java");
    		arguments.addAll(getArguments(archive, serverPortFile));
    		arguments.add("--server.servlet.register-default-servlet=true");
    		ProcessBuilder processBuilder = new ProcessBuilder(StringUtils.toStringArray(arguments));
    		if (workingDirectory != null) {
    			processBuilder.directory(workingDirectory);
    		}
    		Process process = processBuilder.start();
    		new ConsoleCopy(process.getInputStream(), System.out).start();
    		new ConsoleCopy(process.getErrorStream(), System.err).start();
    		this.httpPort = awaitServerPort(process, serverPortFile);
    		return process;
    	}
}
