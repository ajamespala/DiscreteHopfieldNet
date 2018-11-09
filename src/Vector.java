public class Vector {
    public int [] matrix;
    public int mRows, nCols;

    //TODO: create constructor and class for vectors

    public Vector (int mRows, int nCols, int [] array){
        int length = mRows * nCols;
        this.matrix = new int [length];
        this.mRows = mRows;
        this.nCols = nCols;
        this.matrix = array;
    }



}
