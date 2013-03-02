package model;

/**
 * Represents one data point. A data point is a pair of Doubles.
 * Either or both points may be null.
 * @author Julian Jocque
 *
 */
public class DataPoint implements Comparable<DataPoint>
{
	private Double x;
	private Double y;
	
	/**
	 * Default constructor, makes a DataPoint with null data.
	 */
	public DataPoint()
	{
		this.x = null;
		this.y = null;
	}
	
	/**
	 * Constructs a DataPoint with given Number x and y.
	 * @param x The left side of the DataPoint
	 * @param y The right side of the DataPoint
	 */
	public DataPoint(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Tests if this DataPoint is the same as another object
	 * @return true if the data contained is the same, otherwise false
	 */
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		else if (!(o instanceof DataPoint))
		{
			return false;
		}
		else if(this.getX() == null || this.getY() == null
				|| ((DataPoint)o).getX() == null || ((DataPoint)o).getY() == null)
		{
			Double thisX = this.getX();
			Double thisY = this.getY();
			Double otherX = ((DataPoint)o).getX();
			Double otherY = ((DataPoint)o).getY();
			if (thisY != null && (thisX == otherX) && thisY.equals(otherY))
			{
				return true;
			}
			else if (thisX != null && (thisY == otherY) && thisX.equals(otherX))
			{
				return true;
			}
			else if ((thisX == otherX) && (thisY == otherY))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return this.getX().equals(((DataPoint) o).getX()) 
					&& this.getY().equals(((DataPoint)o).getY());
		}
	}

	/**
	 * Returns this DataPoint as a string. Formatted as such:
	 * (<X>, <Y>)
	 * X and Y are doubles representing the data in the DataPoint. 
	 * No \n at the end of the string.
	 * @return The DataPoint as a string.
	 */
	public String toString()
	{
		String toReturn = "(" + this.getX() + "," + this.getY() + ")";
		return toReturn;
	}
	
	/**
	 * Gets the Y value of this DataPoint
	 * @return The Y value.
	 */
	public Double getY() 
	{
		return this.y;
	}

	/**
	 * Getter for the X value of this DataPoint
	 * @return The X value.
	 */
	public Double getX() 
	{
		return this.x;
	}
	
	/**
	 * Sets the right side value of this DataPoint
	 */
	public void setX(Double newX)
	{
		this.x = newX;
	}
	
	/**
	 * Sets the left side value of this DataPoint
	 */
	public void setY(Double newY)
	{
		this.y = newY;
	}

	/**
	 * Compares this DataPoint to another DataPoint.
	 * Null is a valid value and considered the lowest value possible.
	 * @param other The DataPoint we are comparing to.
	 * @return 1 is the X value of this DataPoint is greater than the X value of other,
	 * 0 if the X values are equal and -1 if the X value of this DataPoint is less than
	 * the X value of the other DataPoint.
	 */
	public int compareTo(DataPoint other) 
	{
		Double thisNumber = this.getX();
		Double otherNumber = ((DataPoint)other).getX();
		double thisValue;
		double otherValue;
		
		if (thisNumber == null && otherNumber == null)
		{
			return 0;
		}
		else if (thisNumber != null && otherNumber == null)
		{
			return 1;
		}
		else if (thisNumber == null && otherNumber != null)
		{
			return -1;
		}

		otherValue = other.getX().doubleValue();
		thisValue = this.getX().doubleValue();

		if (thisValue > otherValue)
		{
			return 1;
		}
		else if (thisValue == otherValue)
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Swaps the X and Y values
	 */
	public void swap()
	{
		Double temp = x;
		x = y;
		y = temp;
	}

	
}
