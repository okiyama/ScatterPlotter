package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key Listener for ScatterPlotView
 * Toggles showing lines when space is pressed.
 * Toggle showing points when perios is pressed.
 * @author jocquej
 *
 */
public class ScatterPlotViewKeyListener implements KeyListener
{
	ScatterPlotSettings settings;
	ScatterPlotView view;
	
	public ScatterPlotViewKeyListener(ScatterPlotSettings toEdit, ScatterPlotView toPaint)
	{
		settings = toEdit;
		view = toPaint;
	}


	/**
	 * Deals with the user pressing keyboard buttons.
	 * Toggles showing lines on pressing space.
	 * Toggles showing points on pressing period.
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) 
	{
		if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
		{
			settings.setDrawLines(!settings.isDrawingLines());
		}
		if (keyEvent.getKeyCode() == KeyEvent.VK_PERIOD)
		{
			settings.setDrawPoints(!settings.isDrawingPoints());
		}
		view.repaint();
	}

	/**
	 * Unused, necessary because we implement KeyListener
	 */
	@Override
	public void keyReleased(KeyEvent e) 
	{
	}

	/**
	 * Unused, necessary because we implement KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) 
	{
	}

}
