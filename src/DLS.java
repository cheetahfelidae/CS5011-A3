import core.logic.propositional.inference.DPLLSatisfiable;
import core.logic.propositional.parsing.PLParser;
import core.logic.propositional.parsing.ast.Sentence;
import core.util.Util;

import java.util.ArrayList;

public class DLS {
    private DPLLSatisfiable dpll;

    private int[][] answer_map;
    private Cell[][] uncovered_map;

    private class BorderedCell {
        private Cell cell;
        private ArrayList<Cell> neighbours;

        BorderedCell(Cell cell, ArrayList<Cell> neighbours) {
            this.cell = cell;
            this.neighbours = neighbours;
        }

        public Cell get_cell() {
            return cell;
        }

        public ArrayList<Cell> get_neighbours() {
            return neighbours;
        }
    }

    DLS(Cell[][] uncovered_map, int[][] answer_map) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;

        dpll = new DPLLSatisfiable();
    }

    //    public static void main(String[] args) {
//        String a = "( (A & B) & C)";
//        String b = "( A & (~ A) )";
//        String c = "( (A | B) & C)";
//        displayDPLLSatisfiableStatus(a);
//        displayDPLLSatisfiableStatus(b);
//        displayDPLLSatisfiableStatus(c);
//
//        String p = "N20";
//        System.out.println("ProveNotNettle " + p);
//
//    String KBU = "((N20 & ~N21) | (~N20 &  N21)) " +
//            "& ((N20 & ~N21 & ~N22) | (~N20 &  N21 & ~N22) | (~N20 & ~N21 & N22))" +
//            "& ((N21 & ~N22) | (~N21 & N22))";
//
//        String prove = KBU + " & " + p;
//        boolean ans = displayDPLLSatisfiableStatus(prove);
//        System.out.println("Does KBU entail ~" + p + "?, Test KBU & " + p);
//
//        if (!ans) {//if false probe
//            System.out.println("Yes, No Nettle, Probe");
//        } else {
//            System.out.println("No");
//        }
//    }

    private boolean has_nettle(String p, String KBU) {
        System.out.println("ProveNettle " + p);

        String prove = KBU + " & ~" + p;
        boolean ans = displayDPLLSatisfiableStatus(prove);
//        System.out.println("Does KBU entail " + p + "?, Test KBU & ~" + p);

        return !ans;
    }


    private boolean displayDPLLSatisfiableStatus(String query) {
        PLParser parser = new PLParser();
        Sentence sent = parser.parse(query);
        if (dpll.dpllSatisfiable(sent)) {
//            System.out.println(query + " is satisfiable");
            return true;
        } else {
//            System.out.println(query + " is NOT satisfiable");
            return false;
        }
    }

    private ArrayList<BorderedCell> get_bordered_cells(Cell[][] uncovered_map) {
        ArrayList<BorderedCell> bordered_cells = new ArrayList<>();

        for (Cell[] row : uncovered_map) {
            for (Cell column : row) {
                ArrayList<Cell> uncovered_neighbours = Utility.find_uncovered_neighbours(column, uncovered_map, answer_map);

                if (Utility.is_numeric(column.get_value()) && uncovered_neighbours.size() > 0) {
                    bordered_cells.add(new BorderedCell(column, uncovered_neighbours));
                }
            }
        }

        return bordered_cells;
    }


    public void run() {
        Printer.set_algorithm(Printer.DPLL);

        ArrayList<BorderedCell> bordered_cells = get_bordered_cells(uncovered_map);
        ArrayList<String> tokens = new ArrayList<>();

        for (BorderedCell b_cell : bordered_cells) {
            ArrayList<Cell> b_neighbours = b_cell.neighbours;

            if (b_neighbours.size() == 1) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y());

                tokens.add(String.format("(N%s | ~N%s)", x0 + y0, x0 + y0));
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

        String KBU = "";
        for (int i = 0; i < tokens.size(); i++) {
            if (i == tokens.size() - 1) {
                KBU += tokens.get(i);
            } else {
                KBU += tokens.get(i) + " & ";

            }
        }

        System.out.println(KBU);
        ArrayList<Cell> uncovered_cells = Utility.find_uncovered_cells(uncovered_map);

        for (Cell cell : uncovered_cells) {
            String x = Integer.toString(cell.get_x()), y = Integer.toString(cell.get_y());
            boolean ans = has_nettle("N" + x + y, KBU);
            if (ans) {//if false probe
                System.out.println("Yes, No Nettle, Probe");
            } else {
                System.out.println("No");
            }
        }
    }
}
