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
     * 1. Start with SPS.
     * 2. If all nettle cells are marked, game win and exit the programme.
     * 3. If all nettle cells can't be found, resort to the EES.
     * 4. Repeat from the beginning.
     * 5. If all uncovered-nettle cells can't be found, then resort to random guess.
     *
     * @param answer_map
     * @param num_nettles
     */
    private void run(int[][] answer_map, int num_nettles) {
        Printer.set_num_nettles(num_nettles);

        Cell[][] uncovered_map = Utility.create_uncovered_map(answer_map);

        SPS sps = new SPS(uncovered_map, answer_map, num_nettles);
        EES ees = new EES(uncovered_map, answer_map, num_nettles);

        for (int i = 0; i < 2; i++) {
            if (sps.run()) {
                return;
            }

            if (ees.run()) {
                return;
            }
        }

        new RGS(uncovered_map, answer_map, num_nettles).run();

    }

    /**
     * @param args three command-line arguments required which are
     *             1. the desired level of difficulty of the map ('e' for easy level, 'm' for medium level and 'h' for hard level).
     *             2. the map number (any number 1-5).
     *             3. the frame delay for rendering each move of the agent (millisecond).
     */
    public static void main(String[] args) {
        try {
            Logic2 logic2 = new Logic2();
            char level = args[0].charAt(0);
            int map_no = Integer.parseInt(args[1]);

            Printer.set_frame_delay(Integer.parseInt(args[2]));
            Printer.set_game_level(level);
            Printer.set_map_number(map_no);

            switch (level) {
                case 'e':
                case 'E':
                    logic2.run(EasyMap.get_map(map_no), EasyMap.NUM_NETTLES.get_int());
                    break;
                case 'm':
                case 'M':
                    logic2.run(MediumMap.get_map(map_no), MediumMap.NUM_NETTLES.get_int());
                    break;
                case 'h':
                case 'H':
                    logic2.run(HardMap.get_map(map_no), HardMap.NUM_NETTLES.get_int());
                    break;
                default:
                    System.out.printf("MAP LEVEL %c NOT EXIST\n", level);
            }
        } catch (Exception e) {
            System.out.println("usage: java Logic2 <level> <map_no> <frame_delay>");
        }
    }
}
