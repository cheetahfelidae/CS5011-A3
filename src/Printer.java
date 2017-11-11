public class Printer {
    public static void print_hyphens(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("-");
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
        clear_screen();
        for (Cell[] row : map) {
            for (Cell column : row) {
                System.out.print(column.get_value() + " ");
            }
            System.out.println();
        }
        print_hyphens(map.length * 2);
        sleep(100);
    }
}
