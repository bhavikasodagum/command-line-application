/* package com.bhavika.logparser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    public Map<String, List<Double>> apmMetrics;
    public Map<String, Integer> applicationLogs;
    public Map<String, List<Double>> requestLogs;

    public LogParser() {
        apmMetrics = new HashMap<>();
        applicationLogs = new HashMap<>();
        requestLogs = new HashMap<>();
    }

    public void processApmLog(String line) {
        // Regex pattern to match the expected APM log format
        String patternString = "timestamp=.+ metric=([a-zA-Z_]+) host=.+ value=([0-9.]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String metric = matcher.group(1);
            Double value = Double.valueOf(matcher.group(2));
            apmMetrics.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
        } else {
            throw new IllegalArgumentException("Invalid APM log format: " + line);
        }
    }

    public void processApplicationLog(String line) {
        // Regex pattern for application log format
        String patternString = "timestamp=.+ level=([A-Z]+) message=.+ host=.+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String level = matcher.group(1);
            applicationLogs.put(level, applicationLogs.getOrDefault(level, 0) + 1);
        } else {
            throw new IllegalArgumentException("Invalid application log format: " + line);
        }
    }

    public void processRequestLog(String line) {
        // Adjusted regex pattern for request log format to match given format correctly
        String patternString = "timestamp=.+ request_method=([A-Z]+) request_url=\"([^\"]+)\" response_status=(\\d+) response_time_ms=([0-9.]+) host=.+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String requestUrl = matcher.group(2);
            Double responseTime = Double.valueOf(matcher.group(4));
            requestLogs.computeIfAbsent(requestUrl, k -> new ArrayList<>()).add(responseTime);
        } else {
            throw new IllegalArgumentException("Invalid request log format: " + line);
        }
    }

    public Map<String, Map<String, Double>> aggregateApmMetrics() {
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String metric : apmMetrics.keySet()) {
            List<Double> values = apmMetrics.get(metric);
            Collections.sort(values);
            double average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double median = values.size() % 2 == 0 ?
                    (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0 :
                    values.get(values.size() / 2);
            double min = values.get(0);
            double max = values.get(values.size() - 1);

            Map<String, Double> metrics = new HashMap<>();
            metrics.put("average", average);
            metrics.put("median", median);
            metrics.put("minimum", min);
            metrics.put("maximum", max);

            result.put(metric, metrics);
        }
        return result;
    }

    public Map<String, Map<String, Double>> aggregateRequestMetrics() {
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String url : requestLogs.keySet()) {
            List<Double> values = requestLogs.get(url);
            Collections.sort(values);
            double min = values.get(0);
            double max = values.get(values.size() - 1);
            double median = values.size() % 2 == 0 ?
                    (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0 :
                    values.get(values.size() / 2);

            Map<String, Double> metrics = new HashMap<>();
            metrics.put("min", min);
            metrics.put("50th_percentile", median);
            metrics.put("max", max);

            result.put(url, metrics);
        }
        return result;
    }
}
*/
package com.bhavika.logparser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    public Map<String, List<Double>> apmMetrics;
    public Map<String, Integer> applicationLogs;
    public Map<String, List<Double>> requestLogs;

    public LogParser() {
        apmMetrics = new HashMap<>();
        applicationLogs = new HashMap<>();
        requestLogs = new HashMap<>();
    }

    public void processApmLog(String line) {
        // Regex pattern to match the expected APM log format
        String patternString = "timestamp=.+ metric=([a-zA-Z_]+) host=.+ value=([0-9.]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String metric = matcher.group(1);
            Double value = Double.valueOf(matcher.group(2));
            apmMetrics.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
        } else {
            throw new IllegalArgumentException("Invalid APM log format: " + line);
        }
    }

    public void processApplicationLog(String line) {
        // Regex pattern for application log format
        String patternString = "timestamp=.+ level=([A-Z]+) message=.+ host=.+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String level = matcher.group(1);
            applicationLogs.put(level, applicationLogs.getOrDefault(level, 0) + 1);
        } else {
            throw new IllegalArgumentException("Invalid application log format: " + line);
        }
    }

    public void processRequestLog(String line) {
        // Adjusted regex pattern for request log format to match given format correctly
        String patternString = "timestamp=.+ request_method=([A-Z]+) request_url=\"([^\"]+)\" response_status=(\\d+) response_time_ms=([0-9.]+) host=.+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            String requestUrl = matcher.group(2);
            Double responseTime = Double.valueOf(matcher.group(4));
            requestLogs.computeIfAbsent(requestUrl, k -> new ArrayList<>()).add(responseTime);
        } else {
            throw new IllegalArgumentException("Invalid request log format: " + line);
        }
    }

    public Map<String, Map<String, Double>> aggregateApmMetrics() {
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String metric : apmMetrics.keySet()) {
            List<Double> values = apmMetrics.get(metric);
            Collections.sort(values);
            double average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double median = values.size() % 2 == 0 ?
                    (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0 :
                    values.get(values.size() / 2);
            double min = values.get(0);
            double max = values.get(values.size() - 1);

            Map<String, Double> metrics = new HashMap<>();
            metrics.put("average", average);
            metrics.put("median", median);
            metrics.put("minimum", min);
            metrics.put("maximum", max);

            result.put(metric, metrics);
        }
        return result;
    }

    public Map<String, Map<String, Double>> aggregateRequestMetrics() {
        Map<String, Map<String, Double>> result = new HashMap<>();
        for (String url : requestLogs.keySet()) {
            List<Double> values = requestLogs.get(url);
            Collections.sort(values);
            double min = values.get(0);
            double max = values.get(values.size() - 1);
            double median = values.size() % 2 == 0 ?
                    (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0 :
                    values.get(values.size() / 2);

            Map<String, Double> metrics = new HashMap<>();
            metrics.put("min", min);
            metrics.put("50th_percentile", median);
            metrics.put("max", max);

            result.put(url, metrics);
        }
        return result;
    }

    // Add the main method to run the application
    public static void main(String[] args) {
        LogParser logParser = new LogParser();

        // Sample log entries
        String apmLog = "timestamp=2024-02-24T16:22:15Z metric=cpu_usage_percent host=webserver1 value=72";
        String appLog = "timestamp=2024-02-24T16:22:20Z level=INFO message=\"Scheduled maintenance starting\" host=webserver1";
        String requestLog = "timestamp=2024-02-24T16:22:25Z request_method=POST request_url=\"/api/update\" response_status=202 response_time_ms=200 host=webserver1";

        // Process log entries
        logParser.processApmLog(apmLog);
        logParser.processApplicationLog(appLog);
        logParser.processRequestLog(requestLog);

        // Aggregate metrics and print results
        Map<String, Map<String, Double>> aggregatedApmMetrics = logParser.aggregateApmMetrics();
        Map<String, Map<String, Double>> aggregatedRequestMetrics = logParser.aggregateRequestMetrics();

        // Print the output (can be directed to JSON format if needed)
        System.out.println("Aggregated APM Metrics:");
        System.out.println(aggregatedApmMetrics);
        System.out.println("Aggregated Request Metrics:");
        System.out.println(aggregatedRequestMetrics);
    }
}
