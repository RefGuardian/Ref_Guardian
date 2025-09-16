public class test260 {

    private void configureDateFormat(Jackson2ObjectMapperBuilder builder) {
        // We support a fully qualified class name extending DateFormat or a date
        // pattern string value
        String dateFormat = this.jacksonProperties.getDateFormat();
        if (dateFormat != null) {
            try {
                Class<?> dateFormatClass = ClassUtils.forName(dateFormat, null);
                builder.dateFormat((DateFormat) BeanUtils.instantiateClass(dateFormatClass));
            }
            catch (ClassNotFoundException ex) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                // Since Jackson 2.6.3 we always need to set a TimeZone (see
                // gh-4170). If none in our properties fallback to the Jackson's
                // default
                TimeZone timeZone = this.jacksonProperties.getTimeZone();
                if (timeZone == null) {
                    timeZone = new ObjectMapper().getSerializationConfig().getTimeZone();
                }
                simpleDateFormat.setTimeZone(timeZone);
                builder.dateFormat(simpleDateFormat);
            }
        }
    }
}
