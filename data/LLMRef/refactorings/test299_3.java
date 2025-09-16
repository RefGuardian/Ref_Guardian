public class test299 {

    /**
     * Encodes the number as a JSON string.
     * @param number a finite value. May not be {@link Double#isNaN() NaNs} or
     * {@link Double#isInfinite() infinities}.
     * @return the encoded value
     * @throws JSONException if an error occurs
     */
    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Number must be non-null");
        }

        double doubleValue = extractDoubleValue(number);
        JSON.checkDouble(doubleValue);

        // the original returns "-0" instead of "-0.0" for negative zero
        if (number.equals(NEGATIVE_ZERO)) {
            return "-0";
        }

        return number.toString();
    }

    private static double extractDoubleValue(Number number) {
        return number.doubleValue();
    }
}
