package algorithms;

import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class is used to randomly select a cell to probe and identify whether a nettle is present in the selected cell.
 */
public class RGS {
    private int[][] answer_map;
    private Cell[][] uncovered_map;

    public RGS(Cell[][] uncovered_map, int[][] answer_map) {
        this.uncovered_map = uncovered_map;
        this.answer_map = answer_map;
    }

    private Integer[] get_random(int size) {
        Integer[] randoms = new Integer[size];
        for (int i = 0; i < randoms.length; i++) {
            randoms[i] = i;
        }
        Collections.shuffle(Arrays.asList(randoms));

        return randoms;
    }


    /**
     * Maintain a list of covered cells Pick a random one [x,y]
     * Probe [x,y]
     * if [x,y] is a nettle (= −1) - game over
     * if [x,y] has value > −1 - continue
     * Repeat until game won or lost
     */
    public void run() {
        Printer.set_algorithm(Printer.RGS);

        ArrayList<Cell> uncovered = Utility.find_uncovered_cells(uncovered_map);
        Integer[] randoms = get_random(uncovered.size());

        for (int i = 0; i < uncovered.size(); i++) {
            Cell cell = uncovered.get(randoms[i]);
            int x = cell.get_x(), y = cell.get_y();

            cell.set_value(Integer.toString(answer_map[x][y]));

            Printer.set_position_name(String.format("(%d,%d)", x, y));

            if (answer_map[x][y] == -1) {
                Printer.set_game_result(Printer.GAME_LOST);
                Printer.render_map(uncovered_map);
                break;
            } else if (i == uncovered.size() - 1) {
                Printer.set_game_result(Printer.GAME_WON);
            }

            Printer.render_map(uncovered_map);
        }

    }
}
