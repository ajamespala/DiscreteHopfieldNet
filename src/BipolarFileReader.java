/* Filename: BipolarFileReader.java
 * Description: BipolarFileReader reads the vector file that stores the images.
 * Names: James Pala and Taylor Wong
 * Date: November 11th, 2018
 */

import java.io.*;
import java.util.Scanner;

public class BipolarFileReader {
	public static Vector[] vectorReader(String filename, int mRows, int nCols, int numInputVectors) {
		FileReader file = null;
		Vector [] vectors = new Vector [numInputVectors];
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(filename));
			input.readLine();
			input.readLine();
			input.readLine();
			for(int k = 0; k < numInputVectors; k++) {
				int index = 0;
				int length = mRows * nCols;
				int [] array = new int[length];
				for (int i = 0; i < mRows; i++) {
					for (int j = 0; j <= nCols; j++) {
						char c = (char) input.read();
						if (c == ' ') {
							array[index] = -1;
							index++;
						}
						else if (c == 'O') {
							array[index] = 1;
							index++;
						}
						else if(c == '#') {
							array[index] = 0;
							index++;
						}
					}
				}
				vectors[k] = new Vector(mRows, nCols, array);
				input.readLine();
			}
		}catch(EOFException e){
			System.out.println("End of File");
		}catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}catch(IOException e) {
			System.out.println("Error reading/writing file");
		}finally{
			if(input != null){
				try{
					input.close(); 
				} catch(IOException e){
					System.out.println("Error Closing");
					System.exit(1);
				}
			}
		}
		return vectors;
	}

	// weightReader reads the weight matrix from a specified file
	public static Weights weightReader(String filename) {
		FileReader file = null;
		Weights weights = null;
		int length = 0;
		int mRows = 0, nCols = 0;
		BufferedReader input = null;
		Scanner in = null;
		try {
			input = new BufferedReader(new FileReader(filename));
			in = new Scanner(new File(filename));
			length = in.nextInt();
			mRows = in.nextInt();
			nCols = in.nextInt();
			int [][] array = new int[length][length];
			for (int i = 0; i < length; i++) {
				for(int j = 0; j < length; j++){
					int tmp = in.nextInt();
					array[i][j] = tmp;
				}
			}
			weights = new Weights(array, length, mRows, nCols);
			input.readLine();
		} catch(EOFException e){
			System.out.println("End of File");
		} catch(FileNotFoundException e){
			System.out.println("File Not Found");
		} catch(IOException e) {
			System.out.println("Error reading/writing file");
		} finally{
			if(input != null){
				try{
					input.close(); 
				} catch(IOException e){
					System.out.println("Error Closing");
					System.exit(1);
				}
			}
		}
		return weights;
	}
}
