package view;

import java.awt.Color;

/**
 * The settings for a ScatterPlotView
 * @author Julian Jocque
 *
 */
public class ScatterPlotSettings 
{
	private Color pointsColor;
	private Color lineColor;
	private int pointRadius;
	private boolean drawLines;
	private boolean drawPoints;
	
	/**
	 * Default constructor, sets default settings
	 */
	public ScatterPlotSettings()
	{
		setPointsColor(Color.RED);
		setLineColor(Color.BLACK);
		setPointRadius(3);
		drawLines = true;
		setDrawPoints(true);
	}

	/**
	 * Gets the current color of the points
	 * @return The current color of the points
	 */
	public Color getPointsColor() 
	{
		return pointsColor;
	}

	/**
	 * Sets the color of the points to another color
	 * @param pointsColor Color to set the color of the points to
	 */
	public void setPointsColor(Color pointsColor) 
	{
		this.pointsColor = pointsColor;
	}

	/**
	 * Gets the current color of the lines connecting the points
	 * @return current color of the lines connecting points
	 */
	public Color getLineColor() 
	{
		return lineColor;
	}

	/**
	 * Sets the color of the lines connecting the points
	 * @param lineColor Color to set the lines connecting the points to
	 */
	public void setLineColor(Color lineColor) 
	{
		this.lineColor = lineColor;
	}

	/**
	 * Getter for how large the points should be
	 * @return Radius of the points in pixels
	 */
	public int getPointRadius() 
	{
		return pointRadius;
	}
	
	/**
	 * Sets the size of the points
	 * @param radius Radius of how large points should be
	 */
	public void setPointRadius(int radius)
	{
		this.pointRadius = radius;
	}

	/**
	 * Returns whether or not this plot is drawing lines
	 * @return true if this Plot is drawing lines, else false
	 */
	public boolean isDrawingLines() 
	{
		return this.drawLines;
	}
	
	/**
	 * Sets whether to draw lines or not
	 * @param shouldDraw Whether or not we should draw lines
	 */
	public void setDrawLines(boolean shouldDraw) 
	{
		this.drawLines = shouldDraw;
	}

	/**
	 * Returns whether or not this Plot is drawing points
	 * @return true if we are drawing points, else false
	 */
	public boolean isDrawingPoints() 
	{
		return drawPoints;
	}

	/**
	 * Sets whether or not to draw points
	 * @param drawPoints Whether or not we should draw points
	 */
	public void setDrawPoints(boolean drawPoints) 
	{
		this.drawPoints = drawPoints;
	}

}
