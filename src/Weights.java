public class Weights {
    int mRows, nCols;
    int length;
    int matrix[][];

    public Weights(int [][]array, int length, int mRows, int nCols){
        matrix = new int[length][length];
        this.matrix = array;
        this.length = length;
        this.mRows = mRows;
        this.nCols = nCols;
    }
    //TODO: create constructor and class for weights matrix

    //TODO: zero diagonal function

    //TODO: create and addition function to add different objects of type weights together


}
