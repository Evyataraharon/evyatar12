import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import java.util.HashSet;


public class Ex2SheetTest {
    private Ex2Sheet sheet;

    @BeforeEach
    void setUp() {
        sheet = new Ex2Sheet(5, 5);
    }

    @Test
    void value() {
        sheet.set(0, 0, "Test");
        assertEquals("Test", sheet.value(0, 0));

        sheet.set(0, 0, "");
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(0, 0));

        // Check numeric values
        sheet.set(1, 1, "42");
        assertEquals("42", sheet.value(1, 1));
    }

    @Test
    void get() {
        sheet.set(0, 0, "Hello");
        Cell cell = sheet.get(0, 0);
        assertNotNull(cell);
        assertEquals("Hello", cell.getData());

        // Test empty cell
        Cell emptyCell = sheet.get(1, 1);
        assertNotNull(emptyCell);
        assertEquals("", emptyCell.getData());
    }

    @Test
    void testGet() {
        sheet.set(0, 0, "Test"); // A0
        Cell cell = sheet.get("A0");
        assertNotNull(cell);
        assertEquals("Test", cell.getData());

        sheet.set(1, 1, "B1"); // B1
        cell = sheet.get("B1");
        assertNotNull(cell);
        assertEquals("B1", cell.getData());

        // Test invalid coordinates
        assertNull(sheet.get("Z9"));
        assertNull(sheet.get(""));
    }

    @Test
    void width() {
        assertEquals(5, sheet.width());
        Ex2Sheet defaultSheet = new Ex2Sheet();
        assertEquals(Ex2Utils.WIDTH, defaultSheet.width());
    }

    @Test
    void height() {
        assertEquals(5, sheet.height());
        Ex2Sheet defaultSheet = new Ex2Sheet();
        assertEquals(Ex2Utils.HEIGHT, defaultSheet.height());
    }

    @Test
    void set() {
        sheet.set(0, 0, "42");
        assertEquals("42", sheet.value(0, 0));

        sheet.set(1, 1, "Test");
        assertEquals("Test", sheet.value(1, 1));

        // Test special characters
        sheet.set(0, 1, "!@#$%^&*()");
        assertEquals("!@#$%^&*()", sheet.value(0, 1));

        // Test formula
        sheet.set(2, 2, "=1+1");
        assertEquals("2.0", sheet.value(2, 2));

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                sheet.set(5, 5, "Should fail")
        );
    }

    @Test
    void eval() {
        sheet.set(0, 0, "5"); // A0
        sheet.set(0, 1, "3"); // A1
        sheet.set(0, 2, "=A0+A1"); // A2
        assertEquals("8.0", sheet.value(0, 2));

        sheet.set(1, 0, "=A0*A1"); // B0
        assertEquals("15.0", sheet.value(1, 0));
    }

    @Test
    void isIn() {
        assertTrue(sheet.isIn(0, 0));
        assertTrue(sheet.isIn(4, 4));
        assertFalse(sheet.isIn(5, 5));
        assertFalse(sheet.isIn(-1, 0));
        assertFalse(sheet.isIn(0, -1));
    }

    @Test
    void depth() {
        sheet.set(0, 0, "=5");        // A0: depth 0
        sheet.set(0, 1, "=A0+3");    // A1: depth 1
        sheet.set(0, 2, "=A1*2");    // A2: depth 2

        int[][] depths = sheet.depth();
        assertEquals(0, depths[0][0]); // A0
        assertEquals(1, depths[0][1]); // A1
        assertEquals(2, depths[0][2]); // A2
    }

    @Test
    void load() throws IOException {
        String tempFile = "test_sheet_load.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment\n");
            writer.write("0,0,Test\n");
            writer.write("1,1,=A0+5\n");
        }

        sheet.load(tempFile);
        assertEquals("Test", sheet.value(0, 0));
        new File(tempFile).delete();

        // Test loading empty file
        String emptyFile = "empty_test.csv";
        new FileWriter(emptyFile).close();
        sheet.load(emptyFile);
        new File(emptyFile).delete();
    }

    @Test
    void save() throws IOException {
        sheet.set(0, 0, "Test");
        sheet.set(1, 1, "=A0+5");

        String tempFile = "test_sheet_save.csv";
        sheet.save(tempFile);

        // Verify file exists
        File savedFile = new File(tempFile);
        assertTrue(savedFile.exists());

        // Load and verify content
        Ex2Sheet newSheet = new Ex2Sheet(5, 5);
        newSheet.load(tempFile);
        assertEquals(sheet.value(0, 0), newSheet.value(0, 0));
        assertEquals(sheet.value(1, 1), newSheet.value(1, 1));

        savedFile.delete();
    }

    @Test
    void testEval() {
        // Test complex formulas
        sheet.set(0, 0, "10"); // A0
        sheet.set(0, 1, "2");  // A1
        sheet.set(0, 2, "=A0*A1+5"); // A2
        assertEquals("25.0", sheet.value(0, 2));

        sheet.set(1, 0, "=(A0+A1)*2"); // B0
        assertEquals("24.0", sheet.value(1, 0));

        // Test circular references
        sheet.set(2, 0, "=B1");  // C0 references B1
        sheet.set(1, 1, "=C0");  // B1 references C0
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(2, 0));
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(1, 1));
    }



}