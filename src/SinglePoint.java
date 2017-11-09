import java.util.ArrayList;

public class SinglePoint {
    private int[][] answer_map;
    private Cell[][] map;

    SinglePoint(int[][] answer_map) {
        this.answer_map = answer_map;

        map = new Cell[answer_map.length][answer_map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * All free neighbours (return true if the number of marked-nettle neighbour cells is the value of the current cell).
     *
     * @param cell
     * @return
     */
    private boolean all_free_neighbours(Cell cell) {
        int count = 0;

        for (Cell[] row : map) {
            for (Cell column : row) {
                if (column.get_value().equals(Cell.MARKED_NETTLE)) {
                    count++;
                }
            }
        }

        return count == Integer.parseInt(cell.get_value());
    }

    /**
     * Checks if the moving to a particular cell in the answer_map is legal (i.e. array out of bound).
     *
     * @param x
     * @param y
     * @return
     */
    private boolean is_legal_move(int x, int y) {
        return x >= 0 && x < answer_map.length && y >= 0 && y < answer_map[0].length;
    }

    private void uncover_neighbors(Cell cell) {
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = x - 1; j <= y + 1; j++) {
                if (is_legal_move(i, j) && i != x && j != y) {
                    map[i][j].set_value(Integer.toString(answer_map[i][j]));
                }
            }
        }
    }

    /**
     * All marked neighbours (return true if the number of uncovered neighbour cells is the value of the current cell).
     *
     * @param cell
     * @return
     */
    private boolean all_marked_neighbours(Cell cell) {
        int count = 0;

        for (Cell[] row : map) {
            for (Cell column : row) {
                if (column.get_value().equals(Cell.UNCOVERED)) {
                    count++;
                }
            }
        }

        return count == Integer.parseInt(cell.get_value());
    }

    /**
     * Find uncovered cells and mark them a nettle.
     *
     * @param cell
     */
    private void mark_nettle(Cell cell) {
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = x - 1; j <= y + 1; j++) {
                if (is_legal_move(i, j) && i != x && j != y) {
                    if (map[i][j].equals(Cell.UNCOVERED)) {
                        map[i][j].set_value(Integer.toString(answer_map[i][j]));
                    }
                }
            }
        }
    }

    /**
     * This method is used to clean screen to be able to render a motion.
     */
    public static void clear_screen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * This makes the programme sleep for a specific amount of time.
     *
     * @param millis the number of milli seconds of the thread sleep.
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print() {
        clear_screen();
        for (Cell[] row : map) {
            for (Cell column : row) {
                System.out.print(column.get_value() + " ");
            }
            System.out.println();
        }
        sleep(1000);
    }

    /**
     * 1. Scan all cells one by one.
     * 2. For each cell that is covered, check its adjacent neighbours.
     * 3. If the cell has:
     * 3.1 All free neighbours, uncover.
     * 3.2 All marked neighbours, mark a nettle.
     * 4. Repeat until no other change can be made.
     * 5. Then resort to random guess Random Guess Strategy(RGS).
     */
    public void run() {

        for (int i = 0; i < answer_map.length; i++) {
            for (int j = 0; j < answer_map.length; j++) {

                map[i][j].set_value(Integer.toString(answer_map[i][j])); // 2

                if (!map[i][j].get_value().equals("-1")) {
                    if (all_free_neighbours(map[i][j])) {// 3.1
                        uncover_neighbors(map[i][j]);
                    }

                    if (all_marked_neighbours(map[i][j])) {// 3.2
                        mark_nettle(map[i][j]);
                    }
                } else {
                    System.out.println("Game is over!! ");
                    return;
                }

                print();
            }
        }
    }
}
