import core.logic.propositional.inference.DPLLSatisfiable;
import core.logic.propositional.parsing.PLParser;
import core.logic.propositional.parsing.ast.Sentence;

import java.util.ArrayList;

public class DLS {
    private DPLLSatisfiable dpll;

    private int[][] answer_map;
    private Cell[][] uncovered_map;

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
    String KBU = "((N20 & ~N21) | (~N20 &  N21)) " +
            "& ((N20 & ~N21 & ~N22) | (~N20 &  N21 & ~N22) | (~N20 & ~N21 & N22))" +
            "& ((N21 & ~N22) | (~N21 & N22))";
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
        System.out.println("Does KBU entail " + p + "?, Test KBU & ~" + p);

        return !ans;
    }


    private boolean displayDPLLSatisfiableStatus(String query) {
        PLParser parser = new PLParser();
        Sentence sent = parser.parse(query);
        if (dpll.dpllSatisfiable(sent)) {
            System.out.println(query + " is satisfiable");
            return true;
        } else {
            System.out.println(query + " is NOT satisfiable");
            return false;
        }
    }


    public void run() {
        Printer.set_algorithm(Printer.DPLL);

        ArrayList<Cell> uncovered_cells = Utility.find_uncovered_cells(uncovered_map);
        ArrayList<String> KBU = new ArrayList<>();

//        String KBU = "((N20 & ~N21) | (~N20 &  N21)) " +
//                "& ((N20 & ~N21 & ~N22) | (~N20 &  N21 & ~N22) | (~N20 & ~N21 & N22))" +
//                "& ((N21 & ~N22) | (~N21 & N22))";
//
        for (Cell cell : uncovered_cells) {
            ArrayList<Cell> neighbours = Utility.find_uncovered_neighbours(cell, uncovered_map, answer_map);

            if (neighbours.size() == 2) {
                String x0 = Integer.toString(neighbours.get(0).get_x()), y0 = Integer.toString(neighbours.get(0).get_y()),
                        x1 = Integer.toString(neighbours.get(1).get_x()), y1 = Integer.toString(neighbours.get(1).get_y());

                KBU.add(String.format("((N%s & ~N%s) " +
                                "| (~N%s &  N%s))",
                        x0 + y0, x1 + y1,
                        x0 + y0, x1 + y1));
            } else if (neighbours.size() == 3) {
                String x0 = Integer.toString(neighbours.get(0).get_x()), y0 = Integer.toString(neighbours.get(0).get_y()),
                        x1 = Integer.toString(neighbours.get(1).get_x()), y1 = Integer.toString(neighbours.get(1).get_y()),
                        x2 = Integer.toString(neighbours.get(2).get_x()), y2 = Integer.toString(neighbours.get(2).get_y());

                KBU.add(String.format("((N%s & ~N%s & ~N%s) " +
                                "| (~N%s &  N%s & ~N%s) " +
                                "| (~N%s & ~N%s & N%s))",
                        x0 + y0, x1 + y1, x2 + y2,
                        x0 + y0, x1 + y1, x2 + y2,
                        x0 + y0, x1 + y1, x2 + y2));
            }
        }

        for (String str : KBU)
            System.out.println(str);

    }
}
