package algorithms.others;

public class BorderingPair {
    private Cell cell1, cell2;

    public BorderingPair(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    @Override
    public String toString() {
        return "[" + cell1.toString() + "," + cell2.toString() + "]";
    }
}
