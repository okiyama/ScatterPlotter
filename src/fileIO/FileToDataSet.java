package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.DataSet;
import model.InvalidRangeException;

/**
 * Takes in a file and converts it into a DataSet object.
 * @author Julian Jocque
 */
public class FileToDataSet 
{
	private static final String FOLDER_TO_USE = "data";
	private Scanner reader;
	private File fileIn;
	private DataSet output;
	
	/**
	 * Constructs using the given fileName
	 * @param fileName File that we are reading
	 * @throws FileNotFoundException If we get a non-existent path
	 */
	public FileToDataSet(String fileName) throws FileNotFoundException
	{
		String filePath = FOLDER_TO_USE + "/" + fileName + ".jjf";
		fileIn = new File(filePath);
		reader = new Scanner(fileIn);
	}
	
	/**
	 * Processes the file into a DataSet then returns it
	 * @return The DataSet we construct form the file
	 * @throws InvalidRangeException If the file contains invalid range parameters
	 */
	public DataSet parse() throws InvalidRangeException
	{
		parseVariables();
		parseData();
		
		return output;
	}

	/**
	 * Parses the variables from the file and makes a new DataSet with them
	 * @throws InvalidRangeException If file contains an invalid range
	 */
	private void parseVariables() throws InvalidRangeException 
	{
	
		String title;
		String xAxisLabel;
		String yAxisLabel;
		Double xMin;
		Double xMax;
		Double yMin;
		Double yMax;
		
		String currentToken = reader.nextLine();
		title = currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length());
		currentToken = reader.nextLine();
		xAxisLabel = currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length());
		currentToken = reader.nextLine();
		yAxisLabel = currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length());
		currentToken = reader.nextLine();
		xMin = Double.parseDouble(currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
		currentToken = reader.nextLine();
		xMax = Double.parseDouble(currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
		currentToken = reader.nextLine();
		yMin = Double.parseDouble(currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
		currentToken = reader.nextLine();
		yMax = Double.parseDouble(currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
		
		output = new DataSet(title, xAxisLabel, yAxisLabel, xMin, xMax, yMin, yMax);
	
	}

	/**
	 * Parses the data from the file and adds it to the output DataSet
	 */
	private void parseData() 
	{
		String currentToken;
		
		while(reader.hasNextLine())
		{
			currentToken = reader.nextLine();
			Double x;
			Double y;
			x = Double.parseDouble(currentToken.split(",")[0]);
			y = Double.parseDouble(currentToken.split(",")[1]);
			output.add(x, y);
		}
		reader.close();
	}
}
