package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import fileIO.FileToDataSet;
import fileIO.DataSetToFile;

import view.DataView;

/**
 * A DataModel to be observed by Views
 * 
 * Represents a sorted DataModel with pairs of Doubles for the data.
 * Data is always sorted by increasing X values of the data points.
 * Data has labels for the X and Y values as well as a title.
 * Data has a range of acceptable values for X and Y.
 * Only allows one instance of a particular pair in the DataModel.
 * @author Julian Jocque
 */
public class DataSet
{
	private ArrayList<DataView> listeners;
	private ArrayList<DataPoint> data;
	private String title;
	private String xLabel;
	private String yLabel;
	private Double xMax;
	private Double xMin;
	private Double yMax;
	private Double yMin;
	
	/**
	 * Default constructor, creates an empty DataSet
	 * Has empty String for Title, X Label or Y Label.
	 * X and Y have range 0-10
	 * @throws InvalidRangeException If either of the min values
	 * are greater than the max values or if max are less than min.
	 */
	public DataSet() throws InvalidRangeException
	{
		listeners = new ArrayList<DataView>();
		data = new ArrayList<DataPoint>();
		setTitle("");
		setXLabel("");
		setYLabel("");
		setXMin(0.0);
		setYMin(0.0);
		setXMax(10.0);
		setYMax(10.0);
	}
	
	/**
	 * Creates a DataSet with given Title, X Label and Y Label
	 * @param title The title to set
	 * @param xLabel The name of X data
	 * @param yLabel The name of the Y data
	 * @throws InvalidRangeException If either of the min values
	 * are greater than the max values or if max are less than min.
	 */
	public DataSet(String title, String xLabel, String yLabel, Double xMin, Double xMax, Double yMin, Double yMax) throws InvalidRangeException
	{
		listeners = new ArrayList<DataView>();
		data = new ArrayList<DataPoint>();
		setTitle(title);
		setXLabel(xLabel);
		setYLabel(yLabel);
		setXMin(xMin);
		setYMin(yMin);
		setXMax(xMax);
		setYMax(yMax);
	}
	
	/**
	 * Attaches the given DataView to this DataModel
	 */
	public void attach(DataView toAttach)
	{
		listeners.add(toAttach);
	}
	
	/**
	 * Detaches the given DataView from this DataModel
	 */
	public void detach(DataView toDetach)
	{
		listeners.remove(toDetach);
	}
	
	/**
	 * Updates all listeners attached to this DataModel
	 */
	private void updateListeners()
	{
		for (DataView listener : listeners)
		{
			listener.update();
		}
	}
	
	/**
	 * Adds the given two data points to the DataSet if they are not already represented
	 * and if they are within the acceptable range of values as a pair then sorts the data.
	 * @return True if the point was added successfully, otherwise false
	 */
	public boolean add(Double point1, Double point2)
	{
		DataPoint toAdd = new DataPoint(point1, point2);
		
		boolean willAdd = isLegalPoint(toAdd);
		
		if (willAdd)
		{
			data.add(toAdd);
		}
		Collections.sort(data);
		updateListeners();
		return willAdd;
	}
	
	/**
	 * Checks the given point can be added to this DataSet legally
	 * @param checking The point to check
	 * @return True if it can be added, else false.
	 */
	private boolean isLegalPoint(DataPoint checking) 
	{
		Double x = checking.getX();
		Double y = checking.getY();
		
		return (x != null && y != null && 
				(!data.contains(checking)) && 
				(x.compareTo(getXMin()) != -1 && x.compareTo(getXMax()) != 1) &&
				(y.compareTo(getYMin()) != -1 && y.compareTo(getYMax()) != 1));
	}

	/**
	 * Removes the given two data points from the DataSet
	 */
	public void remove(Double point1, Double point2)
	{
		DataPoint toRemove = new DataPoint(point1, point2);
		data.remove(toRemove);
		updateListeners();
	}
	
	/**
	 * Gives the points as a 2D array. Each array item is a 2-item array with the x, y
	 * values of that particular point.
	 * EX: [[1.0,0.0],[3.3,4.5]] would represent the DataModel with the points (1.0,0.0) and (3.3,4.5) in it.
	 * @return The data in the model as a 2D array.
	 */
	public Double[][] getDataAsArray()
	{
		Object[] dataAsPoints = data.toArray();
		Double[][] dataAsDoubles = new Double[dataAsPoints.length][2];
		for (int row = 0; row < dataAsPoints.length; row++)
		{
			dataAsDoubles[row][0] = ((DataPoint)dataAsPoints[row]).getX();
			dataAsDoubles[row][1] = ((DataPoint)dataAsPoints[row]).getY();
		}
		return dataAsDoubles;
	}
	
	/**
	 * Replaces the current data in this DataSet with
	 * the data from the given file.
	 * @throws InvalidRangeException If the given file contains invalid x, y range values
	 * @throws FileNotFoundException If the given file does not exist
	 */
	public void load(String fileToLoad) throws FileNotFoundException, InvalidRangeException
	{
		FileToDataSet loader = new FileToDataSet(fileToLoad);
		DataSet newSet = loader.parse();
		
		eraseCurrentData();
		
		setData(newSet);
		updateListeners();
	}
	
	/**
	 * Sets the data of this set to the data of a given new set
	 * @param newSet The set we are getting new data from
	 * @throws InvalidRangeException If we set any range values to invalid values
	 */
	private void setData(DataSet newSet) throws InvalidRangeException 
	{

		Double[][] newData = newSet.getDataAsArray();
		
		setTitle(newSet.getTitle());
		setXLabel(newSet.getXLabel());
		setYLabel(newSet.getYLabel());
		setXMin(newSet.getXMin());
		setYMin(newSet.getYMin());
		setXMax(newSet.getXMax());
		setYMax(newSet.getYMax());
		
		for (int i = 0; i < newData.length; i++)
		{
			Double x = newData[i][0];
			Double y = newData[i][1];
			data.add(new DataPoint(x, y));
		}
	}

	/**
	 * Clears out all the current data in this DataSet.
	 */
	private void eraseCurrentData() 
	{
		data = new ArrayList<DataPoint>();
		title = "";
		xLabel = "";
		yLabel = "";
		xMin = null;
		yMin = null;
		xMax = null;
		yMax = null;
	}

	/**
	 * Saves the current DataSet to the given file name.
	 * File extension is automatically appended.
	 * Example filename: data/coolGraph
	 * @param fileName the name of the file we should write to
	 * @throws IOException If we cannot write to the given file
	 */
	public void save(String fileName) throws IOException
	{
		DataSetToFile saver = new DataSetToFile(this);
		saver.write(fileName);
	}
	
	/**
	 * Checks if this DataSet is equal to another Object
	 * @return true if they are equal, otherwise false
	 */
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		else if (!(other instanceof DataSet))
		{
			return false;
		}
		else
		{
			return this.isAllSameData((DataSet)other);
		}
	}

	/**
	 * Checks that all the data in this DataSet is the same
	 * as that in the given other DataSet.
	 * @param other The DataSet we are comparing against
	 * @return true if all data is the same, false if any data differs
	 */
	private boolean isAllSameData(DataSet other) 
	{
		boolean allValuesSame = this.getTitle().equals(other.getTitle()) &&
				this.getXLabel().equals(other.getXLabel()) &&
				this.getYLabel().equals(other.getYLabel()) &&
				this.getXMin().equals(other.getXMin()) &&
				this.getXMax().equals(other.getXMax()) &&
				this.getYMin().equals(other.getYMin()) &&
				this.getYMax().equals(other.getYMax());
		if (!allValuesSame)
		{
			return false;
		}
		else if (this.sizeOfData() != other.sizeOfData())
		{
			return false;
		}
		
		boolean dataAllSame = true;
		for (int i = 0; i < this.sizeOfData();i++)
		{
			dataAllSame = (this.getDataAsArray()[i][0].equals(other.getDataAsArray()[i][0]) 
					&& this.getDataAsArray()[i][1].equals(other.getDataAsArray()[i][1]));
		}
		return dataAllSame;
	}

	/**
	 * Converts this DataSet into a string then returns it. Returns it in the
	 * following form
	 * 
	 * Title: <title>
	 * X-Axis Label: <xLabel>
	 * Y-Axis Label: <yLabel>
	 * X-Min: <xMin>
	 * X-Max: <xMax>
	 * Y-Min: <yMin>
	 * Y-Max: <yMax>
	 * Data: (x1,y1)
	 * (x2,y2)
	 * ...
	 * (xn,yn)
	 * 
	 * Where Title and the labels are strings and x, y min and max are doubles.
	 * Data is points in (x,y) format.
	 * @return This DataSet as a string
	 */
	public String toString()
	{
		String toReturn;
		toReturn = "Title: " + this.getTitle() + "\n";
		toReturn += "X-Axis Label: " + this.getXLabel() + "\n";
		toReturn += "Y-Axis Label: " + this.getYLabel() + "\n";
		toReturn += "X-Min: " + this.getXMin() + "\n";
		toReturn += "X-Max: " + this.getXMax() + "\n";
		toReturn += "Y-Min: " + this.getYMin() + "\n";
		toReturn += "Y-Max: " + this.getYMax() + "\n";
		
		toReturn += "Data: ";
		for (int i = 0; i < data.size(); i++)
		{
			toReturn += "(" + data.get(i).getX() + "," + 
					data.get(i).getY() + ")\n";
		}
		return toReturn;
	}
	
	/**
	 * Returns the size of the data in the DataSet
	 * @return The number of elements in the data of the DataSet
	 */
	public int sizeOfData() 
	{
		return data.size();
	}
	
	/**
	 * Getter for the title
	 * @return The title
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Setter for the title
	 * Sets as empty string if given null
	 * @param title Title to set for this DataSet
	 */
	public void setTitle(String title) {
		if (title == null) {
			this.title = "";
		}
		else {
			this.title = title;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the label of the X-Axis
	 * @return The label of the X-Axis
	 */
	public String getXLabel() {
		return xLabel;
	}
	
	/**
	 * Setter for the Label of the X-Axis
	 * Sets as empty string if it receives null
	 * @param xLabel The label of the X-Axis to set
	 */
	public void setXLabel(String xLabel) {
		if (xLabel == null) {
			this.xLabel = "";
		}
		else {
			this.xLabel = xLabel;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the label of the Y-Axis
	 * @return The label of the Y-axis
	 */
	public String getYLabel() {
		return yLabel;
	}
	
	/**
	 * Setter for the Label of the Y-Axis
	 * Sets as empty string if it receives null
	 * @param yLabel The label of the Y-Axis to set
	 */
	public void setYLabel(String yLabel) {
		if (yLabel == null) {
			this.yLabel = "";
		}
		else {
			this.yLabel = yLabel;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the max value of X
	 * @return The max value of X
	 */
	public Double getXMax() {
		return xMax;
	}
	
	/**
	 * Setter for the max value of X
	 * @param xMax Max value of X to set
	 * @throws InvalidRangeException If given xMax is greater than current xMin
	 */
	public void setXMax(Double xMax) throws InvalidRangeException {
		if (xMax == null)
		{
			this.xMax = 0.0;
		}
		else if (this.xMin != null && xMax < this.xMin) {
			throw new InvalidRangeException();
		}
		else {
			this.xMax = xMax;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the minimum value of X
	 * @return The minimum value of X
	 */
	public Double getXMin() {
		return xMin;
	}
	
	/**
	 * Setter for the min value of X
	 * @param xMin Min value of X to set
	 * @throws InvalidRangeException If given xMin is greater than current xMax
	 */
	public void setXMin(Double xMin) throws InvalidRangeException {
		if (xMin == null)
		{
			this.xMin = 0.0;
		}
		else if (this.xMax != null && xMin > this.xMax) {
			throw new InvalidRangeException();
		}
		else 
		{
			this.xMin = xMin;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the maximum value of Y
	 * @return The maximum value of Y
	 */
	public Double getYMax() {
		return yMax;
	}
	
	/**
	 * Setter for the max value of Y
	 * @param yMax Max value of Y to set
	 * @throws InvalidRangeException If given yMax is less than current yMin
	 */
	public void setYMax(Double yMax) throws InvalidRangeException {
		if (yMax == null)
		{
			this.yMax = 0.0;
		}
		else if (this.yMin != null && yMax < this.yMin)
		{
			throw new InvalidRangeException();
		}
		else 
		{
			this.yMax = yMax;
		}
		updateListeners();
	}
	
	/**
	 * Getter for the minimum value of Y
	 * @return The minimum value of Y
	 */
	public Double getYMin() {
		return yMin;
	}
	
	/**
	 * Setter for the min value of Y
	 * @param yMin Min value of Y to set
	 * @throws InvalidRangeException If given yMin is greater than current yMax
	 */
	public void setYMin(Double yMin) throws InvalidRangeException {
		if (yMin == null)
		{
			this.yMin = 0.0;
		}
		else if (this.yMax != null && yMin > this.yMax) {
			throw new InvalidRangeException();
		}
		else 
		{
			this.yMin = yMin;
		}
		updateListeners();
	}
}
