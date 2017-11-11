public class Cell {
    public static final String UNCOVERED = "-";
    public static final String MARKED_NETTLE = "N";

    private int x, y;
    private String value;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;

        value = UNCOVERED;
    }

    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }

    public void set_value(String value) {
        this.value = value;
    }

    public String get_value() {
        return value;
    }

    public boolean is_border(Cell cell) {
        int x = Math.abs(this.x - cell.get_x());
        int y = Math.abs(this.y - cell.get_y());

        return (x == 0 && y == 1) || (x == 1 && y == 0);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
