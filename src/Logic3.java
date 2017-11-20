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
     * 2. If all nettle cells are marked, game win and exit the programme
     * 3. If all nettle cells can't be found, continue no. 4
     * 4. Use a DPLL to decide on the remaining cells
     * 9. Repeat from the beginning
     *
     * @param answer_map
     * @param num_nettles
     */
    private void run(int[][] answer_map, int num_nettles) {
        Printer.set_num_nettles(num_nettles);

        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        SPS sps = new algorithms.SPS(uncovered_map, answer_map, num_nettles);
        DLS dls = new DLS(uncovered_map, answer_map, num_nettles);

        for (int i = 0; i < 2; i++) {
            if (sps.run()) {
                return;
            }

            if (dls.run()) {
                return;
            }
        }

        System.out.println("Num: " + (Utility.find_uncovered_cells(uncovered_map).size() - (num_nettles - Utility.find_num_marked_nettles(uncovered_map))));

//        RGS rgs = new RGS(uncovered_map, answer_map);
//        rgs.run();
    }

    /**
     * @param args three command-line arguments required which are
     *             1. the desired level of difficulty of the map ('e' for easy level, 'm' for medium level and 'h' for hard level).
     *             2. the map number (any number 1-5).
     *             3. the frame delay for rendering each move of the agent (millisecond).
     */
    public static void main(String[] args) {
        try {
            Logic3 logic3 = new Logic3();
            char level = args[0].charAt(0);
            int map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));
            Printer.set_game_level(level);
            Printer.set_map_number(map_no);

            switch (level) {
                case 'e':
                case 'E':
                    logic3.run(EasyMap.get_map(map_no), EasyMap.NUM_NETTLES.get_int());
                    break;
                case 'm':
                case 'M':
                    logic3.run(MediumMap.get_map(map_no), MediumMap.NUM_NETTLES.get_int());
                    break;
                case 'h':
                case 'H':
                    logic3.run(HardMap.get_map(map_no), HardMap.NUM_NETTLES.get_int());
                    break;
                default:
                    System.out.printf("MAP LEVEL %c NOT EXIST\n", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic3 <level> <map_no> <frame_delay>");
        }
    }
}
