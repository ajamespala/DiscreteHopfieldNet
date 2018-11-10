/* Filename: HopfieldNet.java
 * Description: HopfieldNet simulates a Hopfield Auto-associative neural network to store and recall a set of bitmap images.
 * Names: James Pala and Taylor Wong
 * Date: November 9th, 2018
 */

import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;
import java.util.Arrays;

public class HopfieldNet {
	public static void main(String[]args){
		Scanner kb = new Scanner(System.in);
		System.out.println("\n Welcome to the Hopfield Neural Network");
		while(true){
			int mode = handleInput();
			if(mode == 1){
				trainingMode();
			} else{
				testingMode();
			}
			System.out.println("Enter a 1 to enter testing mode.  Enter 2 to quit.");
			int test = kb.nextInt();
			if(test == 1)
				break;
			else
				testingMode();

			System.out.println("Would you like to run again? Y/N");
			String again = kb.nextLine();
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
	public static boolean trainingMode(){
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter training file name: ");
		String filename = kb.next();

		System.out.println("Enter mRows: ");
		int mRows = kb.nextInt();
		System.out.println("Enter nCols: ");
		int nCols = kb.nextInt();
		System.out.println("Enter number of input vectors: ");
		int numInputVectors = kb.nextInt();
		System.out.println("Enter a filename to save the vectors: ");
		String vectorFilename = kb.next();

		Vector[] vectors = BipolarFileReader.vectorReader(filename, mRows, nCols, numInputVectors);
		BipolarFileWriter.vectorWriter(vectorFilename, vectors);
		Vector[] newVectors = BipolarFileReader.vectorReader(vectorFilename, mRows, nCols, numInputVectors);
		Weights[] weights = new Weights[numInputVectors];

		Weights W = initWeightMatrix(numInputVectors, weights, newVectors);
		System.out.println("Enter a filename to save the weight matrix: ");
		String weightFilename = kb.next();
		BipolarFileWriter.weightsWriter(weightFilename, W);
		return true;
	}

	// method that generates a weight matrix = sum of weight matrices from S^T*t
	public static Weights initWeightMatrix(int numVectors, Weights[] weights, Vector[] newVectors){
		for(int i = 0; i < numVectors; i++) {
			weights[i] = new Weights(vectors[i]);
			weights[i].zeroDiagonal();
		}
		Weights W = Weights.addWeights(weights);
		return W;
	}

	// handles testing mode
	public static boolean testingMode(){
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter testing file name: ");
		String filename = kb.next();

		System.out.println("Enter mRows: ");
		int mRows = kb.nextInt();
		System.out.println("Enter nCols: ");
		int nCols = kb.nextInt();
		System.out.println("Enter number of input vectors: ");
		int numInputVectors = kb.nextInt();
		Vector [] outputVectors = test(W, filename, mRows, nCols, numInputVectors);
		BipolarFileWriter.vectorWriter("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/outputVectors/outputFile1.txt", outputVectors);

		return true;
	}

	// test simulates the Hopfield Neural Net
	public static Vector[] test(Weights W, String testfilename, int testmRows, int testnCols, int testNumInputVectors){
		Vector [] outputVectors = new Vector[testNumInputVectors];
		int length = testmRows * testmRows;

		Vector [] testVectors = BipolarFileReader.vectorReader(testfilename,testmRows,testnCols,testNumInputVectors);
		int index = 0;
		for(Vector v : testVectors) {
			Vector testVector = new Vector(v.mRows,v.nCols,v.matrix);
			int [] indexSeen = new int [length];
			boolean continueCondition = true;
			int epochCount = 0;
			int [] testArray = new int[length];
			while(continueCondition) {
				boolean updated = false;
				for(int j = 0; j < length; j++){
					testArray[j] = testVector.matrix[j];
				}

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

					int y = activationFunc(y_in);
					if(y != 0)
						testVector.matrix[random] = y;
					if(y != beginInt)
						updated = true;
				}
				if(updated)
					continueCondition = true;
				else
					continueCondition = false;
				//if(epochCount > 100)
				//     continueCondition = false;
				//TODO: check for flipflop, ie has it seen this output vector before?
				System.out.println("<-Epoch " + epochCount + " for test vector #" + index);
				epochCount++;

				if(Arrays.equals(testVector.matrix, testArray))
					System.out.println("Same array");
				else
					System.out.println("Different array");
			}
			outputVectors[index] = testVector;
			index++;
		}
		return outputVectors;
	}

	//TODO: make theta user-set (right now theta is hardcoded to 0
	// activationFunc calculates the value of y as a result of the activiation function with input y_in
	public static int activationFunc(int y_in){
		int y = 0;

		if(y_in > 0)
			y = 1;
		else if(y_in < 0)
			y = -1;
		else
			y = 0;

		return y;
	}
	//TODO: create functions that implement the testing algorithm
	//TODO: need to verify testing vectors are same dimensions as training vectors
	//ie weights file will match testing vectors (which also matches training
}

// for training mode - simulates the Hopfield Neural Net implementing the Hebbian Learning Rule
/*
   public static void main(String [] args) {
   System.out.println("Welcome to a Discrete Neural Network System");
//TODO: implement UI and call higher level function
Scanner kb = new Scanner(System.in);

System.out.println("Enter training file name: ");
String filename = kb.next();

//TODO: may have to read mRows and nCols from input vectors, not user input, although I am not sure if that is important
System.out.println("Enter mRows: ");
int mRows = kb.nextInt();
System.out.println("Enter nCols: ");
int nCols = kb.nextInt();
System.out.println("Enter number of input vectors: ");
int numInputVectors = kb.nextInt();

Vector [] vectors = BipolarFileReader.vectorReader(filename, mRows, nCols, numInputVectors);
BipolarFileWriter.vectorWriter("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/testingVectors/outputFile1.txt", vectors);
Vector [] newVectors = BipolarFileReader.vectorReader("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/testingVectors/outputFile1.txt",mRows,nCols,numInputVectors);
Weights [] weights = new Weights[numInputVectors];
for(int i = 0; i < numInputVectors; i++) {
weights[i] = new Weights(vectors[i]);
System.out.println("Initialized weights for #" + i);
//weights[i].printWeights();
weights[i].zeroDiagonal();
System.out.println("Zeroed diagonal for #" + i);
// weights[i].printWeights();
}
Weights W = Weights.addWeights(weights);
BipolarFileWriter.weightsWriter("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/testingVectors/weightsFile1.txt",W);
for(Vector v : newVectors){
v.printVector();
}

Weights newW = BipolarFileReader.weightReader("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/testingVectors/weightsFile1.txt");
newW.printWeights();

//TODO move to own function test
System.out.println("Enter testing file name: ");
String testfilename = kb.next();

//TODO: may have to read mRows and nCols from input vectors, not user input, although I am not sure if that is important
System.out.println("Enter mRows: ");
int testmRows = kb.nextInt();
System.out.println("Enter nCols: ");
int testnCols = kb.nextInt();
//TODO: check that these dimensions are the same as the training dimesions (can use mRows and nCols values in weights object)
System.out.println("Enter number of input vectors: ");
int testNumInputVectors = kb.nextInt();

Vector [] outputVectors = testingMode(W, testfilename, testmRows, testnCols, testNumInputVectors);
BipolarFileWriter.vectorWriter("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/outputVectors/outputFile1.txt", outputVectors);

//W.printWeights();
//System.out.println("Num input vectors in vectors array: " + vectors.length);
//for(int i = 0; i<numInputVectors; i++){
//  for(int j = 0; j<vectors[0].matrix.length; j++) {
//System.out.print(vectors[0].matrix.length);
//     }
//}
}
 */
