import java.util.ArrayList;
import java.util.Collections;

public class EES {
    private int[][] answer_map;
    private Cell[][] uncovered_map;

    EES(Cell[][] uncovered_map, int[][] answer_map) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;
    }

    private int get_num_marked_nettle_neighbours(Cell cell) {
        int count = 0;
        int x = cell.get_x(), y = cell.get_y();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Utility.is_legal_move(i, j, answer_map) && uncovered_map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * For each pair [x,y][k,j].
     * Assume that v[x,y] is the number of nettles contained in the cell.
     * m[x,y] is the number of nettles already marked in the 8 neighbours of [x,y].
     * Total difference in undiscovered nettle value: diff = | (v[x,y] − m[x,y]) − (v[k,j] − m[k,j]) |.
     *
     * @param cell1
     * @param cell2
     * @return
     * @throws NumberFormatException
     */
    public int diff(Cell cell1, Cell cell2) throws NumberFormatException {
        return Math.abs((Integer.parseInt(cell1.get_value()) - get_num_marked_nettle_neighbours(cell1))
                - (Integer.parseInt(cell2.get_value()) - get_num_marked_nettle_neighbours(cell2)));
    }

    private ArrayList<Cell> find_subtraction(ArrayList<Cell> list1, ArrayList<Cell> list2) {

        ArrayList<Cell> union = new ArrayList<>(list1);
        union.addAll(list2);

        ArrayList<Cell> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);

        union.removeAll(intersection);

        return union;
    }

    private ArrayList<BorderedPair> find_pair() {
        ArrayList<Cell> bordered_cells = new ArrayList<>();
        for (int i = 0; i < uncovered_map.length; i++) {
            for (int j = 0; j < uncovered_map[0].length; j++) {

                if (!uncovered_map[i][j].get_value().equals(Cell.UNCOVERED) && !uncovered_map[i][j].get_value().equals(Cell.MARKED_NETTLE)) {
                    if (Integer.parseInt(uncovered_map[i][j].get_value()) > 0) {
                        bordered_cells.add(uncovered_map[i][j]);
                    }
                }

            }
        }

        ArrayList<BorderedPair> pairs = new ArrayList<>();
        for (int i = 0; i < bordered_cells.size(); i++) {
            Cell cell1 = bordered_cells.get(i);
            for (int j = i + 1; j < bordered_cells.size(); j++) {
                Cell cell2 = bordered_cells.get(j);
                if (cell1.is_border(cell2)) {
                    pairs.add(new BorderedPair(cell1, cell2));
                }
            }
        }

        return pairs;
    }

    private void uncover_cells(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            int x = cell.get_x(), y = cell.get_y();
            cell.set_value(Integer.toString(answer_map[x][y]));
        }
    }

    private void mark_nettle_cells(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            int x = cell.get_x(), y = cell.get_y();
            cell.set_value(Cell.MARKED_NETTLE);
        }
    }

    /**
     * If one set fully overlaps the other then proceed.
     * if diff == 0, then sub = 0 ,and all si can be probed, they are all 0s, so clear!
     * If diff == |S[k,j] \ S[x,y]|, then sub = |S[k,j] \ S[x,y]|, all si are 1, all nettles, mark!
     * otherwise abandon.
     */
    public void run() {
        Printer.set_algorithm(Printer.EES);

        ArrayList<BorderedPair> pairs = find_pair();
        Collections.reverse(pairs);// make the order of the bordered pairs running from the bottom to the top.

        for (BorderedPair pair : pairs) {
            Cell cell1 = pair.getCell1(), cell2 = pair.getCell2();
            ArrayList<Cell> uncovered_cells1 = Utility.find_uncovered_neighbours(cell1, uncovered_map, answer_map),
                    uncovered_cells2 = Utility.find_uncovered_neighbours(cell2, uncovered_map, answer_map);

            if (uncovered_cells2.containsAll(uncovered_cells1)) { // return true if uncovered_cell1 is a subset of uncovered_cell2.
                ArrayList<Cell> intersection = find_subtraction(uncovered_cells1, uncovered_cells2);

                if (intersection.size() > 0) {
                    int diff = diff(cell1, cell2);

                    if (diff == 0) {
                        uncover_cells(intersection);
                    } else if (diff == intersection.size()) {
                        mark_nettle_cells(intersection);
                    }
                }

            }

            Printer.set_position_name(cell1 + " " + cell2);
            Printer.print(uncovered_map);
        }

    }

}
