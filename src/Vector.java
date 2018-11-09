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

    public void printVector(){
        System.out.println("Length: " + mRows*nCols);
        System.out.println("mRows: " + mRows);
        System.out.println("nCols: " + nCols);

        int index = 0;
        for(int i = 0; i < mRows; i++){
            for(int j = 0; j < mRows; j++){
                int c = this.matrix[index];
                if(c == 1)
                    System.out.print("O");
                else if(c == -1)
                    System.out.print(" ");
                else
                    System.out.print("#");
                index++;
            }
            System.out.println();
        }
    }

}
