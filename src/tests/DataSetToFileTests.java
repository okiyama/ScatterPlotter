package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import model.DataSet;
import model.InvalidRangeException;

import org.junit.Test;

import fileIO.DataSetToFile;
import fileIO.FileToDataSet;

/**
 * Tests writing a DataSet to a file.
 * @author Julian Jocque
 *
 */
public class DataSetToFileTests 
{
	/**
	 * Tests writing a default DataSet
	 * @throws InvalidRangeException If the DataSet has an invalid range
	 * @throws IOException If the file cannot be written
	 */
	@Test
	public void testWriteDefault() throws InvalidRangeException, IOException 
	{
		DataSet testSet = new DataSet();
		DataSetToFile saver = new DataSetToFile(testSet);
		saver.write("tests");
		FileToDataSet loader = new FileToDataSet("tests");
		
		assertEquals(testSet, loader.parse());
	}

	/**
	 * Tests writing a DataSet with customized data
	 * @throws InvalidRangeException If the DataSet has an invalid range
	 * @throws IOException If the file cannot be written
	 */
	@Test
	public void testWriteCustom() throws InvalidRangeException, IOException
	{
		DataSet testSet = new DataSet("title", "xAxis", "yAxis", -1000.0, 1000.0, -1000000.0, 1000000.0);
		testSet.add(-529.3,399.2);
		testSet.add(0.0,0.0);
		testSet.add(0.0,782.9228);
		testSet.add(-299.133,0.0);
		testSet.add(933.2,938214.282);
		testSet.add(-1000.0,-1000000.0);
		testSet.add(1000.0,1000000.0);
		testSet.add(476.229,0.0);
		testSet.add(8.0,-19923.3);
		testSet.add(1.1,1.1);
		testSet.add(3.1,0.0);
		
		DataSetToFile saver = new DataSetToFile(testSet);
		saver.write("tests");
		
		FileToDataSet loader = new FileToDataSet("tests");
		
		assertEquals(testSet, loader.parse());
	}
}
