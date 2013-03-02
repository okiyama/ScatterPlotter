package model;

/**
 * Exception raised when any of the following are true:
 * X Min > X Max
 * X Max < X Min
 * Y Min > Y Max
 * Y Max < Y Min
 * In reference to the ranges of a DataSet.
 * @author Julian Jocque
 *
 */
public class InvalidRangeException extends Exception 
{
	/**
	 * Default constructor
	 */
	public InvalidRangeException()
	{
		super("Invalid Range");
	}
}
