public class test29 {

    private static String addApplicationNameIfNecessary(String jdbcUrl, Environment environment) {
    			if (jdbcUrl.contains("&ApplicationName=") || jdbcUrl.contains("?ApplicationName=")) {
    				return jdbcUrl;
    			}
    			String applicationName = environment.getProperty("spring.application.name");
    			if (!StringUtils.hasText(applicationName)) {
    				return jdbcUrl;
    			}
    			StringBuilder jdbcUrlBuilder = new StringBuilder(jdbcUrl);
    			if (!jdbcUrl.contains("?")) {
    				jdbcUrlBuilder.append("?");
    			}
    			else if (!jdbcUrl.endsWith("&")) {
    				jdbcUrlBuilder.append("&");
    			}
    			return jdbcUrlBuilder.append("ApplicationName")
    				.append('=')
    				.append(URLEncoder.encode(applicationName, StandardCharsets.UTF_8))
    				.toString();
    		}
}
