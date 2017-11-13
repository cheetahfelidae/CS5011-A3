public class Printer {
    public static final String SPS = "Single Point Strategy";
    public static final String RGS = "Random Guess Strategy";
    public static final String EES = "Easy Equation Strategy";

    private static String algorithm;
    private static int round = 1;

    public static void set_algorithm(String name) {
        algorithm = name;
    }

    public static void print_hyphens(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void print_asterisks(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    /**
     * This method is used to clean screen to be able to render a motion.
     */
    public static void clear_screen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * This makes the programme sleep for a specific amount of time.
     *
     * @param millis the number of milli seconds of the thread sleep.
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void print(Cell[][] map) {
        final String space = " ";

        clear_screen();

        print_hyphens(map.length * 3);
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Round: " + round++);
        print_hyphens(map.length * 3);
        for (Cell[] row : map) {
            for (Cell column : row) {
                String value = column.get_value();
                System.out.print(value.length() == 2 ? value + space : space + value + space);
            }
            System.out.println();
        }
        print_hyphens(map.length * 3);

        sleep(0);
    }
}
