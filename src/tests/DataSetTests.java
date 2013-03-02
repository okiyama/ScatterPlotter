package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.DataSet;
import model.InvalidRangeException;

import org.junit.Test;


import fileIO.FileToDataSet;

/**
 * Test case for testing out DataSet
 * @author Julian Jocque
 *
 */
public class DataSetTests 
{

	/**
	 * Tests default creation
	 * @throws InvalidRangeException If either of the min values
	 * are greater than the max values or if max are less than min.
	 */
	@Test
	public void testDefaultCreate() throws InvalidRangeException 
	{
		DataSet testSet = new DataSet();
		
		assertEquals("", testSet.getTitle());
		assertEquals("", testSet.getXLabel());
		assertEquals("", testSet.getYLabel());
		assertEquals(0.0, testSet.getXMin(), 0.001);
		assertEquals(0.0, testSet.getYMin(), 0.001);
		assertEquals(10.0, testSet.getXMax(), 0.001);
		assertEquals(10.0, testSet.getYMax(), 0.001);
	}
	
	/**
	 * Tests custom creation
	 * @throws InvalidRangeException If either of the min values
	 * are greater than the max values or if max are less than min.
	 */
	@Test
	public void testCustomCreate() throws InvalidRangeException
	{
		DataSet customSet = new DataSet("title", "xLabel",
				"yLabel", 10.5, 100.3, 5.6, 10.9);
		DataSet nullSet = new DataSet(null, null, null,
				null, null, null, null);

		assertEquals("title", customSet.getTitle());
		assertEquals("xLabel", customSet.getXLabel());
		assertEquals("yLabel", customSet.getYLabel());
		assertEquals(10.5, customSet.getXMin(), 0.001);
		assertEquals(100.3, customSet.getXMax(), 0.001);
		assertEquals(5.6, customSet.getYMin(), 0.001);
		assertEquals(10.9, customSet.getYMax(), 0.001);
		
		assertEquals("", nullSet.getTitle());
		assertEquals("", nullSet.getXLabel());
		assertEquals("", nullSet.getYLabel());
		assertEquals(0.0, nullSet.getXMin(), 0.001);
		assertEquals(0.0, nullSet.getYMin(), 0.001);
		assertEquals(0.0, nullSet.getXMax(), 0.001);
		assertEquals(0.0, nullSet.getYMax(), 0.001);
	}
	
	/**
	 * Tests out construction which should throw an exception
	 * @throws InvalidRangeException If the DataSet is constructed with invalid range
	 */
	@Test(expected=InvalidRangeException.class)
	public void throwExceptionConstructor() throws InvalidRangeException
	{
		DataSet ruleBreaker = new DataSet("null", "null",
				"null", 0.0, -10.5, -100.5, -200.0);
	}
	
	/**
	 * Tests updating views
	 * @throws InvalidRangeException If the range of the DataSet is invalid
	 */
	@Test
	public void testUpdatingViews() throws InvalidRangeException
	{
		DataSet testSet = new DataSet();
		MockView testView = new MockView();
		testSet.attach(testView);
		
		assertEquals(0, testView.getUpdateCount());
		
		testSet.add(10.0,10.0);
		assertEquals(1, testView.getUpdateCount());
		
		testSet.setTitle("testTitle");
		assertEquals(2, testView.getUpdateCount());
		
		testSet.setXLabel("X!!");
		assertEquals(3, testView.getUpdateCount());
		
		testSet.setXMax(99999.9);
		assertEquals(4, testView.getUpdateCount());
		
		testSet.setXMin(-100.2);
		assertEquals(5, testView.getUpdateCount());
		
		testSet.setYLabel("Y?!?");
		assertEquals(6, testView.getUpdateCount());
		
		testSet.setYMax(129399.12939);
		assertEquals(7, testView.getUpdateCount());
		
		testSet.setYMin(-123909123.12390983);
		assertEquals(8, testView.getUpdateCount());
	}
	
	/**
	 * Tests detach
	 * @throws InvalidRangeException If range is invalid
	 */
	@Test
	public void testDetach() throws InvalidRangeException
	{
		DataSet testSet = new DataSet();
		MockView testView = new MockView();
		
		testSet.detach(testView);
		
		assertEquals(0, testView.getUpdateCount());
		
		testSet.attach(testView);
		testSet.detach(testView);
		
		testSet.add(3.3, 2.2);
		
		assertEquals(0, testView.getUpdateCount());
		
		testSet.attach(testView);
		testSet.add(5.2, 6.7);
		testSet.detach(testView);
		
		assertEquals(1, testView.getUpdateCount());
	}
	
	/**
	 * Tests getting data as an array
	 * @throws InvalidRangeException If range is invalid
	 */
	@Test
	public void testGetDataAsArray() throws InvalidRangeException
	{
		DataSet emptySet = new DataSet();
		DataSet customSet = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		customSet.add(10.2,10.2);
		customSet.add(100.3, 100.2);
		customSet.add(0.0,0.0);
		customSet.add(-9222.9,-2999.9);
		customSet.add(99999999.9,99999999.9);
		
		Double[][] expected = new Double[][]{{-9222.9,-2999.9},{0.0,0.0},
				{10.2,10.2},{100.3, 100.2},{99999999.9,99999999.9}};
		
		for (int i = 0; i < expected.length; i++)
		{
			if (!expected[i][0].equals(customSet.getDataAsArray()[i][0])
					|| !expected[i][1].equals(customSet.getDataAsArray()[i][1]))
			{
				fail("GetDataAsArray failed on customSet");
			}
		}
		assertEquals(new Double[0][0].length, emptySet.getDataAsArray().length);
	}
	
	/**
	 * Tests adding points to the DataSet
	 * @throws InvalidRangeException If the range is invalid
	 */
	@Test
	public void testAddValidPoints() throws InvalidRangeException
	{
		DataSet testSet = new DataSet("","","",-10.0,10.0,-10.0,10.0);
		
		testSet.add(0.0,0.0);
		testSet.add(-9.9,-9.9);
		testSet.add(-9.3,9.6);
		testSet.add(8.2,-2.3);
		
		Double[][] expected = new Double[][]{ {-9.9,-9.9},
				{-9.3,9.6},{0.0,0.0},{8.2,-2.3}};
		
		assertEquals(4, testSet.sizeOfData());
		for (int i = 0; i < expected.length; i++)
		{
			if (!expected[i][0].equals(testSet.getDataAsArray()[i][0])
					|| !expected[i][1].equals(testSet.getDataAsArray()[i][1]))
			{
				fail("AddValidPoints failed on testSet");
			}
		}
	}
	
	/**
	 * Tests adding points to the DataSet
	 * @throws InvalidRangeException If the range is invalid
	 */
	@Test
	public void testAddInvalidPoints() throws InvalidRangeException
	{
		DataSet testSet = new DataSet();
		
		testSet.add(null,4.5);
		testSet.add(2.2,null);
		testSet.add(null, null);
		testSet.add(-100.0,4.4);
		testSet.add(100.2, 0.0);
		testSet.add(9.2,-123341.123);
		testSet.add(2.2, 10231.112);
		
		assertEquals(0, testSet.sizeOfData());
	}
	
	/**
	 * Tests removing valid points
	 * @throws InvalidRangeException If range is invalid
	 */
	@Test
	public void removeValidPoint() throws InvalidRangeException
	{
		DataSet testSet = new DataSet("title", "xLabel",
			"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		testSet.add(10.2,10.2);
		testSet.add(100.3, 100.2);
		testSet.add(0.0,0.0);
		testSet.add(-9222.9,-2999.9);
		testSet.add(99999999.9,99999999.9);
		
		DataSet oneGone = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		oneGone.add(100.3, 100.2);
		oneGone.add(0.0,0.0);
		oneGone.add(-9222.9,-2999.9);
		oneGone.add(99999999.9,99999999.9);
		
		DataSet twoGone = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		twoGone.add(0.0,0.0);
		twoGone.add(-9222.9,-2999.9);
		twoGone.add(99999999.9,99999999.9);
		
		DataSet threeGone = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		threeGone.add(-9222.9,-2999.9);
		threeGone.add(99999999.9,99999999.9);
		
		DataSet fourGone = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		fourGone.add(-9222.9,-2999.9);
		
		DataSet allGone = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		
		testSet.remove(10.2, 10.2);
		assertEquals(4, testSet.sizeOfData());
		assertEquals(oneGone, testSet);
		
		testSet.remove(100.3, 100.2);
		assertEquals(3, testSet.sizeOfData());
		assertEquals(twoGone, testSet);
		
		testSet.remove(0.0,0.0);
		assertEquals(2, testSet.sizeOfData());
		assertEquals(threeGone, testSet);
		
		testSet.remove(99999999.9,99999999.9);
		assertEquals(1, testSet.sizeOfData());
		assertEquals(fourGone, testSet);
		
		testSet.remove(-9222.9,-2999.9);
		assertEquals(0, testSet.sizeOfData());
		assertEquals(allGone, testSet);
	}
	
	/**
	 * Tests removing invalid points from the DataSet
	 * @throws InvalidRangeException If range is invalid
	 */
	@Test
	public void removeInvalidPoints() throws InvalidRangeException
	{
		DataSet testSet = new DataSet();
		
		testSet.remove(10.0,10.0);
		assertEquals(0, testSet.sizeOfData());
		
		testSet.add(3.3, 3.8);
		testSet.remove(3.4, 3.8);
		testSet.remove(3.8,3.3);
		testSet.remove(null,3.8);
		testSet.remove(3.3,null);
		testSet.remove(null,null);
		
		assertEquals(1, testSet.sizeOfData());
		assertEquals(3.3, testSet.getDataAsArray()[0][0], 0.0);
		assertEquals(3.8, testSet.getDataAsArray()[0][1], 0.0);
	}
	
	/**
	 * Tests loading data from a file
	 * @throws InvalidRangeException If range is invalid
	 * @throws FileNotFoundException If file isn't found
	 */
	@Test
	public void testLoad() throws InvalidRangeException, FileNotFoundException
	{
		DataSet testSet = new DataSet();
		
		FileToDataSet loader = new FileToDataSet("testData");
		testSet.load("testData");
		assertEquals(loader.parse(), testSet);
		
		loader = new FileToDataSet("customData");
		testSet.load("customData");
		assertEquals(loader.parse(), testSet);

		loader = new FileToDataSet("invalidPoints");
		testSet.load("invalidPoints");
		assertEquals(loader.parse(), testSet);

		loader = new FileToDataSet("default_dataset");
		testSet.load("default_dataset");
		assertEquals(loader.parse(), testSet);
	}
	
	/**
	 * Tests saving this DataSet to a file
	 * @throws InvalidRangeException If range is invalid
	 * @throws IOException If we can't save to the file
	 */
	@Test
	public void testSave() throws InvalidRangeException, IOException
	{
		DataSet defaultSet = new DataSet();
		
		DataSet testSet = new DataSet("title", "xLabel",
				"yLabel", -100000000.5, 10000000000.3, -99999999999.6, 99999999910.9);
		testSet.add(10.2,10.2);
		testSet.add(100.3, 100.2);
		testSet.add(0.0,0.0);
		testSet.add(-9222.9,-2999.9);
		testSet.add(99999999.9,99999999.9);
		
		defaultSet.save("tests");
		FileToDataSet loader = new FileToDataSet("tests");
		assertEquals(defaultSet, loader.parse());
		
		loader = new FileToDataSet("tests");
		testSet.save("tests");
		assertEquals(testSet, loader.parse());
	}
	
	/**
	 * Tests equality on equal DataSets
	 * @throws InvalidRangeException If range invalid
	 */
	@Test
	public void testEqualityEqual() throws InvalidRangeException
	{
		DataSet set1 = new DataSet();
		DataSet set2 = new DataSet();
		
		assertEquals(set1, set1);
		assertEquals(set1, set2);
		
		set1.add(null, null);
		set2.add(null, null);
		assertEquals(set1, set2);
		
		set1.add(3.3,2.5);
		set2.add(3.3,2.5);
		assertEquals(set1, set2);
		
		set1.add(1000.2,0.2);
		set2.add(1000.2,0.2);
		assertEquals(set1, set2);
		
		set1.setTitle("test");
		set2.setTitle("test");
		assertEquals(set1, set2);
		
		set1.setXLabel("X");
		set2.setXLabel("X");
		assertEquals(set1, set2);
		
		set1.setXMax(100.2);
		set2.setXMax(100.2);
		assertEquals(set1, set2);
		
		set1.setXMin(-100.0);
		set2.setXMin(-100.0);
		assertEquals(set1, set2);
		
		set1.setYLabel("Y");
		set2.setYLabel("Y");
		assertEquals(set1, set2);
		
		set1.setYMax(100000.0);
		set2.setYMax(100000.0);
		assertEquals(set1, set2);
		
		set1.setYMin(-1002.3);
		set2.setYMin(-1002.3);
		assertEquals(set1, set2);
	}
	
	/**
	 * Tests equality on non-equal DataSets
	 * @throws InvalidRangeException If range invalid
	 */
	@Test
	public void testEqualityNotEqual() throws InvalidRangeException
	{
		DataSet set1 = new DataSet();
		DataSet set2 = new DataSet();
		
		set1.setTitle("Title");
		assertFalse(set1.equals(set2));
		set2.setTitle("Title");
		
		set1.add(1.2,1.3);
		assertFalse(set1.equals(set2));
		set1.add(1.2,1.3);
		
		set1.setTitle("test");
		assertFalse(set1.equals(set2));
		set2.setTitle("test");
		
		set1.setXLabel("X");
		assertFalse(set1.equals(set2));
		set2.setXLabel("X");
		
		set1.setXMax(100.2);
		assertFalse(set1.equals(set2));
		set2.setXMax(100.2);
		
		set1.setXMin(-100.0);
		assertFalse(set1.equals(set2));
		set2.setXMin(-100.0);
		
		set1.setYLabel("Y");
		assertFalse(set1.equals(set2));
		set2.setYLabel("Y");
		
		set1.setYMax(100000.0);
		assertFalse(set1.equals(set2));
		set2.setYMax(100000.0);
		
		set1.setYMin(-1002.3);
		assertFalse(set1.equals(set2));
		set2.setYMin(-1002.3);
	}
	
	/**
	 * Tests getSizeOfData
	 * @throws InvalidRangeException If range is invalid
	 */
	@Test
	public void testGetSizeOfData() throws InvalidRangeException
	{
		DataSet testSet = new DataSet();
		
		assertEquals(0, testSet.sizeOfData());
		
		testSet.add(2.3, 3.5);
		assertEquals(1, testSet.sizeOfData());
		
		testSet.add(1.1, 5.6);
		assertEquals(2, testSet.sizeOfData());
		
		testSet.remove(1.1, 5.6);
		assertEquals(1, testSet.sizeOfData());
		
		testSet.remove(2.3, 3.5);
		assertEquals(0, testSet.sizeOfData());
		
		testSet.add(1100.0,200.2);
		testSet.add(null,1.2);
		testSet.add(2.3, null);
		testSet.add(null, null);
		assertEquals(0, testSet.sizeOfData());
	}
}
