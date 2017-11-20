package algorithms;

import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;
import com.sun.deploy.pings.Pings;
import core.logic.propositional.inference.DPLLSatisfiable;
import core.logic.propositional.parsing.PLParser;
import core.logic.propositional.parsing.ast.Sentence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DLS {
    private int[][] answer_map;
    private Cell[][] uncovered_map;
    private int num_nettles;

    public DLS(Cell[][] uncovered_map, int[][] answer_map, int num_nettles) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;
        this.num_nettles = num_nettles;
    }

    private boolean has_nettle(String p, String KBU) {
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
                    column.set_num_marked_nettle(Utility.find_num_marked_neighbours(column, uncovered_map, answer_map));

                    bordering_cells.add(column);
                }
            }
        }

        return bordering_cells;
    }

    private String create_KBU(ArrayList<Cell> bordering_cells) {
        ArrayList<String> tokens = new ArrayList<>();

        for (Cell b_cell : bordering_cells) {
            ArrayList<Cell> b_neighbours = b_cell.get_uncovered_neighbours();

            int cell_value = 0;
            if (Utility.is_numeric(b_cell.get_value())) {
                cell_value = Integer.parseInt(b_cell.get_value());
                cell_value -= Utility.find_num_marked_neighbours(b_cell, uncovered_map, answer_map);
            }

            if (b_neighbours.size() == 1) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y());

                int num_not_found_nettle = Integer.parseInt(b_cell.get_value()) - b_cell.get_num_marked_nettle();

                String str = num_not_found_nettle == 0 ? String.format("N%s", x0 + y0) : String.format("(N%s | ~N%s)", x0 + y0, x0 + y0);

                tokens.add(str);

            } else if (b_neighbours.size() == 2) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y()),
                        x1 = Integer.toString(b_neighbours.get(1).get_x()), y1 = Integer.toString(b_neighbours.get(1).get_y());

                String str = String.format("(( N%s & ~N%s) " +
                                "| (~N%s &  N%s))",
                        x0 + y0, x1 + y1,
                        x0 + y0, x1 + y1);

                tokens.add(str);

            } else if (b_neighbours.size() == 3) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y()),
                        x1 = Integer.toString(b_neighbours.get(1).get_x()), y1 = Integer.toString(b_neighbours.get(1).get_y()),
                        x2 = Integer.toString(b_neighbours.get(2).get_x()), y2 = Integer.toString(b_neighbours.get(2).get_y());


                String str = "";


                if (cell_value == 2) {
                    str = String.format("(( N%s &  N%s & ~N%s) " +
                                    "| (~N%s &  N%s &  N%s) " +
                                    "| ( N%s & ~N%s &  N%s))",
                            x0 + y0, x1 + y1, x2 + y2,
                            x0 + y0, x1 + y1, x2 + y2,
                            x0 + y0, x1 + y1, x2 + y2);
                } else {
                    str = String.format("(( N%s & ~N%s & ~N%s) " +
                                    "| (~N%s &  N%s & ~N%s) " +
                                    "| (~N%s & ~N%s &  N%s))",
                            x0 + y0, x1 + y1, x2 + y2,
                            x0 + y0, x1 + y1, x2 + y2,
                            x0 + y0, x1 + y1, x2 + y2);
                }

                tokens.add(str);

            } else if (b_neighbours.size() == 4) {
                System.out.println("**************************** Implementation Needed ***************************************");

            } else if (b_neighbours.size() == 5) {
                String x0 = Integer.toString(b_neighbours.get(0).get_x()), y0 = Integer.toString(b_neighbours.get(0).get_y()),
                        x1 = Integer.toString(b_neighbours.get(1).get_x()), y1 = Integer.toString(b_neighbours.get(1).get_y()),
                        x2 = Integer.toString(b_neighbours.get(2).get_x()), y2 = Integer.toString(b_neighbours.get(2).get_y()),
                        x3 = Integer.toString(b_neighbours.get(3).get_x()), y3 = Integer.toString(b_neighbours.get(3).get_y()),
                        x4 = Integer.toString(b_neighbours.get(4).get_x()), y4 = Integer.toString(b_neighbours.get(4).get_y());

                String str = String.format("(( N%s & ~N%s & ~N%s & ~N%s & ~N%s) " +
                                "| (~N%s &  N%s & ~N%s & ~N%s & ~N%s) " +
                                "| (~N%s & ~N%s &  N%s & ~N%s & ~N%s) " +
                                "| (~N%s & ~N%s & ~N%s &  N%s & ~N%s) " +
                                "| (~N%s & ~N%s & ~N%s & ~N%s &  N%s))",
                        x0 + y0, x1 + y1, x2 + y2, x3 + y3, x4 + y4,
                        x0 + y0, x1 + y1, x2 + y2, x3 + y3, x4 + y4,
                        x0 + y0, x1 + y1, x2 + y2, x3 + y3, x4 + y4,
                        x0 + y0, x1 + y1, x2 + y2, x3 + y3, x4 + y4,
                        x0 + y0, x1 + y1, x2 + y2, x3 + y3, x4 + y4);

                tokens.add(str);
            }
        }

        System.out.println();
        Printer.print_hyphens(uncovered_map.length * 7);
        System.out.println("KBU : ");
        String KBU = "";
        for (int i = 0; i < tokens.size(); i++) {
            String str = i == tokens.size() - 1 ? tokens.get(i) : tokens.get(i) + " & ";
            System.out.println(str);

            KBU += str;
        }
        Printer.print_hyphens(uncovered_map.length * 7);

        return KBU;
    }

    private Set<Cell> get_neighbours_of_bordering_cells(ArrayList<Cell> bordering_cells) {
        Set<Cell> neighbours = new HashSet<>();

        for (Cell cell : bordering_cells) {
            neighbours.addAll(cell.get_uncovered_neighbours());
        }

        return neighbours;
    }

    /**
     * @return
     */
    public boolean run() {
        Printer.set_algorithm(Printer.DPLL);

        ArrayList<Cell> bordering_cells = get_bordering_cells(uncovered_map);
        String KBU = create_KBU(bordering_cells);

        for (Cell cell : get_neighbours_of_bordering_cells(bordering_cells)) {
            Printer.set_position_name(cell.toString());

            int x = cell.get_x(), y = cell.get_y();

            if (has_nettle(String.format("N%d%d", x, y), KBU)) {
                if (Integer.toString(answer_map[x][y]).equals("-1")) {
                    cell.set_value(Cell.MARKED_NETTLE);
                } else {
                    cell.set_value("X");
                }
            } else {
                cell.set_value(Integer.toString(answer_map[x][y]));
            }

            if (Utility.is_game_over(uncovered_map, answer_map, num_nettles)) {
                return true;
            }
        }

        return false;
    }
}
