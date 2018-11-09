import java.util.*;
public class HopfieldNet {
    public static void main(String [] args) {
        System.out.println("Welcome to a Discrete Neural Network System");
    //TODO: implement UI and call higher level function
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter training file name: ");
        String filename = kb.next();
        System.out.println("Enter mRows: ");
        int mRows = kb.nextInt();
        System.out.println("Enter nCols: ");
        int nCols = kb.nextInt();
        System.out.println("Enter number of input vectors: ");
        int numInputVectors = kb.nextInt();

        Vector [] vectors = BipolarFileReader.vectorReader(filename, mRows, nCols, numInputVectors);

        //System.out.println("Num input vectors in vectors array: " + vectors.length);
        //for(int i = 0; i<numInputVectors; i++){
          //  for(int j = 0; j<vectors[0].matrix.length; j++) {
         //       System.out.print(vectors[0].matrix[j]);
       //     }
        //}

    }

    //TODO: create functions that implement the testing algorithm
        //TODO: need to verify testing vectors are same dimensions as training vectors


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

