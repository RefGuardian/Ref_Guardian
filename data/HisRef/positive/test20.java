@Value
record test20(
        @Size(min = 1, max = 2) String bar,
        @NotEmpty @Size(min = 1, max = 2) String notEmptyBar,
        @Size(min = 3, max = 4) List<Object> objects,
        Optional<List<Object>> optionalObjects
) {

}