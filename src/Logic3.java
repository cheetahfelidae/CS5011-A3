import maps.EasyMap;

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

        SPS sps = new SPS(uncovered_map, answer_map);
        sps.run();

        DLS dls = new DLS(uncovered_map, answer_map);
        dls.run();

//        RGS rgs = new RGS(uncovered_map, answer_map);
//        rgs.run();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            (new Logic3()).run(EasyMap.get_map(Integer.parseInt(args[0])));
        } else {
            System.out.println("usage: java Logic3 <map_no>");
        }
    }
}
