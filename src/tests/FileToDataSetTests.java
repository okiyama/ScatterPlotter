package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import model.DataSet;
import model.InvalidRangeException;

import org.junit.Test;

import fileIO.FileToDataSet;

/**
 * Tests loading DataSets from files.
 * @author Julian Jocque
 *
 */
public class FileToDataSetTests {

	/**
	 * Tests loading a default created DataSet from a file
	 * @throws FileNotFoundException If the given file does not exist
	 * @throws InvalidRangeException If the given file contains invalid range parameters
	 */
	@Test
	public void testCreate() throws FileNotFoundException, InvalidRangeException 
	{
		DataSet defaultSet = new DataSet();
		FileToDataSet testFileIn = new FileToDataSet("default_dataset");
		DataSet loadedDefaultSet = testFileIn.parse();
		
		assertEquals(defaultSet, loadedDefaultSet);
	}
	
	/**
	 * Tests loading a file with invalid range, expects to catch an exception.
	 * @throws FileNotFoundException If the given file does not exist
	 * @throws InvalidRangeException If the given file contains invalid range parameters
	 */
	@Test(expected=InvalidRangeException.class)
	public void testLoadInvalidRange() throws FileNotFoundException, InvalidRangeException
	{
		FileToDataSet invalidRangeFile = new FileToDataSet("invalidRange");
		DataSet invalidRange = invalidRangeFile.parse();
	}
	
	/**
	 * Tests loading a file with points that are out of valid ranges
	 * @throws InvalidRangeException If the given file contains invalid range parameters
	 * @throws FileNotFoundException If the given file does not exist
	 */
	@Test
	public void testLoadInvalidPoints() throws InvalidRangeException, FileNotFoundException
	{
		DataSet testSet = new DataSet("title", "x-axis label", "y-axis label",
				-10.0, 10.0, -10.0, 10.0);
		testSet.add(-11.0,0.0);
		testSet.add(-4.5,-14000.0);
		testSet.add(12333.123311,3.4);
		testSet.add(3.2335651,991233.844);
		testSet.add(-119.0,-1199.33);
		testSet.add(13566.1939,1299.3929);
		
		FileToDataSet loadingFrom = new FileToDataSet("invalidPoints");
		DataSet otherTestSet = loadingFrom.parse();
		
		assertEquals(testSet, otherTestSet);
	}
	
	/**
	 * Tests loading a customized DataSet with a wide range of values
	 * @throws InvalidRangeException If we receive an invalid range
	 * @throws FileNotFoundException If the given file does not exist
	 */
	@Test
	public void testLoadCustomSet() throws InvalidRangeException, FileNotFoundException
	{
		DataSet createdSet = new DataSet("title", "xAxis", "yAxis",
				-1000.0, 1000.0, -1000000.0, 1000000.0);
		createdSet.add(-529.3,399.2);
		createdSet.add(0.0,0.0);
		createdSet.add(0.0,782.9228);
		createdSet.add(-299.133,0.0);
		createdSet.add(933.2,938214.282);
		createdSet.add(-1000.0,-1000000.0);
		createdSet.add(1000.0,1000000.0);
		createdSet.add(476.229,0.0);
		createdSet.add(8.0,-19923.3);
		createdSet.add(1.1,1.1);
		createdSet.add(3.1,0.0);
		
		FileToDataSet toLoadFrom = new FileToDataSet("customData");
		DataSet customData = toLoadFrom.parse();
		
		assertEquals(createdSet, customData);
	}
}
