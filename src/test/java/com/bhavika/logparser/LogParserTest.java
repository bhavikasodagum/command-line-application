package com.bhavika.logparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogParserTest {
    private LogParser logParser;

    @BeforeEach
    public void setUp() {
        logParser = new LogParser();
        logParser.apmMetrics = new HashMap<>();
        logParser.applicationLogs = new HashMap<>();
        logParser.requestLogs = new HashMap<>();

        // Sample setup to replicate input for the test cases
        logParser.apmMetrics.put("cpu_usage_percent", new ArrayList<>(Arrays.asList(72.0, 65.0, 80.0)));
        logParser.apmMetrics.put("memory_usage_percent", new ArrayList<>(Arrays.asList(85.0, 90.0)));
        logParser.requestLogs.put("/api/update", new ArrayList<>(Arrays.asList(200.0)));
        logParser.requestLogs.put("/api/status", new ArrayList<>(Arrays.asList(300.0)));
    }

    @Test
    public void testAggregateApmMetrics() {
        Map<String, Map<String, Double>> aggregatedMetrics = logParser.aggregateApmMetrics();

        Map<String, Double> cpuMetrics = aggregatedMetrics.get("cpu_usage_percent");
        assertEquals(72.333, cpuMetrics.get("average"), 0.001);  // Correct average
        assertEquals(72.0, cpuMetrics.get("median"));
        assertEquals(65.0, cpuMetrics.get("minimum"));
        assertEquals(80.0, cpuMetrics.get("maximum"));
    }

    @Test
    public void testProcessApmLog() {
        String line = "timestamp=2024-02-24T16:22:15Z metric=cpu_usage_percent host=webserver1 value=72";
        logParser.processApmLog(line);

        List<Double> values = logParser.apmMetrics.get("cpu_usage_percent");
        assertEquals(72.0, values.get(values.size() - 1));
    }

    @Test
    public void testProcessApplicationLog() {
        String line = "timestamp=2024-02-24T16:22:20Z level=INFO message=\"Scheduled maintenance starting\" host=webserver1";
        logParser.processApplicationLog(line);

        assertEquals(1, logParser.applicationLogs.get("INFO"));
    }

    @Test
    public void testProcessRequestLog() {
        String line = "timestamp=2024-02-24T16:22:25Z request_method=POST request_url=\"/api/update\" response_status=202 response_time_ms=200 host=webserver1";
        logParser.processRequestLog(line);

        List<Double> responseTimes = logParser.requestLogs.get("/api/update");
        assertEquals(200.0, responseTimes.get(responseTimes.size() - 1));
    }

    @Test
    public void testAggregateRequestMetrics() {
        Map<String, Map<String, Double>> aggregatedRequestMetrics = logParser.aggregateRequestMetrics();

        Map<String, Double> apiStatusMetrics = aggregatedRequestMetrics.get("/api/status");
        assertEquals(300.0, apiStatusMetrics.get("min"));
        assertEquals(300.0, apiStatusMetrics.get("50th_percentile"));
        assertEquals(300.0, apiStatusMetrics.get("max"));
    }

    @Test
    public void testInvalidApmLogProcessing() {
        String invalidLine = "invalid_format_metric_log";
        assertThrows(IllegalArgumentException.class, () -> logParser.processApmLog(invalidLine));
    }

    @Test
    public void testInvalidRequestLogProcessing() {
        String invalidLine = "invalid_format_request_log";
        assertThrows(IllegalArgumentException.class, () -> logParser.processRequestLog(invalidLine));
    }

    @Test
    public void testInvalidApplicationLogProcessing() {
        String invalidLine = "invalid_format_application_log";
        assertThrows(IllegalArgumentException.class, () -> logParser.processApplicationLog(invalidLine));
    }
}
