package algorithms;

import algorithms.others.BorderingPair;
import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;

import java.util.ArrayList;
import java.util.Collections;

public class EES {
    private int[][] answer_map;
    private Cell[][] uncovered_map;
    private int num_nettles;

    public EES(Cell[][] uncovered_map, int[][] answer_map, int num_nettles) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;
        this.num_nettles = num_nettles;
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
    private int diff(Cell cell1, Cell cell2) throws NumberFormatException {
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

    private ArrayList<BorderingPair> find_pair() {
        ArrayList<Cell> bordering_cells = new ArrayList<>();
        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {

                if (!column.get_value().equals(Cell.UNCOVERED) && !column.get_value().equals(Cell.MARKED_NETTLE)) {
                    if (Integer.parseInt(column.get_value()) > 0) {
                        bordering_cells.add(column);
                    }
                }

            }
        }

        ArrayList<BorderingPair> pairs = new ArrayList<>();
        for (int i = 0; i < bordering_cells.size(); i++) {
            Cell cell1 = bordering_cells.get(i);
            for (int j = i + 1; j < bordering_cells.size(); j++) {
                Cell cell2 = bordering_cells.get(j);
                if (cell1.is_border(cell2)) {
                    pairs.add(new BorderingPair(cell1, cell2));
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

    private void mark_nettle(ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            cell.set_value(Cell.MARKED_NETTLE);
        }
    }

    /**
     * If one set fully overlaps the other then proceed.
     * if diff == 0, then sub = 0, and all si can be probed, they are all 0s, so clear!
     * If diff == |S[k,j] \ S[x,y]|, then sub = |S[k,j] \ S[x,y]|, all si are 1, all nettles, mark!
     * otherwise abandon.
     *
     * @return
     */
    public void run() {
        Printer.set_algorithm(Printer.EES);

        ArrayList<BorderingPair> pairs = find_pair();
        Collections.reverse(pairs);// make the order of the bordering pairs running from the bottom to the top.

        for (BorderingPair pair : pairs) {
            Cell cell1 = pair.getCell1(), cell2 = pair.getCell2();
            ArrayList<Cell> uncovered_cells1 = Utility.find_uncovered_neighbours(cell1, uncovered_map, answer_map),
                    uncovered_cells2 = Utility.find_uncovered_neighbours(cell2, uncovered_map, answer_map);

            if (uncovered_cells2.containsAll(uncovered_cells1)) { // return true if uncovered_cell1 is a subset of uncovered_cell2.
                ArrayList<Cell> intersection = find_subtraction(uncovered_cells1, uncovered_cells2);

                if (intersection.size() > 0) {
                    try {
                        int diff = diff(cell1, cell2);

                        if (diff == 0) {
                            uncover_cells(intersection);
                        } else if (diff == intersection.size()) {
                            mark_nettle(intersection);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Cell " + cell1 + " || " + cell2 + " : " + e.getMessage());
                    }
                }

            }

            Printer.set_position_name(cell1 + " " + cell2);

            Utility.render_game_result(uncovered_map, answer_map, num_nettles);
        }

    }

}
