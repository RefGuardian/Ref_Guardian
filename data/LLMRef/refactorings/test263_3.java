public class test263 {

    @Test
    void connectionCanBeMadeToOpenTelemetryCollectorContainer() {
        CounterBuilder("test.counter").increment();
        GaugeBuilder("test.gauge").register();
        TimerBuilder("test.timer").record();
        DistributionSummaryBuilder("test.distributionsummary").record();
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .untilAsserted(() -> verifyPrometheusMetrics());
    }

    private void CounterBuilder(String name) {
        Counter.builder(name).register(this.meterRegistry).increment(42);
    }

    private void GaugeBuilder(String name) {
        Gauge.builder(name, () -> 12).register(this.meterRegistry);
    }

    private void TimerBuilder(String name) {
        Timer.builder(name).register(this.meterRegistry).record(Duration.ofMillis(123));
    }

    private void DistributionSummaryBuilder(String name) {
        DistributionSummary.builder(name).register(this.meterRegistry).record(24);
    }

    private void verifyPrometheusMetrics() {
        whenPrometheusScraped().then()
                .statusCode(200)
                .contentType(OPENMETRICS_001)
                .body(endsWith("# EOF"), containsString(
                "{job=\"test\",service_name=\"test\",telemetry_sdk_language=\"java\",telemetry_sdk_name=\"io.micrometer\""),
                matchesPattern("(?s)^.*test_counter\\{.+} 42\\.0\\n.*$"),
                matchesPattern("(?s)^.*test_gauge\\{.+} 12\\.0\\n.*$"),
                matchesPattern("(?s)^.*test_timer_count\\{.+} 1\\n.*$"),
                matchesPattern("(?s)^.*test_timer_sum\\{.+} 123\\.0\\n.*$"),
                matchesPattern("(?s)^.*test_timer_bucket\\{.+,le=\"\\+Inf\"} 1\\n.*$"),
                matchesPattern("(?s)^.*test_distributionsummary_count\\{.+} 1\\n.*$"),
                matchesPattern("(?s)^.*test_distributionsummary_sum\\{.+} 24\\.0\\n.*$"),
                matchesPattern("(?s)^.*test_distributionsummary_bucket\\{.+,le=\"\\+Inf\"} 1\\n.*$"));
    }
}
