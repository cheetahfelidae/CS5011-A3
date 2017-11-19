package algorithms.others;

import java.util.ArrayList;

public class Utility {
    private static int NETTLE_VALUE = -1;


    /**
     * Get an initialised map with cov cell value (since the agent has no information about the map).
     *
     * @param answer_map
     * @return
     */
    public static Cell[][] create_uncovered_map(int[][] answer_map) {
        Cell[][] map = new Cell[answer_map.length][answer_map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Cell(i, j);
            }
        }

        return map;
    }

    /**
     * Checks if the moving to a particular cell in the answer_map is legal (i.e. array out of bound).
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean is_legal_move(int x, int y, int[][] answer_map) {
        return x >= 0 && x < answer_map.length && y >= 0 && y < answer_map[0].length;
    }

    public static ArrayList<Cell> find_uncovered_cells(Cell[][] map) {
        ArrayList<Cell> uncovered = new ArrayList<>();

        for (Cell[] row : map) {
            for (Cell column : row) {
                if (column.get_value().equals(Cell.COVERED)) {
                    uncovered.add(column);
                }
            }
        }

        return uncovered;
    }

    /**
     * Reference: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
     *
     * @param str
     * @return
     */
    public static boolean is_numeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }


    /**
     * For each pair [x,y][k,j].
     * Find S[x,y] all uncovered/unmarked neighbours of [x,y].
     * Find S[k,j] all uncovered/unmarked neighbours of [k, j].
     *
     * @param cell
     * @param uncovered_map
     * @param answer_map
     * @return
     */
    public static ArrayList<Cell> find_uncovered_neighbours(Cell cell, Cell[][] uncovered_map, int[][] answer_map) {
        ArrayList<Cell> uncovered = new ArrayList<>();
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map)
                        && !(i == x && j == y)
                        && uncovered_map[i][j].get_value().equals(Cell.COVERED)) {
                    uncovered.add(uncovered_map[i][j]);
                }
            }
        }

        return uncovered;
    }

    public static int find_num_marked_neighbours(Cell cell, Cell[][] uncovered_map, int[][] answer_map) {
        int count = 0;
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map)
                        && uncovered_map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int find_num_marked_nettles(Cell[][] uncovered_map) {
        int count = 0;

        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {
                if (column.get_value().equals(Cell.MARKED_NETTLE)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static void uncover_all_but_nettles(Cell[][] uncovered_map, int[][] answer_map) {
        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {
                int x = column.get_x(), y = column.get_y();
                if (column.get_value().equals(Cell.COVERED)) {
                    column.set_value(answer_map[x][y] == NETTLE_VALUE ? Cell.MARKED_NETTLE : Integer.toString(answer_map[x][y]));
                }
            }
        }
    }

    private static int find_num_uncover_cells(Cell[][] uncovered_map) {
        int count = 0;

        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {
                if (column.get_value().equals(Cell.COVERED)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean find_nettle(Cell[][] uncovered_map) {
        for (int i = 0; i < uncovered_map.length; i++) {
            for (int j = 0; j < uncovered_map[0].length; j++) {
                if (uncovered_map[i][j].get_value().equals(Integer.toString(NETTLE_VALUE))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean is_game_over(Cell[][] uncovered_map, int[][] answer_map, int num_nettles) {

        if (find_nettle(uncovered_map)) {
            Printer.render_map(uncovered_map);
            Printer.print_game_result(Printer.GAME_LOST, uncovered_map.length);
            return true;

        } else if (Utility.find_num_marked_nettles(uncovered_map) == num_nettles - Utility.find_num_uncover_cells(uncovered_map)) {
            Utility.uncover_all_but_nettles(uncovered_map, answer_map);
            Printer.render_map(uncovered_map);
            Printer.print_game_result(Printer.GAME_WON, uncovered_map.length);
            return true;
        }

        Printer.render_map(uncovered_map);

        return false;
    }
}
