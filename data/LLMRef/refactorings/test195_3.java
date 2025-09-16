public class test195 {

    private static final String TEXT_HTML_UTF8 = "text/html;charset=utf-8";

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (response.isCommitted()) {
            String message = getMessage(model);
            logger.error(message);
            return;
        }
        response.setContentType(TEXT_HTML_UTF8);
        StringBuilder builder = new StringBuilder();
        Object timestamp = model.get("timestamp");
        Object message = model.get("message");
        Object trace = model.get("trace");
        if (response.getContentType() == null) {
            response.setContentType(getContentType());
        }
        builder.append("<html><body><h1>Whitelabel Error Page</h1>")
                .append("<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>")
                .append("<div id='created'>")
                .append(timestamp)
                .append("</div>")
                .append("<div>There was an unexpected error (type=")
                .append(htmlEscape(model.get("error")))
                .append(", status=")
                .append(htmlEscape(model.get("status")))
                .append(").</div>");
        if (message != null) {
            builder.append("<div>").append(htmlEscape(message)).append("</div>");
        }
        if (trace != null) {
            builder.append("<div style='white-space:pre-wrap;'>").append(htmlEscape(trace)).append("</div>");
        }
        builder.append("</body></html>");
        response.getWriter().append(builder.toString());
    }

}
