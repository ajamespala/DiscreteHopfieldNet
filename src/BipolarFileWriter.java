/* Filename: BipolarFileWriter.java
 * Description: BipolarFileWriter writes the vectors to a file
 * Names: James Pala and Taylor Wong
 * Date: November 9th, 2018
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class BipolarFileWriter {

	//TODO: save output patterns to file
	// vectorWriter writes the vectors to a specified file
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

						}
						else if(value == 1) {
							output.write('O');

						}
						else {
							output.write('#');

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

	// weightsWriter writes the weight matrix to a specified file
	public static void weightsWriter(String filename, Weights weights){
		BufferedWriter output = null;
		int mRows = weights.mRows;
		int nCols = weights.nCols;
		int length = mRows * nCols;
		try {
			// System.out.print(filename);
			output = new BufferedWriter(new FileWriter(filename));
			output.write(length + " ");
			output.write(mRows + " ");
			output.write(nCols + " ");
			output.write('\n');
		}catch(IOException e){
			System.out.println("File not found");
			//  e.printStackTrace();
			System.exit(1);
		}
		try {
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					int value = weights.matrix[i][j];
					output.write(value + " ");
				}
				output.write("\n");
			}
		}catch(IOException e){
			System.out.println("Error writing to file");
			System.exit(1);
		}
		try {
			output.close();
		}catch (IOException e){
			System.out.println("Error closing file");
		}
	}
}
