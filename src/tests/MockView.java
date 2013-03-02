package tests;

import view.DataView;


/**
 * MockView used as a test double for testing out DataSet.
 * @author jocquej
 *
 */
public class MockView implements DataView
{
	private int updateCount;
	
	public MockView()
	{
		updateCount = 0;
	}

	/**
	 * Counts how many updates this view has received
	 */
	@Override
	public void update() 
	{
		updateCount++;
	}

	/**
	 * Getter for the update count
	 * @return The number of updates this view has received
	 */
	public int getUpdateCount()
	{
		return updateCount;
	}
}
