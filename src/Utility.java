import java.util.ArrayList;

public class Utility {

    public static Cell[][] get_uncovered_map(int[][] answer_map) {
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
                if (column.get_value().equals(Cell.UNCOVERED)) {
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
                        && uncovered_map[i][j].get_value().equals(Cell.UNCOVERED)) {
                    uncovered.add(uncovered_map[i][j]);
                }
            }
        }

        return uncovered;
    }
}
