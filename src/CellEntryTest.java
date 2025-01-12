import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @Test
    void isValid() {
        // Valid range tests
        assertTrue(new CellEntry("A0").isValid());
        assertTrue(new CellEntry("Z99").isValid());
        assertTrue(new CellEntry(0, 0).isValid());
        assertTrue(new CellEntry(25, 99).isValid());

        // Invalid range tests
        assertFalse(new CellEntry("A100").isValid());
        assertFalse(new CellEntry("Z100").isValid());
        assertFalse(new CellEntry("[1").isValid());  // Character after 'Z'
        assertFalse(new CellEntry("@1").isValid());  // Character before 'A'
        assertFalse(new CellEntry(-1, -1).isValid());
        assertFalse(new CellEntry(26, 100).isValid());
        assertFalse(new CellEntry((String)null).isValid());
        assertFalse(new CellEntry("").isValid());
        assertFalse(new CellEntry("A").isValid());
    }

    @Test
    void getX() {
        // Valid cases
        assertEquals(0, new CellEntry("A1").getX());
        assertEquals(25, new CellEntry("Z1").getX());
        assertEquals(5, new CellEntry("F7").getX());
        assertEquals(5, new CellEntry("f7").getX());  // Test case insensitivity

        // Constructor with coordinates
        assertEquals(0, new CellEntry(0, 1).getX());
        assertEquals(25, new CellEntry(25, 1).getX());

        // Edge cases
        assertEquals(-1, new CellEntry((String)null).getX());
        assertEquals(-1, new CellEntry("").getX());
        assertEquals(-1, new CellEntry("A").getX());  // מחרוזת קצרה מ-2 תווים
    }

    @Test
    void getY() {
        // Valid cases
        assertEquals(1, new CellEntry("A1").getY());
        assertEquals(99, new CellEntry("A99").getY());
        assertEquals(7, new CellEntry("F7").getY());
        assertEquals(0, new CellEntry("Z0").getY());

        // Constructor with coordinates
        assertEquals(1, new CellEntry(0, 1).getY());
        assertEquals(99, new CellEntry(0, 99).getY());

        // Edge cases
        assertEquals(-1, new CellEntry((String)null).getY());
        assertEquals(-1, new CellEntry("").getY());
        assertEquals(-1, new CellEntry("A").getY());
        assertEquals(-1, new CellEntry("Ax").getY());  // Invalid number
    }

    @Test
    void testToString() {
        // Valid cases
        assertEquals("A0", new CellEntry("A0").toString());
        assertEquals("Z99", new CellEntry("Z99").toString());
        assertEquals("B5", new CellEntry(1, 5).toString());
        assertEquals("A1", new CellEntry("a1").toString()); // Test case conversion

        // Invalid cases - should return empty string
        assertEquals("", new CellEntry(-1, -1).toString());
        assertEquals("", new CellEntry(26, 100).toString());
        assertEquals("", new CellEntry((String)null).toString());
        assertEquals("", new CellEntry("").toString());
        assertEquals("", new CellEntry("A100").toString());
        assertEquals("", new CellEntry("A").toString());
    }
}