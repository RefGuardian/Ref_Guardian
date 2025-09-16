public class test201 {

    private static final String APPLICATION_NAME = "Application Name";
    private static final String DEFAULT_ERROR_MESSAGE = "This application has no configured error view, so you are seeing this as a fallback.";

    /**
     * Render a default HTML "Whitelabel Error Page".
     * <p>
     * Useful when no other error view is available in the application.
     * @param responseBody the error response being built
     * @param error the error data as a map
     * @return a Publisher of the {@link ServerResponse}
     */
    protected Mono<ServerResponse> renderDefaultErrorView(ServerResponse.BodyBuilder responseBody,
                                                           Map<String, Object> error) {
        StringBuilder builder = new StringBuilder();
        Date timestamp = (Date) error.get("timestamp");
        Object message = error.get("message");
        Object trace = error.get("trace");
        Object requestId = error.get("requestId");
        builder.append("<html><body><h1>Whitelabel Error Page</h1>")
                .append("<p>").append(DEFAULT_ERROR_MESSAGE).append("</p>")
                .append("<div id='created'>")
                .append(timestamp)
                .append("</div>")
                .append("<div>[")
                .append(requestId)
                .append("] There was an unexpected error (type=")
                .append(htmlEscape(error.get("error")))
                .append(", status=")
                .append(htmlEscape(error.get("status")))
                .append(").</div>");
        if (message != null) {
            builder.append("<div>").append(htmlEscape(message)).append("</div>");
        }
        if (trace != null) {
            builder.append("<div style='white-space:pre-wrap;'>").append(htmlEscape(trace)).append("</div>");
        }
        builder.append("</body></html>");
        return responseBody.bodyValue(builder.toString());
    }
}
