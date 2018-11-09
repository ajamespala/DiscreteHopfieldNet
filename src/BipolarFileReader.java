import java.io.*;
import java.util.Scanner;
public class BipolarFileReader {
    public static Vector[] vectorReader(String filename, int mRows, int nCols, int numInputVectors) {
        //TODO read in input vectors from file, return array of type vector
        //
        FileReader file = null;
        Vector [] vectors = new Vector [numInputVectors];
        try {
           // System.out.print(filename);
            file = new FileReader(filename);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
          //  e.printStackTrace();
            System.exit(1);
        }
        BufferedReader input = new BufferedReader(file);
        try {
            input.readLine();
            input.readLine();
            input.readLine();
        } catch (IOException e) {
            System.out.println("Error reading from file, first read");
            System.exit(1);
        }
       for(int k = 0; k < numInputVectors; k++) {
           int index = 0;
           int length = mRows * nCols;
           int [] array = new int[length];
           for (int i = 0; i < mRows; i++) {
               for (int j = 0; j <= nCols; j++) {
                  try {
                      //System.out.println(index);
                      char c = (char) input.read();
                      System.out.print(c);

                      //c = (char)input.read();
                      if (c == ' ') {
                          array[index] = -1;
                          index++;
                      }
                      else if (c == 'O') {
                          array[index] = 1;
                          index++;
                      }
                      else if(c != '\n') {
                          array[index] = 0;
                          index++;
                      }

                  }catch (IOException e) {
                      System.out.println("Error reading from file");
                      System.exit(1);
                  }
               }
           }
           vectors[k] = new Vector(mRows, nCols, array);

            try {
               input.readLine();
           } catch (IOException e) {
               System.out.println("Error reading from file, last read");
               System.exit(1);
           }

       }
        try {
            input.close();
        }catch (IOException e){
            System.out.println("Error closing file");
        }
       return vectors;
    }

    public static Weights weightReader(String filename) {
        FileReader file = null;
        Weights weights = null;
        int length = 0;
        int mRows = 0, nCols = 0;
        try {
            // System.out.print(filename);
            file = new FileReader(filename);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            //  e.printStackTrace();
            System.exit(1);
        }
        BufferedReader input = new BufferedReader(file);
        Scanner in = null;
        try {
            in = new Scanner(new File(filename));
            length = in.nextInt();
            mRows = in.nextInt();
            nCols = in.nextInt();
          /*  length = input.read();
            input.read();
            mRows = input.read();
            input.read();
            nCols = input.read();
            input.read(); */
        } catch (IOException e) {
            System.out.println("Error reading from file, first read");
            System.exit(1);
        }
        int [][] array = new int[length][length];
        for (int i = 0; i < length; i++) {
                //try {

                  // String line = input.readLine();
                //   String [] token = line.split(" ");
                   //for(String str : token){
                 ///      System.out.println(str);
                 //  }
                  // if(token.length != length)
                      // System.out.print("Length of the read line does not match entered length");
                   for(int j = 0; j < length; j++){
                       //token[j].replace("\n","");
                        //while(in.hasNextInt()) {
                            int tmp = in.nextInt();
                            System.out.print(tmp);
                            array[i][j] = tmp;
                      //  }
                   }
              //  }catch (IOException e) {
              //      System.out.println("Error reading from file");
               //     System.exit(1);
                //}
        }
        weights = new Weights(array, length, mRows, nCols);

        try {
            input.readLine();
        } catch (IOException e) {
            System.out.println("Error reading from file, last read");
            System.exit(1);
        }
        try {
            input.close();
        }catch (IOException e){
            System.out.println("Error closing file");
        }

        return weights;
    }


  //  public static Weights
    //TODO: read weights from file (needs to also save the dimensions (mRows, nCols) of the training date

}
