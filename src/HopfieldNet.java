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
		Weights W = null;
		
		while(true){
			int mode = handleInput();
			if(mode == 1){
				W = trainingMode();
			} else{
				System.out.println("Enter the weight file name you wish to use: ");
				String weightFile = kb.next();
				W = BipolarFileReader.weightReader(weightFile);
				testingMode(W);
			}
			
			// ask user if they want to go directly into testing mode 
			System.out.println("Enter a 1 to enter testing mode.  Enter 2 to quit.");
			int test = kb.nextInt();
			if(test == 1)
				testingMode(W);
			else
				break;
			
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
	public static Weights trainingMode(){
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
	public static boolean testingMode(Weights W){
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter testing file name: ");
		String filename = kb.next();

		System.out.println("Enter mRows: ");
		int mRows = kb.nextInt();
		System.out.println("Enter nCols: ");
		int nCols = kb.nextInt();
		System.out.println("Enter number of input vectors: ");
		int numInputVectors = kb.nextInt();
		System.out.println("Enter output file name: ");
		String outputFile = kb.next();
		
		Vector [] outputVectors = test(W, filename, mRows, nCols, numInputVectors);
		BipolarFileWriter.vectorWriter(outputFile, outputVectors);

		// TODO: call numCorrect function
		//int correctVectors = numCorrect(/*inputVectors goes here but how do we get it...*/,outputVectors);
		//System.out.println("Number of vectors correctly identified: " + correctVectors);
		
		return true;
	}

	// test simulates the Hopfield Neural Net
	public static Vector[] test(Weights W, String filename, int testmRows, int testnCols, int testNumInputVectors){
		Vector [] outputVectors = new Vector[testNumInputVectors];
		int length = testmRows * testmRows;
		Vector [] testVectors = BipolarFileReader.vectorReader(filename,testmRows,testnCols,testNumInputVectors);
		int index = 0;
		
		// go through each test vector and adjust weights as needed
		for(Vector v : testVectors) {
			Vector testVector = new Vector(v.mRows,v.nCols,v.matrix);  // y vector 
			int [] indexSeen = new int [length];
			boolean continueCondition = true;
			int epochCount = 0;
			int [] testArray = new int[length];
			
			// simulates the Hopfield Neural Net implementing the Hebbian Learning Rule
			while(continueCondition) {
				boolean updated = false;
				for(int j = 0; j < length; j++){
					testArray[j] = testVector.matrix[j];
				}
				
				// TODO: could put in another function that computes y_in for i to n, applies activiation function and broadcasts y
				// -----------------------------------------------------------------------------------------------------------------
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

					int y = activationFunc(y_in);
					if(y != 0)
						testVector.matrix[random] = y;
					if(y != beginInt)
						updated = true;
				}
				// -----------------------------------------------------------------------------------------------------------------
				
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
	
	//ie weights file will match testing vectors (which also matches training
	
	// verifyVectors checks if the input and output vectors are the same, else return false
	public static boolean verifyVectors(Vector input, Vector output){
		// check for equal dimensions
		if(!checkDimensions(input, output))
			return false;
		
		// check vector matrix 
		for(int i = 0; i < input.length; i++){
			if(input.matrix[i] != output.matrix[i])
				return false;
		}
		
		return true;
	}
	
	//TODO: need to verify testing vectors are same dimensions as training vectors
	public static boolean checkDimensions(Vector input, Vector output){
		if((input.mRows != output.mRows) || (input.nCols != input.nCols))
			return false;
		if(input.length != output.length)  // TODO: James check - may be unnecessary
			return false;
		return true;
	}
	
	// numCorrect counts the number of correct vectors after training and testing
	public static int numCorrect(Vector[] inputVectors, Vector[] outputVectors){
		int count = 0;
		for(int i = 0; i < inputVectors.length; i++){
			if(!checkDimensions(input, output)){
				if(verifyVector(inputVectors[i], outputVectors[i]))
					count++;
			}
		}
		return count;
	}
}


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
