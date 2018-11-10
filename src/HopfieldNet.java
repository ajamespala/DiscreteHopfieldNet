import java.util.*;
import java.util.stream.IntStream;
import java.lang.*;
public class HopfieldNet {
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


        Vector [] outputVectors = test(W, testfilename, testmRows, testnCols, testNumInputVectors);
        BipolarFileWriter.vectorWriter("/Users/jamespala/NeuralNetworks/DiscreteHopfieldNet/outputVectors/outputFile1.txt", outputVectors);




        //W.printWeights();

        //System.out.println("Num input vectors in vectors array: " + vectors.length);
        //for(int i = 0; i<numInputVectors; i++){
          //  for(int j = 0; j<vectors[0].matrix.length; j++) {
              //System.out.print(vectors[0].matrix.length);
       //     }
        //}

    }

    public static Vector[] test(Weights W, String testfilename, int testmRows, int testnCols, int testNumInputVectors){
        Vector [] outputVectors = new Vector[testNumInputVectors];

        int length = testmRows * testmRows;

        Vector [] testVectors = BipolarFileReader.vectorReader(testfilename,testmRows,testnCols,testNumInputVectors);
        int index = 0;
        for(Vector v : testVectors) {
            Vector testVector = new Vector(v.mRows,v.nCols,v.matrix);
            int [] indexSeen = new int [length];
            boolean updated = true;
            while(updated) {
                updated = false;
                for (int i = 0; i < length; i++) {
                    int random = (int) Math.random() * 100;
                    while (Arrays.asList(indexSeen).contains(random)) {
                        random = (int) Math.random() * 100;
                    }
                    System.out.print(random + " ");

                    int y_in = testVector.matrix[random];

                    for (int j = 0; j < length; j++) {
                        y_in += testVector.matrix[j] * W.matrix[j][random];
                    }

                    int y = activationFunc(y_in);
                    testVector.matrix[random] = y;
                  //  System.out.println("Y_IN:" + y_in);
                   // System.out.println("New y:" + y);
                    if(y != testVector.matrix[random])
                        updated = true;
                }
               //TODO: check for flipflop, ie has it seen this output vector before?
            }
            outputVectors[index] = testVector;
            index++;
            System.out.println("Epoch " + index);
        }
        return outputVectors;
    }

    //TODO: make theta user-set (right now theta is hardcoded to 0
    public static int activationFunc(int y_in){
        int y = 0;

        if(y_in > 0)
            y = 1;
        else if(y_in < 0)
            y = -1;
        else
            y = y_in;

        return y;
    }
    //TODO: create functions that implement the testing algorithm
        //TODO: need to verify testing vectors are same dimensions as training vectors
        //ie weights file will match testing vectors (which also matches training


}



/*


public Hopfield {

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
        System.out.println();
        System.out.println();

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

        }

// handles testing mode
public static boolean testingMode(){

        }

// method that generates a weight matrix = sum of weight matrices from S^T*t
public static initWeightMatrix(){

        }

// for training mode - method reads image file
public static boolean readImageFile(String fileName){
        BufferedReader input = null;
        String thisLine = null;

        try{
        input = new BufferedReader(new FileReader(filename));
        String dimension = input.nextLine();
        String numVectors = input.nextLine();

        int count = 0;
        int nRows = 0, mCols;
        int[][] images;

        while((thisLine = input.readLine()) != null && count < numVectors){
        StringTokenizer st = new StringTokenizer(thisLine);
        mCols = st.countTokens();
        nRows = (int) dimension / (int) mCols;
        images = new int[nRows][mCols];
        Sample newSample;

        // read in matrix into a 2D array
        for(int i = 0; i < nRows; i++){
        st = new StringTokenizer(thisLine);
        for(int j = 0; j < mCols; j++){
        if(st.hasMoreTokens()){
        images[i][j] = Integer.parseInt(st.nextToken(" "));
        }
        }
        thisLine = input.readLine();
        if(thisLine == null)
        break;
        }

        }
        }catch(EOFException e){
        System.out.println("End of File");
        return false;
        }
        catch(FileNotFoundException e){
        System.out.println("File Not Found");
        return false;
        }
        catch(IOException e) {
        System.out.println("Error reading/writing file");
        return false;
        }
        finally{
        if(input != null){
        try{
        input.close();
        }
        catch(IOException e){
        System.out.println("Error Closing");
        System.exit(1);
        }
        }
        }
        return true;
        }

// for training mode
public static boolean writeWeightMatrix(String fileName){
        BufferedWriter output = null;
        try{
        output = new BufferedWriter(new FileWriter(filename));

        // TODO: write weight matrix to file
        }
        catch(EOFException e){
        System.out.println("End of File");
        }
        catch(FileNotFoundException e){
        System.out.println("File Not Found");
        return false;
        }
        catch(IOException e) {
        System.out.println("Error reading/writing file");
        return false;
        }
        finally{
        if(output != null){
        try{
        output.flush();
        output.close();
        }
        catch(IOException e){
        System.out.println("Error Closing");
        System.exit(1);
        }
        }
        return true;
        }
        }

// for testing mode
public static boolean readWeightsFile(String fileName){
        BufferedReader input = null;
        try{
        input = new BufferedReader(new FileReader(filename));

        // read weights from previously saved weight file


        }catch(EOFException e){
        System.out.println("End of File");
        return false;
        }
        catch(FileNotFoundException e){
        System.out.println("File Not Found");
        return false;
        }
        catch(IOException e) {
        System.out.println("Error reading/writing file");
        return false;
        }
        finally{
        if(input != null){
        try{
        input.close();
        }
        catch(IOException e){
        System.out.println("Error Closing");
        System.exit(1);
        }
        }
        }
        return true;
        }

// for testing mode
public static boolean writeResultsFile(String fileName){
        BufferedWriter output = null;
        try{
        output = new BufferedWriter(new FileWriter(filename));

        // TODO: write response of net and results to file
        }
        catch(EOFException e){
        System.out.println("End of File");
        }
        catch(FileNotFoundException e){
        System.out.println("File Not Found");
        return false;
        }
        catch(IOException e) {
        System.out.println("Error reading/writing file");
        return false;
        }
        finally{
        if(output != null){
        try{
        output.flush();
        output.close();
        }
        catch(IOException e){
        System.out.println("Error Closing");
        System.exit(1);
        }
        }
        return true;
        }
        }
        }
*/

