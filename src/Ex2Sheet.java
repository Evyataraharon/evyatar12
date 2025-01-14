import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a spreadsheet that supports formulas and cell references
 */
public class Ex2Sheet implements Sheet {
    private Cell[][] table;

    /**
     * Creates a new spreadsheet with specified dimensions and initializes all cells as empty
     * Evaluates the entire sheet after creation
     */
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }

    /**
     * Creates a spreadsheet with default dimensions from Ex2Utils
     */
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    /**
     * Returns the evaluated value of a cell at given coordinates
     */
    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        Cell c = get(x, y);
        if (c != null) {
            ans = eval(x, y);
        }
        return ans;
    }

    /**
     * Returns the cell object at given coordinates
     */
    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    /**
     * Returns a cell by its string coordinates (e.g., "A1")
     * Converts the string format to array indices and returns the corresponding cell
     */
    @Override
    public Cell get(String cords) {
        Cell ans = null;
        if (cords != null && cords.length() >= 2) {
            char col = Character.toUpperCase(cords.charAt(0));
            int row = Integer.parseInt(cords.substring(1));
            int x = col - 'A';
            int y = row;
            if (x >= 0 && x < width() && y >= 0 && y < height()) {
                ans = table[x][y];
            }
        }
        return ans;
    }

    /**
     * Returns the width (number of columns) of the spreadsheet
     */
    @Override
    public int width() {
        return table.length;
    }

    /**
     * Returns the height (number of rows) of the spreadsheet
     */
    @Override
    public int height() {
        return table[0].length;
    }

    /**
     * Sets a cell's value and triggers full sheet evaluation
     */
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        eval();
    }

    /**
     * Evaluates all cells in the sheet based on their dependency depth
     * Makes sure cells are evaluated in the correct order to handle formulas properly
     */
    @Override
    public void eval() {
        int[][] dd = depth();
        int maxDepth = 0;
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (dd[i][j] > maxDepth) {
                    maxDepth = dd[i][j];
                }
            }
        }

        for (int d = 0; d <= maxDepth; d++) {
            for (int i = 0; i < width(); i++) {
                for (int j = 0; j < height(); j++) {
                    if (dd[i][j] == d) {
                        eval(i, j);
                    }
                }
            }
        }
    }

    /**
     * Checks if given coordinates are within the spreadsheet boundaries
     */
    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx >= 0 && yy >= 0;
        ans = ans && xx < width() && yy < height();
        return ans;
    }

    /**
     * Calculates and returns the dependency depths for all cells
     * Used to determine the order of evaluation for cells with formulas
     */
    @Override
    public int[][] depth() {
        int[][] depths = new int[width()][height()];
        boolean[][] visited = new boolean[width()][height()];

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                calculateDepth(i, j, depths, visited);
            }
        }
        return depths;
    }

    /**
     * Recursively calculates the dependency depth of a cell
     * Takes into account all cell references in formulas
     */
    private void calculateDepth(int x, int y, int[][] depths, boolean[][] visited) {
        if (visited[x][y]) return;
        visited[x][y] = true;

        String content = get(x, y).getData().trim();
        if (!content.startsWith("=")) {
            depths[x][y] = 0;
            return;
        }

        int maxDepth = 0;
        String formula = content.substring(1);
        for (int i = 0; i < formula.length() - 1; i++) {
            if (Character.isLetter(formula.charAt(i)) && Character.isDigit(formula.charAt(i + 1))) {
                int col = Character.toUpperCase(formula.charAt(i)) - 'A';
                int row = Integer.parseInt(formula.substring(i + 1, i + 2));

                if (isIn(col, row)) {
                    calculateDepth(col, row, depths, visited);
                    maxDepth = Math.max(maxDepth, depths[col][row] + 1);
                }
            }
        }

        depths[x][y] = maxDepth;
    }

    /**
     * Loads spreadsheet data from a CSV file
     * Ignores first line and processes cell data from subsequent lines
     */
    @Override
    public void load(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                try {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    String value = parts[2].trim();
                    if (isIn(x, y)) {
                        set(x, y, value);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        reader.close();
    }

    /**
     * Saves spreadsheet to a CSV file
     * Writes header and all non-empty cells with their coordinates
     */
    @Override
    public void save(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method\n");

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                String cellData = value(x, y);
                if (!cellData.equals(Ex2Utils.EMPTY_CELL)) {
                    writer.write(x + "," + y + "," + cellData + "\n");
                }
            }
        }
        writer.close();
    }

    /**
     * Evaluates a cell and handles any errors in formula evaluation
     */
    @Override
    public String eval(int x, int y) {
        try {
            return eval(x, y, new HashSet<>());
        } catch (IllegalArgumentException e) {
            Cell cell = get(x, y);
            if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                cell.setType(Ex2Utils.ERR_CYCLE_FORM);
                return Ex2Utils.ERR_CYCLE;
            }
            cell.setType(Ex2Utils.ERR_FORM_FORMAT);
            return Ex2Utils.ERR_FORM;
        }
    }

    public String eval(int x, int y, Set<String> visited) {
        Cell cell = get(x, y);
        String cellKey = x + "," + y;

        if (visited.contains(cellKey)) {
            cell.setType(Ex2Utils.ERR_CYCLE_FORM);
            throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
        }
        visited.add(cellKey);

        if (cell == null || cell.getData().isEmpty()) {
            cell.setType(Ex2Utils.NUMBER);
            return Ex2Utils.EMPTY_CELL;
        }

        String cellData = cell.getData().trim();

        if (cellData.startsWith("=")) {
            String formula = cellData.substring(1).trim();

            if (formula.equals(getCellName(x, y))) {
                cell.setType(Ex2Utils.ERR_CYCLE_FORM);
                throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
            }

            try {
                double result = calculateFormula(formula, new HashSet<>(visited));
                cell.setType(Ex2Utils.FORM);
                return String.valueOf(result);
            } catch (IllegalArgumentException e) {
                if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                    cell.setType(Ex2Utils.ERR_CYCLE_FORM);
                    throw e;
                }
                cell.setType(Ex2Utils.ERR_FORM_FORMAT);
                return Ex2Utils.ERR_FORM;
            } catch (Exception e) {
                cell.setType(Ex2Utils.ERR_FORM_FORMAT);
                return Ex2Utils.ERR_FORM;
            }
        }

        cell.setType(Ex2Utils.NUMBER);
        return cellData;
    }

    /**
     * Processes and calculates the result of a formula
     * Handles cell references, mathematical operations, and parentheses
     */
    private double calculateFormula(String formula, Set<String> visited) {
        formula = formula.replaceAll(" ", "");

        String cellPattern = "[A-Z]\\d+";
        if (formula.matches(cellPattern)) {
            char col = Character.toUpperCase(formula.charAt(0));
            int row = Integer.parseInt(formula.substring(1));
            int x = col - 'A';
            int y = row;

            if (isIn(x, y)) {
                String cellValue;
                try {
                    cellValue = eval(x, y, visited);
                    if (cellValue.equals(Ex2Utils.ERR_CYCLE)) {
                        throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
                    }
                    return Double.parseDouble(cellValue);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                        throw e;
                    }
                    throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                }
            }
        }

        for (int i = 0; i < formula.length() - 1; i++) {
            if (Character.isLetter(formula.charAt(i)) &&
                    Character.isDigit(formula.charAt(i + 1))) {

                int j = i + 1;
                while (j < formula.length() && Character.isDigit(formula.charAt(j))) {
                    j++;
                }

                String cellRef = formula.substring(i, j);
                char col = Character.toUpperCase(cellRef.charAt(0));
                int row = Integer.parseInt(cellRef.substring(1));
                int x = col - 'A';
                int y = row;

                if (isIn(x, y)) {
                    String cellValue;
                    try {
                        cellValue = eval(x, y, visited);
                        if (cellValue.equals(Ex2Utils.ERR_CYCLE)) {
                            throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
                        }
                        formula = formula.replace(cellRef, cellValue);
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                            throw e;
                        }
                        throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                    }
                }
            }
        }

        int bracketCount = 0;
        int lastOp = -1;

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (c == '(') bracketCount++;
            if (c == ')') bracketCount--;
            if (bracketCount == 0 && "+-*/".indexOf(c) >= 0) {
                if (c == '-' && (i == 0 || "+-*/(".indexOf(formula.charAt(i - 1)) >= 0)) {
                    continue;
                }
                lastOp = i;
            }
        }

        if (lastOp != -1) {
            String leftPart = formula.substring(0, lastOp);
            String rightPart = formula.substring(lastOp + 1);
            char op = formula.charAt(lastOp);

            switch (op) {
                case '+': return calculateFormula(leftPart, visited) + calculateFormula(rightPart, visited);
                case '-': return calculateFormula(leftPart, visited) - calculateFormula(rightPart, visited);
                case '*': return calculateFormula(leftPart, visited) * calculateFormula(rightPart, visited);
                case '/':
                    double divisor = calculateFormula(rightPart, visited);
                    if (divisor == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return calculateFormula(leftPart, visited) / divisor;
            }
        }

        try {
            if (formula.startsWith("(") && formula.endsWith(")")) {
                return calculateFormula(formula.substring(1, formula.length() - 1), visited);
            }
            return Double.parseDouble(formula);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid formula: " + formula);
        }
    }

    /**
     * Retrieves numeric value from a cell reference
     * Returns 0.0 for invalid references or non-numeric content
     */
    private double getValueFromCell(String cellReference) {
        if (cellReference.length() >= 2) {
            char column = Character.toUpperCase(cellReference.charAt(0));
            int row = Integer.parseInt(cellReference.substring(1));
            int colIndex = column - 'A';
            int rowIndex = row;

            if (isIn(colIndex, rowIndex)) {
                String data = get(colIndex, rowIndex).getData();
                try {
                    return Double.parseDouble(data);
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }

    /**
     * Converts numeric coordinates to cell reference format (e.g., "A1")
     */
    private String getCellName(int x, int y) {
        char column = (char) ('A' + x);
        return column + String.valueOf(y);
    }
}