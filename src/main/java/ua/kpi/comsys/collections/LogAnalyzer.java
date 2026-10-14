package ua.kpi.comsys.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Analyzes IP addresses in valid Common Log Format log lines. */
public class LogAnalyzer {
    // Common Log Format: host ident authuser [date] "request" status bytes
    private static final Pattern COMMON_LOG = Pattern.compile(
            "^(\\S+) \\S+ \\S+ \\[\\d{2}/[A-Za-z]{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} [+-]\\d{4}\\] "
                    + "\"[^\"]*\" \\d{3} (?:\\d+|-)$");

    /** Returns every unique IP occurring in a valid log line. */
    public Set<String> getUniqueIps(List<String> logLines) {
        validateLogLines(logLines);
        Set<String> ips = new HashSet<>();
        for (String line : logLines) {
            String ip = extractIp(line);
            if (ip != null) {
                ips.add(ip);
            }
        }
        return ips;
    }

    /** Returns at most topN IPs, sorted by descending frequency then ascending IP. */
    public List<String> getTopIps(List<String> logLines, int topN) {
        validateLogLines(logLines);
        if (topN <= 0) {
            throw new IllegalArgumentException("topN must be greater than 0");
        }

        Map<String, Integer> counts = new HashMap<>();
        for (String line : logLines) {
            String ip = extractIp(line);
            if (ip != null) {
                counts.merge(ip, 1, Integer::sum);
            }
        }

        List<String> result = new ArrayList<>(counts.keySet());
        result.sort(Comparator.<String>comparingInt(counts::get).reversed()
                .thenComparing(Comparator.naturalOrder()));
        return result.subList(0, Math.min(topN, result.size()));
    }

    private static void validateLogLines(List<String> logLines) {
        if (logLines == null) {
            throw new IllegalArgumentException("logLines cannot be null");
        }
    }

    private static String extractIp(String line) {
        if (line == null) {
            return null;
        }
        Matcher matcher = COMMON_LOG.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        String candidate = matcher.group(1);
        return isIpv4(candidate) || isIpv6(candidate) ? candidate : null;
    }

    private static boolean isIpv4(String text) {
        String[] octets = text.split("\\.", -1);
        if (octets.length != 4) {
            return false;
        }
        for (String octet : octets) {
            if (octet.isEmpty() || octet.length() > 3) {
                return false;
            }
            for (int i = 0; i < octet.length(); i++) {
                if (octet.charAt(i) < '0' || octet.charAt(i) > '9') {
                    return false;
                }
            }
            if (Integer.parseInt(octet) > 255) {
                return false;
            }
        }
        return true;
    }

    private static boolean isIpv6(String text) {
        // Only numeric IPv6 literals; do not resolve hostnames or perform DNS queries.
        if (!text.contains(":") || !text.matches("[0-9a-fA-F:.]+")) {
            return false;
        }
        try {
            return java.net.InetAddress.getByName(text) instanceof java.net.Inet6Address;
        } catch (java.net.UnknownHostException ex) {
            return false;
        }
    }
}
