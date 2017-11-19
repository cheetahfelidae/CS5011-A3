import algorithms.*;
import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;
import maps.EasyMap;
import maps.HardMap;
import maps.MediumMap;

/**
 * (RGS + SPS + EES).
 */
public class Logic2 {
    /**
     * 1. Start with SPS
     * 2. Scan all cells one by one
     * 3. For each cell that is covered check its adjacent neighbours
     * 4. If the cell has:
     * - All Free Neighbours: uncover
     * - All Marked Neighbours: mark a nettle
     * 5. Repeat until no other change can be made
     * 6. If all uncovered-nettle cells are marked, game win and exit the programme
     * 7. If all uncovered-nettle cells can't be found, continue no. 8
     * 8. Get all the frontiers
     * 9. Compare pairs of bordering cells in the frontiers to uncover some new cells
     * 10. Repeat from the beginning
     * 11. If all uncovered-nettle cells can't be found, then resort to random guess
     *
     * @param answer_map
     * @param num_nettles
     */
    private void run(int[][] answer_map, int num_nettles) {
        Printer.set_num_nettles(num_nettles);

        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        SPS sps = new SPS(uncovered_map, answer_map, num_nettles);
        sps.run();

        EES ees = new EES(uncovered_map, answer_map, num_nettles);
        ees.run();

        sps.run();

        ees.run();

        new RGS(uncovered_map, answer_map, num_nettles).run();

    }

    public static void main(String[] args) {
        try {
            Logic2 logic2 = new Logic2();
            char level = args[0].charAt(0);
            int map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));

            switch (level) {
                case 'e':
                case 'E':
                    logic2.run(EasyMap.get_map(map_no), EasyMap.NUM_NETTLES.get_int());
                    break;
                case 'm' :
                case 'M':
                    logic2.run(MediumMap.get_map(map_no), MediumMap.NUM_NETTLES.get_int());
                    break;
                case 'h' :
                case 'H':
                    logic2.run(HardMap.get_map(map_no), HardMap.NUM_NETTLES.get_int());
                    break;
                default:
                    System.out.printf("MAP LEVEL %d NOT EXIST\n", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic2 <level> <map_no> <delay>");
        }
    }
}
