import maps.EasyMap;
import maps.HardMap;
import maps.MediumMap;

/**
 * The single point strategy (SPS + RGS).
 */
public class Logic1 {

    /**
     * 1. Scan all cells one by one
     * 2. For each cell that is covered check its adjacent neighbours If the cell has:
     * - All Free Neighbours: uncover
     * - All Marked Neighbours: mark a nettle
     * 3. Repeat until no other change can be made
     * 4. Then resort to random guess RGS
     *
     * @param answer_map
     */
    private void run(int[][] answer_map) {
        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        new SPS(uncovered_map, answer_map).run();

        new RGS(uncovered_map, answer_map).run();
    }

    public static void main(String[] args) {
        try {
            Logic1 logic1 = new Logic1();
            int level = Integer.parseInt(args[0]),
                    map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));

            switch (level) {
                case 1:
                    logic1.run(EasyMap.get_map(map_no));
                    break;
                case 2:
                    logic1.run(MediumMap.get_map(map_no));
                    break;
                case 3:
                    logic1.run(HardMap.get_map(map_no));
                    break;
                default:
                    System.out.printf("MAP LEVEL %d NOT EXIST", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic1 <level> <map_no> <delay>");
        }
    }
}
