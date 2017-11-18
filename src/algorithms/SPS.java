package algorithms;

import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;

public class SPS {
    private int[][] answer_map;
    private Cell[][] uncovered_map;
    private int num_nettles;

    public SPS(Cell[][] uncovered_map, int[][] answer_map, int num_nettles) {
        this.uncovered_map = uncovered_map;
        this.num_nettles = num_nettles;
        this.answer_map = answer_map;
    }

    /**
     * All free neighbours (return true if the number of marked-nettle neighbours is the value of the current cell).
     *
     * @param cell
     * @return
     */
    private boolean all_free_neighbours(Cell cell) {
        String value = cell.get_value();
        return Utility.is_numeric(value) && Utility.find_num_marked_nettles(cell, uncovered_map, answer_map) == Integer.parseInt(value);
    }

    /**
     * Set a value to each cell's uncovered neighbour.
     *
     * @param cell
     */
    private void uncover_neighbors(Cell cell) {
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map)
                        && uncovered_map[i][j].get_value().equals(Cell.UNCOVERED)) {
                    uncovered_map[i][j].set_value(Integer.toString(answer_map[i][j]));
                }
            }
        }
    }

    /**
     * All marked neighbours (return true if the number of uncovered neighbours
     * is the value of the current cell excluding the number of the marked nettle cells).
     *
     * @param cell
     * @return
     */
    private boolean all_marked_neighbours(Cell cell) {
        int uncovered_cell_count = 0, nettle_count = 0;
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map)
                        && uncovered_map[i][j].get_value().equals(Cell.UNCOVERED)) {
                    uncovered_cell_count++;
                }
                if (Utility.is_legal_move(i, j, answer_map)
                        && uncovered_map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    nettle_count++;
                }
            }
        }

        String value = cell.get_value();
        return Utility.is_numeric(value) && uncovered_cell_count == Integer.parseInt(value) - nettle_count;
    }

    /**
     * Mark a nettle to each the cell's uncovered neighbour.
     *
     * @param cell
     */
    private void mark_nettle_neighbours(Cell cell) {
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map)
                        && uncovered_map[i][j].get_value().equals(Cell.UNCOVERED)) {
                    uncovered_map[i][j].set_value(Cell.MARKED_NETTLE);
                }
            }
        }
    }

    /**
     * 1. Scan all cells one by one.
     * 2. For each cell that is covered, check its adjacent neighbours.
     * 3. If the cell has:
     * - All free neighbours, uncover.
     * - All marked neighbours, mark a nettle.
     * 4. Repeat until no other change can be made.
     * 5. Then resort to random guess Random Guess Strategy(RGS).
     */
    public void run() {
        Printer.set_algorithm(Printer.SPS);

        uncovered_map[0][0].set_value(Integer.toString(answer_map[0][0]));

        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {

                Printer.set_position_name(column.toString());

                if (!column.get_value().equals(Cell.UNCOVERED)) {

                    if (all_free_neighbours(column)) {
                        uncover_neighbors(column);
                    }
                    if (all_marked_neighbours(column)) {
                        mark_nettle_neighbours(column);
                    }

                }

                Utility.render_game_result(uncovered_map, answer_map, num_nettles);
            }
        }
    }
}
