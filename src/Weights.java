/* FIlename: Weights.java
 * Description: Weights class consists of functions that support the creation of the weight matrix 
 * and stores it into a 2D array. 
 * Names: James Pala and Taylor Wong
 * Date: November 9th, 2018
 */

public class Weights {
	int mRows, nCols;
	int length;
	int matrix[][];

	// constructor
	public Weights(int [][] array, int length, int mRows, int nCols){
		matrix = new int[length][length];
		this.matrix = array;
		this.length = length;
		this.mRows = mRows;
		this.nCols = nCols;
	}

	// constructor
	public Weights(int [] array, int length, int mRows, int nCols){
		matrix = new int[length][length];
		int [][] newArray = getWeightsFromVector(array);
		this.matrix = newArray;
		this.length = length;
		this.mRows = mRows;
		this.nCols = nCols;
	}

	// constructor
	public Weights(Vector vector){
		int length = vector.mRows * vector.nCols;
		matrix = new int[length][length];
		int [][] newArray = getWeightsFromVector(vector.matrix);
		this.matrix = newArray;
		this.length = length;
		this.mRows = vector.mRows;
		this.nCols = vector.nCols;
	}

	// outer product of array passed in, returns autoassociative nonzeroed weight matrix
	public int[][] getWeightsFromVector(int [] array){
		int [][] returnArray = new int[array.length][array.length];
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array.length; j++) {
				returnArray[i][j] = array[i] * array[j];
			}
		}
		return returnArray;
	}

	// zeroDiagonal implements the no self connection feature of an auto associative net
	public void zeroDiagonal(){

		int [][] returnArray = new int[this.length][this.length];
		for(int i = 0; i < this.length; i++) {
			this.matrix[i][i] = 0;
		}
	}

	// printWeights prints the weight matrix
	public void printWeights() {
		for(int i = 0; i < this.matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				System.out.print(this.matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	// addWeights is the sumation of all the weight matrices calculated by getWeightsFromVector
	public static Weights addWeights(Weights [] w){
		Weights returnWeights = null;

		returnWeights = w[0];
		for(int i = 1; i < w.length; i++) {
			int [][] matrix = w[i].matrix;
			for(int m = 0; m < matrix.length;m++) {
				for(int n = 0; n < matrix.length;n++) {
					int tmp = returnWeights.matrix[m][n];
					returnWeights.matrix[m][n] = tmp + matrix[m][n];
				}
			}
		}
		return returnWeights;
	}
}
