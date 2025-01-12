import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SCellTest {

    @Test
    void setData() {
        SCell cell = new SCell("");

        // Test null input
        cell.setData(null);
        assertEquals("", cell.getData());
        assertEquals(Ex2Utils.NUMBER, cell.getType());

        // Test formula input
        cell.setData("=A1+B2");
        assertEquals("=A1+B2", cell.getData());
        assertEquals(Ex2Utils.FORM, cell.getType());

        // Test trimming spaces
        cell.setData("  123  ");
        assertEquals("123", cell.getData());
        assertEquals(Ex2Utils.NUMBER, cell.getType());

        // Edge cases
        cell.setData("");  // Empty string
        assertEquals("", cell.getData());

        cell.setData("   ");  // Only spaces
        assertEquals("", cell.getData());

        cell.setData("=");  // Just equals sign
        assertEquals("=", cell.getData());
        assertEquals(Ex2Utils.FORM, cell.getType());

        // Test very long string
        String longString = "a".repeat(1000);
        cell.setData(longString);
        assertEquals(longString, cell.getData());
    }

    @Test
    void getData() {
        SCell cell = new SCell("test123");
        assertEquals("test123", cell.getData());

        cell.setData("=SUM(A1:A5)");
        assertEquals("=SUM(A1:A5)", cell.getData());

        // Edge cases
        cell = new SCell("");  // Empty constructor
        assertEquals("", cell.getData());

        cell = new SCell(" ");  // Space in constructor
        assertEquals("", cell.getData());
    }

    @Test
    void getType() {
        SCell cell = new SCell("");
        assertEquals(Ex2Utils.NUMBER, cell.getType());

        cell.setData("=A1");
        assertEquals(Ex2Utils.FORM, cell.getType());

        // Edge cases
        cell.setData("==");  // Double equals
        assertEquals(Ex2Utils.FORM, cell.getType());

        cell.setData("= ");  // Equals with space
        assertEquals(Ex2Utils.FORM, cell.getType());
    }

    @Test
    void setType() {
        SCell cell = new SCell("");

        cell.setType(Ex2Utils.ERR_FORM_FORMAT);
        assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell.getType());

        cell.setType(Ex2Utils.ERR_CYCLE_FORM);
        assertEquals(Ex2Utils.ERR_CYCLE_FORM, cell.getType());

        // Edge cases
        cell.setType(-1);  // Negative type
        assertEquals(-1, cell.getType());

        cell.setType(Integer.MAX_VALUE);  // Maximum integer value
        assertEquals(Integer.MAX_VALUE, cell.getType());
    }

    @Test
    void getOrder() {
        SCell cell = new SCell("");
        assertEquals(0, cell.getOrder());

        // Edge case - check order after type change
        cell.setType(Ex2Utils.FORM);
        assertEquals(0, cell.getOrder(), "Order should not change when type changes");
    }

    @Test
    void setOrder() {
        SCell cell = new SCell("");

        cell.setOrder(3);
        assertEquals(3, cell.getOrder());

        cell.setOrder(0);
        assertEquals(0, cell.getOrder());

        // Edge cases
        cell.setOrder(-100);  // Negative order
        assertEquals(-100, cell.getOrder());

        cell.setOrder(Integer.MAX_VALUE);  // Maximum integer value
        assertEquals(Integer.MAX_VALUE, cell.getOrder());

        cell.setOrder(Integer.MIN_VALUE);  // Minimum integer value
        assertEquals(Integer.MIN_VALUE, cell.getOrder());
    }

    @Test
    void testToString() {
        SCell cell = new SCell("Hello");
        assertEquals("Hello", cell.toString());

        cell.setData("=A1+B2");
        assertEquals("=A1+B2", cell.toString());

        // Edge cases
        cell.setData("");
        assertEquals("", cell.toString());

        cell.setData(null);
        assertEquals("", cell.toString());

        String longString = "x".repeat(1000);
        cell.setData(longString);
        assertEquals(longString, cell.toString());
    }
}