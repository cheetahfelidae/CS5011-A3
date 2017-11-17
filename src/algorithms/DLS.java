package algorithms;

import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;
import core.logic.propositional.inference.DPLLSatisfiable;
import core.logic.propositional.parsing.PLParser;
import core.logic.propositional.parsing.ast.Sentence;

import java.util.ArrayList;

public class DLS {
    private int[][] answer_map;
    private Cell[][] uncovered_map;

    public DLS(Cell[][] uncovered_map, int[][] answer_map) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;
    }

    private boolean has_nettle(String p, String KBU) {
        System.out.println("ProveNettle " + p);

        String prove = KBU + " & ~" + p;
        boolean ans = is_satisfied(prove);

        return !ans;
    }

    private boolean is_satisfied(String query) {
        PLParser parser = new PLParser();
        Sentence sent = parser.parse(query);

        return new DPLLSatisfiable().dpllSatisfiable(sent);
    }

    /**
     * Get covered and non-marked-nettle cells with uncovered uncovered_neighbours and number of marked nettles around each cell's uncovered_neighbours.
     *
     * @param uncovered_map
     * @return
     */
    private ArrayList<Cell> get_bordering_cells(Cell[][] uncovered_map) {
        ArrayList<Cell> bordering_cells = new ArrayList<>();

        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {
                ArrayList<Cell> uncovered_neighbours = Utility.find_uncovered_neighbours(column, uncovered_map, answer_map);

                if (Utility.is_numeric(column.get_value()) && uncovered_neighbours.size() > 0) {
                    column.set_uncovered_neighbours(uncovered_neighbours);
                    column.set_num_marked_nettle(Utility.find_num_marked_nettle(column, uncovered_map, answer_map));

                    bordering_cells.add(column);
                }
            }
        }

        return bordering_cells;
    }

    private ArrayList<String> create_KBU(ArrayList<Cell> bordering_cells) {
        ArrayList<String> tokens = new ArrayList<>();

        for (Cell b_cell : bordering_cells) {
            ArrayList<Cell> b_neighbours = b_cell.get_uncovered_neighbours();

            if (b_neighbours.size() == 1) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y());

                int num_not_found_nettle = Integer.parseInt(b_cell.get_value()) - b_cell.get_num_marked_nettle();

                String str = num_not_found_nettle == 0 ? String.format("N%s", x0 + y0) : String.format("(N%s | ~N%s)", x0 + y0, x0 + y0);

                tokens.add(str);

            } else if (b_neighbours.size() == 2) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y()),
                        x1 = Integer.toString(b_neighbours.get(1).get_x()), y1 = Integer.toString(b_neighbours.get(1).get_y());

                tokens.add(String.format("((N%s & ~N%s) " +
                                "| (~N%s &  N%s))",
                        x0 + y0, x1 + y1,
                        x0 + y0, x1 + y1));

            } else if (b_neighbours.size() == 3) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y()),
                        x1 = Integer.toString(b_neighbours.get(1).get_x()), y1 = Integer.toString(b_neighbours.get(1).get_y()),
                        x2 = Integer.toString(b_neighbours.get(2).get_x()), y2 = Integer.toString(b_neighbours.get(2).get_y());

                tokens.add(String.format("((N%s & ~N%s & ~N%s) " +
                                "| (~N%s &  N%s & ~N%s) " +
                                "| (~N%s & ~N%s & N%s))",
                        x0 + y0, x1 + y1, x2 + y2,
                        x0 + y0, x1 + y1, x2 + y2,
                        x0 + y0, x1 + y1, x2 + y2));

            }
        }

        return tokens;
    }

    public void run() {
        Printer.set_algorithm(Printer.DPLL);

        ArrayList<String> tokens = create_KBU(get_bordering_cells(uncovered_map));

        String KBU = "";
        for (int i = 0; i < tokens.size(); i++) {
            KBU += i == tokens.size() - 1 ? tokens.get(i) : tokens.get(i) + " & ";
        }

        System.out.println(KBU);
        System.out.println();
        ArrayList<Cell> uncovered_cells = Utility.find_uncovered_cells(uncovered_map);

        for (Cell cell : uncovered_cells) {
            String x = Integer.toString(cell.get_x()), y = Integer.toString(cell.get_y());

            if (has_nettle("N" + x + y, KBU)) {
                System.out.println("Yes, Nettle, Mark");
            } else {
                System.out.println("No");
            }
            System.out.println();
        }
    }
}
