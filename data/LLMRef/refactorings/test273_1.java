public class test273 {

    private void check(String[] command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            Assert.state(process.waitFor(30, TimeUnit.SECONDS), "Process did not exit within 30 seconds");
            Assert.state(process.exitValue() == 0, () -> "Process exited with %d".formatted(process.exitValue()));
            process.destroy();
        }
        catch (Exception ex) {
            String path = processBuilder.environment().get("PATH");
            if (MAC_OS && path != null && !path.contains(USR_LOCAL_BIN)
                    && !command[0].startsWith(USR_LOCAL_BIN + "/")) {
                String[] localCommand = cloneCommandWithPathPrepended(command);
                check(localCommand);
                return;
            }
            throw new RuntimeException(
                    "Unable to start process '%s'".formatted(StringUtils.arrayToDelimitedString(command, " ")));
        }
    }

    private String[] cloneCommandWithPathPrepended(String[] command) {
        String[] localCommand = command.clone();
        localCommand[0] = USR_LOCAL_BIN + "/" + localCommand[0];
        return localCommand;
    }
}
