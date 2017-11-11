import java.util.ArrayList;

public class Utility {

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

    public static ArrayList<Cell> find_uncovered(Cell[][] map) {
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
}
