import algorithms.*;
import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;
import maps.EasyMap;
import maps.HardMap;
import maps.MediumMap;

/**
 * The single point strategy (SPS + RGS).
 */
public class Logic1 {

    /**
     * 1. Scan all cells one by one
     * 2. For each cell that is covered, check its adjacent neighbours if the cell has:
     * - All Free Neighbours: uncover
     * - All Marked Neighbours: mark a nettle
     * 3. Repeat until no other change can be made
     * 4. If all uncovered-nettle cells are marked, game is won and exit the programme
     * 5. If all uncovered-nettle cells can't be found, then resort to random guess RGS
     *
     * @param answer_map
     * @param num_nettles
     */
    private void run(int[][] answer_map, int num_nettles) {
        Printer.set_num_nettles(num_nettles);

        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        new SPS(uncovered_map, answer_map, num_nettles).run();

        new RGS(uncovered_map, answer_map, num_nettles).run();
    }

    public static void main(String[] args) {
        try {
            Logic1 logic1 = new Logic1();
            char level = args[0].charAt(0);
            int map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));

            switch (level) {
                case 'e':
                case 'E':
                    logic1.run(EasyMap.get_map(map_no), EasyMap.NUM_NETTLES.get_int());
                    break;
                case 'm' :
                case 'M':
                    logic1.run(MediumMap.get_map(map_no), MediumMap.NUM_NETTLES.get_int());
                    break;
                case 'h' :
                case 'H':
                    logic1.run(HardMap.get_map(map_no), HardMap.NUM_NETTLES.get_int());
                    break;
                default:
                    System.out.printf("MAP LEVEL %d NOT EXIST", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic1 <level> <map_no> <delay>");
        }
    }
}
