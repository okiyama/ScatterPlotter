package main;

import model.DataSet;
import model.InvalidRangeException;

import view.ScatterPlotView;
import view.TableView;

/**
 * Main class for a Document-View where the document holds x, y pairs as data
 * and the views display the document. 
 * @author Julian Jocque
 *
 */
/*
 * As a student at Union College, I am part of a community that values intellectual effort, 
 * curiosity and discovery. I understand that in order to truly claim my educational and academic
 * achievements, I am obligated to act with academic integrity. Therefore, I affirm that I will carry
 * out my academic endeavors with full academic honesty, and I rely on my fellow students to do the 
 * same.
 */
public class Client 
{
	/**
	 * Main method for a Document-View that shows a DataSet.
	 * Creates a default DataSet then displays it as a ScatterPlot
	 * and as a Table.
	 * ScatterPlot is much more robust and allows for all the data
	 * in the DataSet to be edited, saved and loaded by the user.
	 * @param args command line arguments
	 * @throws InvalidRangeException If either of the min values
	 * are greater than the max values or if max are less than min.
	 */
	public static void main(String[] args) throws InvalidRangeException 
	{
	    DataSet theSet = new DataSet();
	    ScatterPlotView scatterPlotView = new ScatterPlotView(theSet);
	    theSet.attach(scatterPlotView);
	    scatterPlotView.createAndDisplay();

	    TableView tableView = new TableView(theSet);
	    theSet.attach(tableView);
	    tableView.createAndDisplay();
	}

}
