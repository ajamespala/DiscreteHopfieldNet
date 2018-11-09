import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class BipolarFileWriter {

    //TODO: save output patterns to file
    public static void vectorWriter(String filename, Vector [] vectors) {

        BufferedWriter output = null;
        int numInputVectors = vectors.length;


        try {
            // System.out.print(filename);
            int mRows = vectors[0].mRows;
            int nCols = vectors[0].nCols;
            int length = mRows * nCols;
            output = new BufferedWriter(new FileWriter(filename));
            output.write(length + " \t (dimension of the image vectors)\n");
            output.write(numInputVectors + " \t (number of the image vectors)\n");
            output.write('\n');
        }catch(IOException e){
            System.out.println("File not found");
            //  e.printStackTrace();
            System.exit(1);
        }

        for(int k = 0; k < numInputVectors; k++) {
            int index = 0;
            int mRows = vectors[k].mRows;
            int nCols = vectors[k].nCols;
            int length = mRows * nCols;


            for (int i = 0; i < mRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    try {
                        int value = vectors[k].matrix[index];
                        index++;
                        if(value == -1){
                            output.write(' ');
                            System.out.println("Writing for -1");
                        }
                        else if(value == 1) {
                            output.write('O');
                            System.out.println("Writing for 1");
                        }
                        else {
                            output.write('#');
                            System.out.println("Writing for #");
                        }

                    }catch (IOException e) {
                        System.out.println("Error writing to file");
                        System.exit(1);
                    }
                }
                try {
                    output.write('\n');
                }catch (IOException e) {
                    System.out.println("Error printing new line char");
                }
            }
            try {
                if(k != numInputVectors - 1)
                    output.write('\n');
            }catch (IOException e) {
                System.out.println("Error printing new line char");
            }
        }
        try {
            output.close();
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
    //TODO: write weights to file
}
