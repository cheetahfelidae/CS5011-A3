import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RGS {
    private int[][] answer_map;
    private Cell[][] map;

    RGS(Cell[][] map, int[][] answer_map) {
        this.map = map;
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

    public void run(ArrayList<Cell> uncovered) {

        Integer[] randoms = get_random(uncovered.size());

        for (int i = 0; i < uncovered.size(); i++) {
            Cell cell = uncovered.get(randoms[i]);
            int x = cell.get_x(), y = cell.get_y();

            cell.set_value(Integer.toString(answer_map[x][y]));

            Printer.print(map);

            if (answer_map[x][y] == -1) {
                System.out.println("Game is over!! ");
                break;
            }
        }
    }
}
