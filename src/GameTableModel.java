import javax.swing.table.AbstractTableModel;
import java.util.*;

public class GameTableModel extends AbstractTableModel {
    private final int  row;
    private final int column;
    private final int middleRow;
    private final int middleColumn;
    private final int baseRightColumn;
    private final int baseTopRow;
    private final int baseLeftColumn;
    private final int baseBottomRow;

    private Object[][] cells;

    public GameTableModel(int row, int column) {
        this.row = row;
        this.column = column;
        cells = new Object[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cells[i][j] = 0;
            }
        }
        middleRow = row / 2;
        middleColumn = column / 2;
        baseLeftColumn = middleColumn - 2;
        baseTopRow = middleRow - 2;
        baseRightColumn = middleColumn + 2;
        baseBottomRow = middleRow + 2;
        mazeGenerator();
        arrangeRows();
        makeBase();

    }

    @Override
    public int getRowCount() {
        return row;
    }

    @Override
    public int getColumnCount() {
        return column;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex >= 0 && rowIndex >= 0&& rowIndex < row && columnIndex < column)
            return cells[rowIndex][columnIndex];
        return CellType.WALL.getValue();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(rowIndex >= 0 && columnIndex >= 0 && rowIndex < row && columnIndex < column) {
            cells[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    private void makeVerticalWay(int column, int pos1, int pos2) {
        for (int i = pos1; i <= pos2; i++) {
            cells[i][column] = 1;
        }
    }

    private void makeHorizontalWay(int row, int pos1, int pos2) {
        for (int i = pos1; i <= pos2; i++) {
            cells[row][i] = 1;
        }
    }
    private void makeHorizontalWall(int row, int pos1, int pos2){
        for(int i = pos1; i <= pos2; i++) {
            if(i == column - 1)
                break;
            cells[row][i] = 0;
        }

    }
    private void makeVerticalWall(int column, int pos1, int pos2){
        for(int i = pos1; i <= pos2; i++){
            if(i == row - 1)
                break;
            cells[i][column] = 0;
        }
    }

    private void makeBase() {
        makeVerticalWall(baseLeftColumn, baseTopRow, baseBottomRow);
        makeVerticalWall(baseRightColumn, baseTopRow, baseBottomRow);

        makeHorizontalWall(baseBottomRow, baseLeftColumn, baseRightColumn);
        makeHorizontalWall(baseTopRow, baseLeftColumn, baseRightColumn);
        for(int i = baseLeftColumn + 1; i < baseRightColumn; i++){
            makeVerticalWay(i, baseTopRow +1, baseBottomRow -1);
        }
        cells[baseTopRow][middleColumn] = 2;
        makeBaseCellsClear();
        makeBaseSidesClear();
        makeHorizontalWay(1,1,column-2);
        makeHorizontalWay(row-2,1,column-2);
        makeVerticalWay(1,1,row-2);
        makeVerticalWay(column-2,1,row-2);
    }
    private void makeBaseSidesClear(){
        makeVerticalWay(baseLeftColumn-1,baseTopRow-1,baseBottomRow+1);
        makeVerticalWay(baseRightColumn+1,baseTopRow-1,baseBottomRow+1);
        makeHorizontalWay(baseTopRow-1,baseLeftColumn-1,baseRightColumn+1);
        makeHorizontalWay(baseBottomRow+1,baseLeftColumn-1,baseRightColumn+1);
    }

    public Object[][] getCells() {
        return cells;
    }

    public int getMiddleRow() {
        return middleRow;
    }

    public int getMiddleColumn() {
        return middleColumn;
    }

    public int getBaseTopRow() {
        return baseTopRow;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    public void mazeGenerator(){
        List<Cell> toCreate = new ArrayList<>();
        Stack<Cell> visitedCells = new Stack<>();
        Cell current = new Cell(1,1);
        toCreate.add(current);
        visitedCells.push(current);
        while(!visitedCells.isEmpty()){
            current = visitedCells.pop();
            List<Cell> neighbors = unvisitedNeighbours(current,toCreate);
            if(!neighbors.isEmpty()){
                visitedCells.push(current);
                Random random = new Random();
                int numberOfNeighborsToChoose = Math.min(2,neighbors.size());
                for(int i = 0; i < numberOfNeighborsToChoose; i++){
                    int index = random.nextInt(neighbors.size());
                    Cell neighbor = neighbors.get(index);
                    int midRow = (current.row + neighbor.row) / 2;
                    int midCol = (current.column + neighbor.column) / 2;
                    toCreate.add(new Cell(midRow,midCol));
                    toCreate.add(new Cell(neighbor.row,neighbor.column));
                    visitedCells.push(neighbor);
                    neighbors.remove(neighbor);
                }
            }
        }
        create(toCreate);
    }
    public List<Cell> unvisitedNeighbours(Cell cell,List<Cell> visited){
        List<Cell> neighbors = new ArrayList<>();
        if (cell.row - 2 >= 0 && !visited.contains(new Cell(cell.row-2,cell.column))) {
            neighbors.add(new Cell(cell.row - 2, cell.column));
        }
        if (cell.row + 2 < row-1 && !visited.contains(new Cell(cell.row + 2,cell.column))) {
            neighbors.add(new Cell(cell.row + 2, cell.column));
        }
        if (cell.column - 2 >= 0 && !visited.contains(new Cell(cell.row,cell.column-2))) {
            neighbors.add(new Cell(cell.row, cell.column - 2));
        }
        if (cell.column + 2 < column - 1 && !visited.contains(new Cell(cell.row,cell.column+2))) {
            neighbors.add(new Cell(cell.row, cell.column + 2));
        }
        return neighbors;
    }
    public boolean isInBase(int row, int column){
        if(row < baseBottomRow && row > baseTopRow && column < baseRightColumn && column > baseLeftColumn){
            return true;
        }
        return false;
    }
    public void makeBaseCellsClear(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                if(isInBase(i,j)){
                    cells[i][j] = CellType.EMPTY.getValue();
                }
            }
        }
    }
    private boolean isEmptyMore(int row){
        int countWall = 0;
        int countPath = 0;
        for(int i = 1; i < cells[row].length - 1; i++){
            if((int)cells[row][i] == CellType.FOOD.getValue()){
                countPath++;
            } else if((int)cells[row][i] == CellType.WALL.getValue()){
                countWall++;
            }
        }
        return countPath > countWall;
    }
    private void arrangeRows(){
        Random random = new Random();

        for(int i = 1; i < row-1; i++){
            int luck = random.nextInt(3);
            if(i == baseBottomRow || i == baseTopRow || i == baseBottomRow-1 || i ==baseTopRow+1 || i == middleRow)
                continue;
            if(isEmptyMore(i) && luck == 0){
                makeHorizontalWay(i,1,column - 2);
            }
        }
    }
    private void create(List<Cell> toCraete){
        for(Cell cell : toCraete){
            cells[cell.row][cell.column] = CellType.FOOD.getValue();
        }
    }
    public boolean isFoodFinished(){
        for(Object[] row : cells){
            for(Object cell : row){
                if((cell instanceof Integer) && ((int)cell == CellType.FOOD.getValue())){
                    return false;
                }
            }
        }
        return true;
    }
}
class Cell{
    int row;
    int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}