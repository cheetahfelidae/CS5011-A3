import algorithms.*;
import algorithms.others.Cell;
import algorithms.others.Printer;
import algorithms.others.Utility;
import maps.EasyMap;
import maps.HardMap;
import maps.MediumMap;

public class Logic3 {

    /**
     * 1. Start with SPS
     * 2. Scan all cells one by one
     * 3. For each cell that is covered check its adjacent neighbours
     * 4. If the cell has:
     * - All Free Neighbours: uncover
     * - All Marked Neighbours: mark a nettle
     * 5. Repeat until no other change can be made
     * 6. Use a DPLL to decide on the remaining cells
     * 7. Repeat from the beginning
     *
     * @param answer_map
     */
    private void run(int[][] answer_map) {
        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        SPS sps = new algorithms.SPS(uncovered_map, answer_map);
        sps.run();

        DLS dls = new DLS(uncovered_map, answer_map);
        dls.run();

//        RGS rgs = new RGS(uncovered_map, answer_map);
//        rgs.run();
    }

    public static void main(String[] args) {
        try {
            Logic3 logic3 = new Logic3();
            int level = Integer.parseInt(args[0]),
                    map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));

            switch (level) {
                case 1:
                    logic3.run(EasyMap.get_map(map_no));
                    break;
                case 2:
                    logic3.run(MediumMap.get_map(map_no));
                    break;
                case 3:
                    logic3.run(HardMap.get_map(map_no));
                    break;
                default:
                    System.out.printf("MAP LEVEL %d NOT EXIST", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic3 <level> <map_no> <delay>");
        }
    }
}
