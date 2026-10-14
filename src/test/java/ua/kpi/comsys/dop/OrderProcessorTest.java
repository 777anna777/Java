package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {
    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void processesPending() {
        assertEquals("Order Status: PENDING\nDetails: Order is currently pending processing.\n",
                processor.process(new Pending()));
    }

    @Test
    void processesPaid() {
        assertEquals("Order Status: PAID\nDetails: Payment ID: PMT-001, Amount: $100.50\n",
                processor.process(new Paid("PMT-001", new BigDecimal("100.50"))));
    }

    @Test
    void processesShipped() {
        assertEquals("Order Status: SHIPPED\nDetails: Tracking Code: TRK-123, Dispatch Date: 2026-10-14\n",
                processor.process(new Shipped("TRK-123", LocalDate.of(2026, 10, 14))));
    }

    @Test
    void processesCancelled() {
        assertEquals("Order Status: CANCELLED\nDetails: Reason: Customer request\n",
                processor.process(new Cancelled("Customer request")));
    }

    @Test
    void rejectsNullStatus() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));
        assertEquals("Status cannot be null", error.getMessage());
    }

    @Test
    void rejectsInvalidPaidFields() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Paid(null, BigDecimal.ONE)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Paid("   ", BigDecimal.ONE)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Paid("P", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Paid("P", BigDecimal.ZERO)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Paid("P", new BigDecimal("-1")))
        );
    }

    @Test
    void rejectsInvalidShippedFields() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Shipped(null, LocalDate.now())),
                () -> assertThrows(IllegalArgumentException.class, () -> new Shipped("  ", LocalDate.now())),
                () -> assertThrows(IllegalArgumentException.class, () -> new Shipped("TRK", null))
        );
    }

    @Test
    void rejectsInvalidCancelledFields() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Cancelled(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Cancelled("  "))
        );
    }
}
