import maps.EasyMap;

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

        SPS sps = new SPS(uncovered_map, answer_map);
        sps.run();

//        RGS rgs = new RGS(uncovered_map, answer_map);
//        rgs.run();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            (new Logic1()).run(EasyMap.get_map(Integer.parseInt(args[0])));
        } else {
            System.out.println("usage: java Logic1 <map_no>");
        }
    }
}
