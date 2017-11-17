package algorithms.others;

import java.util.ArrayList;

public class Cell {
    public static final String UNCOVERED = "-";
    public static final String MARKED_NETTLE = "N";

    private int x, y;
    /**
     * Used to hold a knowledge base indicating the cell is yet to be probed (-)
     * or is already uncovered (number) or is marked as a nettle.
     */
    private String value;
    private ArrayList<Cell> uncovered_neighbours; // Used for bordering cell only.
    private int num_marked_nettle; // Used for bordering cell only.

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        value = UNCOVERED;
        uncovered_neighbours = null;
        num_marked_nettle = 0;
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

    public void set_uncovered_neighbours(ArrayList<Cell> uncovered_neighbours) {
        this.uncovered_neighbours = uncovered_neighbours;
    }

    public ArrayList<Cell> get_uncovered_neighbours() {
        return uncovered_neighbours;
    }

    public void set_num_marked_nettle(int num_marked_nettle) {
        this.num_marked_nettle = num_marked_nettle;
    }

    public int get_num_marked_nettle() {
        return num_marked_nettle;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
