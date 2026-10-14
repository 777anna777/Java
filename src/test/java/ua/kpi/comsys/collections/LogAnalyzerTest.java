package ua.kpi.comsys.collections;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LogAnalyzerTest {
    private final LogAnalyzer analyzer = new LogAnalyzer();

    private static String log(String ip) {
        return ip + " ident alice [01/May/2025:07:20:10 +0000] "
                + "\"GET /index.html HTTP/1.1\" 200 9481";
    }

    @Test
    void uniqueIpsIgnoresDuplicates() {
        assertEquals(Set.of("127.0.0.1", "192.168.1.2"), analyzer.getUniqueIps(
                List.of(log("127.0.0.1"), log("192.168.1.2"), log("127.0.0.1"))));
    }

    @Test
    void invalidLinesAreIgnored() {
        assertEquals(Set.of("127.0.0.1"), analyzer.getUniqueIps(List.of(
                "not a log", log("300.1.1.1"), log("example.org"), log("127.0.0.1"))));
    }

    @Test
    void emptyAndNullEntries() {
        assertTrue(analyzer.getUniqueIps(List.of()).isEmpty());
        assertTrue(analyzer.getUniqueIps(java.util.Arrays.asList(null, "garbage")).isEmpty());
        assertTrue(analyzer.getTopIps(List.of(), 5).isEmpty());
    }

    @Test
    void topIpsSortsByFrequency() {
        assertEquals(List.of("10.0.0.2", "10.0.0.1"), analyzer.getTopIps(List.of(
                log("10.0.0.2"), log("10.0.0.1"), log("10.0.0.2")), 2));
    }

    @Test
    void topIpsBreaksTiesAlphabetically() {
        assertEquals(List.of("10.0.0.1", "10.0.0.2"), analyzer.getTopIps(List.of(
                log("10.0.0.2"), log("10.0.0.1")), 2));
    }

    @Test
    void topNMoreThanAvailableReturnsAll() {
        assertEquals(List.of("10.0.0.1"), analyzer.getTopIps(List.of(log("10.0.0.1")), 9));
    }

    @Test
    void invalidTopNThrows() {
        assertEquals("topN must be greater than 0", assertThrows(IllegalArgumentException.class,
                () -> analyzer.getTopIps(List.of(), 0)).getMessage());
        assertThrows(IllegalArgumentException.class, () -> analyzer.getTopIps(List.of(), -1));
    }

    @Test
    void nullInputThrowsExactMessage() {
        assertEquals("logLines cannot be null", assertThrows(IllegalArgumentException.class,
                () -> analyzer.getUniqueIps(null)).getMessage());
        assertEquals("logLines cannot be null", assertThrows(IllegalArgumentException.class,
                () -> analyzer.getTopIps(null, 3)).getMessage());
    }

    @Test
    void supportsIpv6Literals() {
        assertEquals(Set.of("2001:db8::1"), analyzer.getUniqueIps(List.of(log("2001:db8::1"))));
    }

    @Test
    void acceptsDashByteCount() {
        assertEquals(Set.of("127.0.0.1"), analyzer.getUniqueIps(List.of(
                "127.0.0.1 - - [01/May/2025:07:20:10 +0000] \"GET / HTTP/1.1\" 404 -")));
    }
}
