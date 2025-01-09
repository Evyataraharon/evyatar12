public class CellEntry implements Index2D {
    private final int x;
    private final int y;

    public CellEntry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CellEntry(String coordinate) {
        int tempY;
        if (coordinate == null || coordinate.length() < 2) {
            this.x = -1;
            tempY = -1;
        } else {
            char col = Character.toUpperCase(coordinate.charAt(0));
            this.x = col - 'A';
            try {
                tempY = Integer.parseInt(coordinate.substring(1));
            } catch (NumberFormatException e) {
                tempY = -1;
            }
        }
        this.y = tempY;
    }

    @Override
    public boolean isValid() {
        return x >= 0 && x <= 25 && y >= 0 && y <= 99;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        if (!isValid()) return "";
        return String.format("%c%d", (char)('A' + x), y);
    }
}
