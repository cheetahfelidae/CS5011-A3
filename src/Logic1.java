import maps.EasyMap;

public class Logic1 {

    private void process(int[][] answer_map) {
        Cell[][] map = new Cell[answer_map.length][answer_map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
        SPS sps = new SPS(map, EasyMap.NWORLD1.value());
        sps.run();

        EES ees = new EES(map, answer_map);
        ees.run();

        //        RGS rgs = new RGS(map, EasyMap.NWORLD1.value());
//        rgs.run(Utility.find_uncovered(map));
    }

    public static void main(String[] args) {
        (new Logic1()).process(EasyMap.NWORLD1.value());
    }
}
