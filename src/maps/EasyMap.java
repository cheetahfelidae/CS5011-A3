package maps;

public enum EasyMap {
    NWORLD1(new int[][]{
            {0, 0, 0, 2, -1},
            {0, 0, 0, 2, -1},
            {1, 2, 1, 2, 1},
            {-1, 3, -1, 2, 0},
            {1, 3, -1, 2, 0},
    }),
    NWORLD2(new int[][]{
            {0, 0, 1, -1, -1},
            {0, 0, 1, 2, 2},
            {1, 2, 1, 1, 0},
            {-1, 2, -1, 2, 1},
            {1, 2, 2, -1, 1},
    }),
    NWORLD3(new int[][]{
            {0, 0, 1, 2, 2},
            {0, 1, 3, -1, -1},
            {0, 2, -1, -1, 3},
            {0, 2, -1, 3, 1},
            {0, 1, 1, 1, 0},
    }),
    NWORLD4(new int[][]{
            {0, 0, 0, 0, 0},
            {1, 2, 1, 1, 0},
            {-1, 2, -1, 1, 0},
            {3, 5, 3, 2, 0},
            {-1, -1, -1, 1, 0},
    }),
    NWORLD5(new int[][]{
            {0, 0, 0, 1, -1},
            {1, 1, 1, 1, 1},
            {2, -1, 2, 0, 0},
            {3, -1, 3, 1, 0},
            {-1, 3, -1, 1, 0},
    }),
    NUM_NETTLES(5);

    private int[][] map;
    private int num_nettles;

    EasyMap(int[][] map) {
        this.map = map;
    }

    EasyMap(int num_nettles) {
        this.num_nettles = num_nettles;
    }

    public int get_int() {
        return num_nettles;
    }

    private int[][] value() {
        return map;
    }

    public static int[][] get_map(int num) {
        switch (num) {
            case 1:
                return NWORLD1.value();
            case 2:
                return NWORLD2.value();
            case 3:
                return NWORLD3.value();
            case 4:
                return NWORLD4.value();
            case 5:
                return NWORLD5.value();
            default:
                System.out.printf("MAP NO %d NOT EXIST\n", num);
        }

        return null;
    }
}
