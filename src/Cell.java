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
}
