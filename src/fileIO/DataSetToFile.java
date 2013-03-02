package fileIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.DataSet;

/**
 * Takes a DataSet and writes it out to a file.
 * @author Julian Jocque
 */
public class DataSetToFile 
{
	private static final String FOLDER_TO_USE = "data";
	private DataSet toWrite;
	private FileWriter fileStream;

	/**
	 * Constructs a FileOut to write the given DataSet to a file
	 * @param dataSetToSave The DataSet we are writing to a file
	 */
	public DataSetToFile(DataSet dataSetToSave)
	{
		toWrite = dataSetToSave;
	}
	
	/**
	 * Writes the DataSet to the given file name.
	 * File will be overwritten if it exists, otherwise it will be created.
	 * @throws IOException if the named file exists but is a directory rather than a regular file, 
	 * does not exist but cannot be created, or cannot be opened for any other reason
	 */
	public void write(String fileToWriteTo) throws IOException
	{
		String filePath = FOLDER_TO_USE + "/" + fileToWriteTo + ".jjf";
		fileStream = new FileWriter(filePath);
		BufferedWriter writer = new BufferedWriter(fileStream);
		Double[][] dataArray = toWrite.getDataAsArray();
		
		String file;
		file = "Title=" + toWrite.getTitle() + "\n";
		file += "X-Axis Label=" + toWrite.getXLabel() + "\n";
		file += "Y-Axis Label=" + toWrite.getYLabel() + "\n";
		file += "X-Min=" + toWrite.getXMin() + "\n";
		file += "X-Max=" + toWrite.getXMax() + "\n";
		file += "Y-Min=" + toWrite.getYMin() + "\n";
		file += "Y-Max=" + toWrite.getYMax() + "\n";
		
		for (int i = 0; i < dataArray.length; i++)
		{
			file += dataArray[i][0] + ",";
			file += dataArray[i][1] + "\n";
		}
		
		writer.write(file);
		writer.close();
	}
}
