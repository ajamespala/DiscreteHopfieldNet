/* Filename: HopfieldNet.java
 * Description: HopfieldNet simulates a Hopfield Auto-associative neural network to store and recall a set of bitmap images.
 * Names: James Pala and Taylor Wong
 * Date: November 12th, 2018
 */

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;
import java.util.Arrays;

public class HopfieldNet {
	public static void main(String[]args){
		Scanner kb = new Scanner(System.in);
		System.out.println("\nWelcome to the Hopfield Neural Network");
		Weights W = null;
		String trainingFile, testFile, weightFile, resultsFile;
		trainingFile = testFile = weightFile = resultsFile = "";
		Vector[] outputVectors = null;

		while(true){
			int mode = handleInput();
			if(mode == 1){
				System.out.println("Enter a filename to save the weight matrix: ");
				weightFile = kb.next();
				W = trainingMode(weightFile);
			} else{
				System.out.println("Enter testing file name: ");
				testFile = kb.next();
				System.out.println("Enter the weight file name you wish to use: ");
				weightFile = kb.next();
				outputVectors = testingMode(weightFile, testFile);
			}

			// ask user if they want to go directly into testing mode 
			System.out.println("Enter a 1 to enter testing mode.  Enter 2 to quit.");
			int test = kb.nextInt();
			if(test == 1){
				System.out.println("Enter testing file name: ");
				testFile = kb.next();
				outputVectors = testingMode(weightFile, testFile);
			}
			String again = " "; 
			if(test != 2){
				System.out.println("Would you like to run again? Y/N");
				again = kb.next();
			}
			if(again.toLowerCase().contains("y"))
				continue;
			else
				break;
		}
	}

	// handles the user input and checks for invalid inputs
	public static int handleInput(){
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter 1 to enter the training mode, enter 2 to enter the testing mode.");
		int mode = kb.nextInt();
		while(mode != 1 && mode != 2){
			System.out.println("Invalid. Please enter again.");
			mode = kb.nextInt();
		}
		return mode;
	}

	// handles training mode
	public static Weights trainingMode(String weightFile){
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter training file name: ");
		String filename = kb.next();
		System.out.println("Enter mRows: ");
		int mRows = kb.nextInt();
		System.out.println("Enter nCols: ");
		int nCols = kb.nextInt();
		System.out.println("Enter number of input vectors: ");
		int numInputVectors = kb.nextInt();

		Vector[] vectors = BipolarFileReader.vectorReader(filename, mRows, nCols, numInputVectors);
		Weights[] weights = new Weights[numInputVectors];
		Weights W = initWeightMatrix(numInputVectors, weights, vectors);
		BipolarFileWriter.weightsWriter(weightFile, W);

		return W;
	}

	// method that generates a weight matrix = sum of weight matrices from S^T*t
	public static Weights initWeightMatrix(int numVectors, Weights[] weights, Vector[] vectors){
		for(int i = 0; i < numVectors; i++) {
			weights[i] = new Weights(vectors[i]);
			weights[i].zeroDiagonal();
		}
		Weights W = Weights.addWeights(weights);
		return W;
	}

	// handles testing mode
	public static Vector[] testingMode(String weightFile, String testFile){
		Scanner kb = new Scanner(System.in);
		Weights W = BipolarFileReader.weightReader(weightFile);
		int mRows = W.mRows;
		int nCols = W.nCols;

		System.out.println("Enter output filename to save output vectors (see later prompt for more): ");
		String outputFile = kb.next();
		System.out.println("Enter number of input vectors: ");
		int numInputVectors = kb.nextInt();
		System.out.println("Enter a value for the threshold theta (usually 0): ");
		double theta = kb.nextDouble();

		Vector [] outputVectors = test(W, testFile, mRows, nCols, numInputVectors, theta);
		Vector [] testVectors = BipolarFileReader.vectorReader(testFile, W.mRows, W.nCols, numInputVectors);

		BipolarFileWriter.vectorWriter(outputFile, outputVectors);
		// save results to a file - vectors, num correct, etc.	
		writeResultsFile(testFile, outputVectors);			
		return outputVectors;
	}

	// test simulates the Hopfield Neural Net
	public static Vector[] test(Weights W, String filename, int testmRows, int testnCols, int testNumInputVectors, double theta){
		Vector [] outputVectors = new Vector[testNumInputVectors];
		int length = testmRows * testmRows;
		Vector [] testVectors = BipolarFileReader.vectorReader(filename,testmRows,testnCols,testNumInputVectors);
		boolean continueCondition = true;
		
		while(continueCondition) {
			int index = 0;

			// go through each test vector and adjust weights as needed
			for(Vector v : testVectors) {
				Vector testVector = new Vector(v.mRows,v.nCols,v.matrix);  // y vector 
				int [] indexSeen = new int [length];
				int epochCount = 0;
				int [] testArray = new int[length];

				// simulates the Hopfield Neural Net implementing the Hebbian Learning Rule
				boolean updated = false;
				for(int j = 0; j < length; j++){
					testArray[j] = testVector.matrix[j];
				}

				// randomly choose x_i from the testVector to calculate y_in
				for (int i = 0; i < length; i++) {
					double rand = Math.random() * 100;
					int random = (int)Math.floor(rand);
					while (Arrays.asList(indexSeen).contains(random)) {
						rand = Math.random() * 100;
						random = (int)Math.floor(rand);
					}
					int beginInt = testVector.matrix[random];
					int y_in = testVector.matrix[random];

					// calculate y_in for i = 1, 2,..., n
					for (int j = 0; j < length; j++) {
						y_in += testVector.matrix[j] * W.matrix[j][random];
					}

					// calculate y = f(y_in) applying the activation function
					int y = activationFunc(y_in, theta);

					// if there are changes on activation function, loop again and broadcast
					if(y != 0)
						testVector.matrix[random] = y;
					if(y != beginInt)
						updated = true;
					if(updated)
						continueCondition = true;
					else
						continueCondition = false;
					epochCount++;
				}
				outputVectors[index] = testVector;
				index++;
			}
		}
		return outputVectors;
	}

	// activationFunc calculates the value of y as a result of the activiation function with input y_in
	public static int activationFunc(int y_in, double theta){  
		int y = 0;
		if(y_in > theta)
			y = 1;
		else if(y_in < theta)
			y = -1;
		else
			y = 0;
		return y;
	}

	// writeResultsFile saves the result to a file - training --> test vectors --> output vectors	
	public static void writeResultsFile(String testFile, Vector[] outputVectors){
		BufferedWriter output = null;
		int mRows = outputVectors[0].mRows;
		int nCols = outputVectors[0].nCols;
		int length = outputVectors[0].length;	
		int numVectors = outputVectors.length;
		Vector[] testVectors = BipolarFileReader.vectorReader(testFile, mRows, nCols, numVectors);
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter a filename to save the results to (just for you, Dr. Jiang: mapping from test vectors to output vectors): ");
		String resultsFile = kb.next();

		try{
			output = new BufferedWriter(new FileWriter(resultsFile));
	
			// Write Header
			output.write(length + " \t (dimension of the image vectors)\n");
			output.write(numVectors + "\t (number of the image vectors)\n");
			output.write("\n");
			output.write("Test Vector Patterns \t --> \t Output Vector Patterns\n");
			output.write("---------------------\t     \t -----------------------\n");
	
			// Write Test Vectors and Output Vectors
			for(int k = 0; k < numVectors; k++) {
				int index1 = 0, index2 = 0;
				for (int i = 0; i < mRows; i++) {
					output.write("\t");
					// write a row from testVector matrix
					for (int j = 0; j < nCols; j++) {
						try {
							int value = testVectors[k].matrix[index1];
							index1++;
							if(value == -1)
								output.write(' ');
							else if(value == 1) 
								output.write('O');
							else
								output.write('#');
						}catch (IOException e) {
							System.out.println("Error writing to file");
							System.exit(1);
						}
					}

					// put space in between two vector patterns
					if(i == (mRows/2))
						output.write("\t --> \t");
					else
						output.write("\t     \t");

					// write a row from the outputVector matrix adjacent to testVector
					for (int j = 0; j < nCols; j++) {
						try {
							int value = outputVectors[k].matrix[index2];
							index2++;
							if(value == -1)
								output.write(' ');
							else if(value == 1) 
								output.write('O');
							else
								output.write('#');
						}catch (IOException e) {
							System.out.println("Error writing to file");
							System.exit(1);
						}
					}
					// newline to go to the next row
					try {
						output.write('\n');
					}catch (IOException e) {
						System.out.println("Error printing new line char");
					}
				}
				try {
					if(k != numVectors - 1)
						output.write('\n');
				}catch (IOException e) {
					System.out.println("Error printing new line char");
				}
			}
			try{
				output.close();
			}catch(IOException e){
				System.out.println("Error closing file");
			}
		}catch(IOException e){
			System.out.println("File not found");
		}
	}
}

