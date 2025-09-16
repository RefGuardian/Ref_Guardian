public class test263 {

    @Test
    void connectionCanBeMadeToOpenTelemetryCollectorContainer() {
        registerTestCounter();
        registerTestGauge();
        registerTestTimer();
        registerTestDistributionSummary();
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .untilAsserted(() -> whenPrometheusScraped().then()
                        .statusCode(200)
                        .contentType(OPENMETRICS_001)
                        .body(endsWith("# EOF\n"), containsString(
                                "{job=\"test\",service_
name=\"test\",telemetry_sdk_language=\"java\",telemetry_sdk_name=\"io.micrometer\""),
                                matchesPattern("(?s)^.*test_counter\\{.+} 42\\.0\\n.*$"),
                                matchesPattern("(?s)^.*test_gauge\\{.+} 12\\.0\\n.*$"),
                                matchesPattern("(?s)^.*test_timer_count\\{.+} 1\\n.*$"),
                                matchesPattern("(?s)^.*test_timer_sum\\{.+} 123\\.0\\n.*$"),
                                matchesPattern("(?s)^.*test_timer_bucket\\{.+,le=\"\\+Inf\"} 1\\n.*$"),
                                matchesPattern("(?s)^.*test_distributionsummary_count\\{.+} 1\\n.*$"),
                                matchesPattern("(?s)^.*test_distributionsummary_sum\\{.+} 24\\.0\\n.*$"),
                                matchesPattern("(?s)^.*test_distributionsummary_bucket\\{.+,le=\"\\+Inf\"} 1\\n.*$")));
    }

    private void registerTestCounter() {
        Counter.builder("test.counter").register(this.meterRegistry).increment(42);
    }

    private void registerTestGauge() {
        Gauge.builder("test.gauge", () -> 12).register(this.meterRegistry);
    }

    private void registerTestTimer() {
        Timer.builder("test.timer").register(this.meterRegistry).record(Duration.ofMillis(123));
    }

    private void registerTestDistributionSummary() {
        DistributionSummary.builder("test.distributionsummary").register(this.meterRegistry).record(24);
    }
}
