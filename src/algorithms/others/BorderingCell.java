package algorithms.others;

import java.util.ArrayList;

public class BorderingCell {
    private Cell cell;
    private ArrayList<Cell> uncovered_neighbours;
    private int num_marked_nettle;

    public BorderingCell(Cell cell, ArrayList<Cell> uncovered_neighbours, int num_marked_nettle) {
        this.cell = cell;
        this.uncovered_neighbours = uncovered_neighbours;
        this.num_marked_nettle = num_marked_nettle;
    }

    public Cell get_cell() {
        return cell;
    }

    public ArrayList<Cell> get_uncovered_neighbours() {
        return uncovered_neighbours;
    }

    public int get_num_marked_nettle() {
        return num_marked_nettle;
    }
}