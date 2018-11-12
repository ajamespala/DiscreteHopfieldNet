/* Filename: BipolarFileWriter.java
 * Description: BipolarFileWriter writes the vectors to a file
 * Names: James Pala and Taylor Wong
 * Date: November 12th, 2018
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.*;

public class BipolarFileWriter {

	// vectorWriter writes the vectors to a specified file 
	public static void vectorWriter(String filename, Vector [] vectors) {

		BufferedWriter output = null;
		int numInputVectors = vectors.length;
		int mRows, nCols, length, index;
		try {
			mRows = vectors[0].mRows;
			nCols = vectors[0].nCols;
			length = mRows * nCols;
			output = new BufferedWriter(new FileWriter(filename));
			output.write(length + " \t (dimension of the image vectors)\n");
			output.write(numInputVectors + " \t (number of the image vectors)\n");
			output.write('\n');

			for(int k = 0; k < numInputVectors; k++) {
				index = 0;
				mRows = vectors[k].mRows;
				nCols = vectors[k].nCols;
				length = mRows * nCols;

				for (int i = 0; i < mRows; i++) {
					for (int j = 0; j < nCols; j++) {
						int value = vectors[k].matrix[index];
						index++;
						if(value == -1){
							output.write(' ');
						}
						else if(value == 1) {
							output.write('O');
						}
						else {
							output.write('#');
						}
					}
					output.write('\n');
				}
				if(k != numInputVectors - 1)
					output.write('\n');
			}
		} catch(EOFException e){
			System.out.println("End of File");
		} catch(FileNotFoundException e){
			System.out.println("File Not Found");
		} catch(IOException e) {
			System.out.println("Error reading/writing file");
		} finally{
			if(output != null){
				try{
					output.close();
				} catch(IOException e){
					System.out.println("Error Closing");
					System.exit(1);
				}
			}
		}
	}

	// weightsWriter writes the weight matrix to a specified file
	public static void weightsWriter(String filename, Weights weights){
		BufferedWriter output = null;
		int mRows = weights.mRows;
		int nCols = weights.nCols;
		int length = mRows * nCols;
		try {
			output = new BufferedWriter(new FileWriter(filename));
			output.write(length + " ");
			output.write(mRows + " ");
			output.write(nCols + " ");
			output.write('\n');
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					int value = weights.matrix[i][j];
					output.write(value + " ");
				}
				output.write("\n");
			}
			output.close();
		} catch(EOFException e){
			System.out.println("End of File");
		} catch(FileNotFoundException e){
			System.out.println("File Not Found");
		} catch(IOException e) {
			System.out.println("Error reading/writing file");
		} finally{
			if(output != null){
				try{
					output.close();
				} catch(IOException e){
					System.out.println("Error Closing");
					System.exit(1);
				}
			}
		}
	}
}
