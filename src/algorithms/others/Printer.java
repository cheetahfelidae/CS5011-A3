package algorithms.others;

public class Printer {
    public static final String SPS = "Single Point Strategy";
    public static final String RGS = "Random Guess Strategy";
    public static final String EES = "Easy Equation Strategy";
    public static final String DPLL = "Davis-Putnam-Logemann-Loveland Algorithm";
    public static final String GAME_LOST = "Found a nettle: the game is over!!";
    public static final String GAME_WON = "All nettles are marked: the game is won!!";

    private static int frame_delay;
    private static String algorithm;
    private static int num_nettles;
    private static int round = 1;
    private static String position_name;
    private static int random_count = 1;

    public static void set_frame_delay(int delay) {
        Printer.frame_delay = delay;
    }

    public static void set_algorithm(String algorithm) {
        Printer.algorithm = algorithm;
    }

    public static void set_num_nettles(int num_nettles) {
        System.out.println(num_nettles);
        Printer.num_nettles = num_nettles;
    }

    public static void set_position_name(String position_name) {
        Printer.position_name = position_name;
    }

    private static void print_hyphens(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static void print_asterisks(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    /**
     * This method is used to clean screen to be able to render a motion.
     */
    private static void clear_screen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * This makes the programme sleep for a specific amount of time.
     *
     * @param millis the number of milli seconds of the thread sleep.
     */
    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used at each move of the position, a map will be rendered indicating the action of the agent or the state of the game;
     * e.g revealing (x,y) for uncovering a cell in [x,y] coordinates,
     * marking (x,y) for marking the presence of a nettle in [x,y],
     * game won or game lost.
     *
     * @param map
     */
    public static void render_map(Cell[][] map) {
        final String space = " ";

        clear_screen();

        print_hyphens(map.length * 7);

        System.out.println("Algorithm: " + algorithm);
        System.out.println("#Marked Nettles: " + Utility.find_num_marked_nettles(map) + "/" + num_nettles);
        System.out.println("Round: " + round++);
        System.out.println("Current Position: " + position_name);
        if (algorithm.equals(RGS)) {
            System.out.println("#Random: " + random_count++);
        }

        print_hyphens(map.length * 3);
        for (Cell[] row : map) {
            for (Cell column : row) {
                String value = column.get_value();
                System.out.print(value.length() == 2 ? value + space : space + value + space);
            }
            System.out.println();
        }
        print_hyphens(map.length * 3);

        sleep(frame_delay);
    }

    public static void print_game_result(String result, int asterisks_length) {
        print_asterisks(asterisks_length * 7);

        System.out.println(result);

        print_asterisks(asterisks_length * 7);
    }
}
