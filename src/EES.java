import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EES {
    private int[][] answer_map;
    private Cell[][] map;

    EES(Cell[][] map, int[][] answer_map) {
        this.map = map;
        this.answer_map = answer_map;
    }

    private int get_num_marked_nettle(Cell cell) {
        int count = 0;
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map) && map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * For each pair [x,y][k,j] - Step 1.
     * Assume that v[x,y] is the number of nettles contained in the cell.
     * m[x,y] is the number of nettles already marked in the 8 neighbours of [x,y].
     * Total difference in undiscovered nettle value: diff = | (v[x,y] − m[x,y]) − (v[k,j] − m[k,j]) |.
     *
     * @param cell1
     * @param cell2
     */
    public int diff(Cell cell1, Cell cell2) throws NumberFormatException {
        return Math.abs((Integer.parseInt(cell1.get_value()) - get_num_marked_nettle(cell1))
                - (Integer.parseInt(cell2.get_value()) - get_num_marked_nettle(cell2)));
    }


    private ArrayList<Cell> find_uncovered(Cell cell) {
        ArrayList<Cell> uncovered = new ArrayList<>();
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map) && map[i][j].get_value().equals(Cell.UNCOVERED)) {
                    uncovered.add(map[i][j]);
                }
            }
        }

        return uncovered;
    }

    /**
     * For each pair [x,y][k,j] – Step 2.
     * Find S[x,y] all uncovered/unmarked neighbours of [x,y].
     * Find S[k,j] all uncovered/unmarked neighbours of [k, j].
     * If one set fully overlaps the other then proceed, otherwise abandon.
     */
    private boolean overlap(Cell cell1, Cell cell2) {
        ArrayList<Cell> uncovered_cell1 = find_uncovered(cell1);
        ArrayList<Cell> uncovered_cell2 = find_uncovered(cell2);

        return uncovered_cell1.containsAll(uncovered_cell2);
    }


    private ArrayList<Cell> find_intersection(ArrayList<Cell> list1, ArrayList<Cell> list2) {
        // Prepare a union
        ArrayList<Cell> union = new ArrayList<>(list1);
        union.addAll(list2);
        // Prepare an find_intersection
        ArrayList<Cell> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        // Subtract the find_intersection from the union
        union.removeAll(intersection);

        return union;
    }

    private void find_pair() {
        ArrayList<Cell> bordered_cells = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                if (!map[i][j].get_value().equals(Cell.UNCOVERED) && !map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    if (Integer.parseInt(map[i][j].get_value()) > 0) {
                        bordered_cells.add(map[i][j]);
                    }
                }

            }
        }

        ArrayList<BorderedPair> pairs = new ArrayList<>();
        for (int i = 0; i < bordered_cells.size(); i++) {
            Cell cell1 = bordered_cells.get(i);
            for (int j = i+ 1; j < bordered_cells.size(); j++) {
                Cell cell2 = bordered_cells.get(j);
                if (cell1.is_border(cell2)) {
                    pairs.add(new BorderedPair(cell1, cell2));
                }
            }
        }

        for (BorderedPair pair : pairs)
            System.out.println(pair);
    }

    /**
     * Now sub looks like this sub = s1 + s2 + . . .
     * if diff == 0, then sub = 0 ,and all si can be probed, they are all 0s, so clear!
     * If diff == |S[k,j] \ S[x,y]|, then sub = |S[k,j] \ S[x,y]|, all si are 1, all nettles, mark!
     * else abandon
     */
    public void run() {
        find_pair();
    }

}
