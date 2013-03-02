package view;

/**
 * Interface to indicate that the implementing class is a DataView capable of
 * interacting with DataModel.
 * @author Julian Jocque
 */
public interface DataView 
{
	/**
	 * Updates the view based on data from the DataModel that this class is observing
	 */
	public void update();
}
