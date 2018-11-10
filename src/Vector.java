/* Filename: Vector.java
 * Description: Vector class saves the input and output vectors 
 * into a Vector object allowing easy printing and access to the data.
 * Names: James Pala and Taylor Wong
 * Date: November 9th, 2018
 */

public class Vector {
	public int [] matrix;
	public int mRows, nCols;
	public int length;
	
	//TODO: create constructor and class for vectors

	public Vector (int mRows, int nCols, int [] array){
		this.length = mRows * nCols;
		this.matrix = new int [length];
		this.mRows = mRows;
		this.nCols = nCols;
		this.matrix = array;
	}

	public void printVector(){
		System.out.println("Length: " + length);
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
