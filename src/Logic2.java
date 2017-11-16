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
     * 6. Get all the frontiers
     * 7. Compare pairs of bordering cells in the frontiers to uncover some new cells
     * 8. Repeat from the beginning
     * 9. then resort to random guess
     *
     * @param answer_map
     */
    private void run(int[][] answer_map) {
        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        SPS sps = new SPS(uncovered_map, answer_map);
        sps.run();

        EES ees = new EES(uncovered_map, answer_map);
        ees.run();

        sps.run();

        ees.run();

        RGS rgs = new RGS(uncovered_map, answer_map);
        rgs.run();
    }

    public static void main(String[] args) {
        try {
            Logic2 logic2 = new Logic2();
            int level = Integer.parseInt(args[0]),
                    map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));

            switch (level) {
                case 1:
                    logic2.run(EasyMap.get_map(map_no));
                    break;
                case 2:
                    logic2.run(MediumMap.get_map(map_no));
                    break;
                case 3:
                    logic2.run(HardMap.get_map(map_no));
                    break;
                default:
                    System.out.printf("MAP LEVEL %d NOT EXIST", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic2 <level> <map_no> <delay>");
        }
    }
}
