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
    });

    private int[][] map;

    EasyMap(int[][] map) {
        this.map = map;
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
        }

        return null;
    }
}
