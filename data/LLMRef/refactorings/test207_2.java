public class test207 {

    List<String> get() {
        if (!isLoggedBasePackageInfo()) {
            logBasePackageInfo();
        }
        return this.packages;
    }

    private void logBasePackageInfo() {
        if (this.packages.isEmpty()) {
            if (logger.isWarnEnabled()) {
                logger.warn("@EnableAutoConfiguration was declared on a class "
                        + "in the default package. Automatic @Repository and "
                        + "@Entity scanning is not enabled.");
            }
        } else {
            if (logger.isDebugEnabled()) {
                String packageNames = StringUtils.collectionToCommaDelimitedString(this.packages);
                logger.debug("@EnableAutoConfiguration was declared on a class in the package '" + packageNames
                        + "'. Automatic @Repository and @Entity scanning is enabled.");
            }
        }
        this.loggedBasePackageInfo = true;
    }

    private boolean isLoggedBasePackageInfo() {
        return this.loggedBasePackageInfo;
    }
}
