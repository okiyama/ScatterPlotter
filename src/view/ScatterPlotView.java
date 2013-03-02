package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DataSet;

/**
 * ScatterPlot view for displaying a DataModel as a scatter plot.
 * Click to add points to the DataSet.
 * Click and drag to drag points around.
 * Press space to toggle displaying lines.
 * Press period to toggle displaying points.
 * @author Julian Jocque
 */
public class ScatterPlotView extends JPanel implements DataView, MouseListener, MouseMotionListener
{
	private ScatterPlotSettings settings;
	private DataSet dataset;
	private JFrame mainWindow;
    
    private static final int PADDING = 100;
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 800;
    
    /**
     * Default constructor, uses default values
     * @param setToUse The DataSet ScatterPlotView is listening to
     */
    public ScatterPlotView(DataSet setToUse) 
    {
    	dataset = setToUse;
    	mainWindow = new JFrame();
    	settings = new ScatterPlotSettings();
	}

	/**
	 * Updates this ScatterPlotView to reflect changes in data
	 */
	@Override
	public void update() 
	{
		repaint();
	}

	/**
	 * Generates the ScatterPlotView GUI then displays it to the screen
	 */
	public void createAndDisplay() 
	{
    	mainWindow.addMouseListener(this);
    	mainWindow.addMouseMotionListener(this);
    	mainWindow.addKeyListener(new ScatterPlotViewKeyListener(settings, this));
    	
	    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		mainWindow.setLayout(new BorderLayout());
	    mainWindow.getContentPane().add(this, BorderLayout.CENTER);
	    addButtons();
	    mainWindow.setFocusable(true);
	    mainWindow.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);  
	    mainWindow.setVisible(true);  
	}

	protected void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D graphics = (Graphics2D)g;
        
        drawAxes(graphics);
        drawLabels(graphics);
        if (settings.isDrawingLines())
        {
        	drawLines(graphics);
        }
        if (settings.isDrawingPoints())
        {
            drawPoints(graphics);
        }
        mainWindow.requestFocus();
    }

	/**
	 * Adds buttons to the frame
	 */
	private void addButtons() 
	{
		ScatterPlotActionListener buttonListener = new ScatterPlotActionListener(dataset, mainWindow);
		JPanel buttons = new JPanel(new BorderLayout());
		
		JPanel editingButtons = new JPanel();
		JButton changeXMin = new JButton("Set X-Min");
		changeXMin.addActionListener(buttonListener);
		changeXMin.setActionCommand("SET_X_MIN");
		JButton changeXMax = new JButton("Set X-Max");
		changeXMax.addActionListener(buttonListener);
		changeXMax.setActionCommand("SET_X_MAX");
		JButton changeYMin = new JButton("Set Y-Min");
		changeYMin.addActionListener(buttonListener);
		changeYMin.setActionCommand("SET_Y_MIN");
		JButton changeYMax = new JButton("Set Y-Max");
		changeYMax.addActionListener(buttonListener);
		changeYMax.setActionCommand("SET_Y_MAX");
		JButton changeXLabel = new JButton("Set X-Axis Label");
		changeXLabel.addActionListener(buttonListener);
		changeXLabel.setActionCommand("SET_X_LABEL");
		JButton changeYLabel = new JButton("Set Y-Axis Label");
		changeYLabel.addActionListener(buttonListener);
		changeYLabel.setActionCommand("SET_Y_LABEL");
		JButton changeTitle = new JButton("Set Title");
		changeTitle.addActionListener(buttonListener);
		changeTitle.setActionCommand("SET_TITLE");
		editingButtons.add(changeXMin);
		editingButtons.add(changeXMax);
		editingButtons.add(changeYMin);
		editingButtons.add(changeYMax);
		editingButtons.add(changeXLabel);
		editingButtons.add(changeYLabel);
		editingButtons.add(changeTitle);
		
		JPanel saveLoadButtons = new JPanel();
		JButton save = new JButton("Save");
		save.addActionListener(buttonListener);
		save.setActionCommand("SAVE");
		JButton load = new JButton("Load");
		load.addActionListener(buttonListener);
		load.setActionCommand("LOAD");
		saveLoadButtons.add(save);
		saveLoadButtons.add(load);
		
		buttons.add(editingButtons, BorderLayout.PAGE_START);
		buttons.add(saveLoadButtons, BorderLayout.PAGE_END);
		
		mainWindow.getContentPane().add(buttons, BorderLayout.PAGE_END);
	}

	/**
	 * Labels the axes and adds the title to the graph
	 * @param graphics Graphics we'll use to draw
	 */
	private void drawLabels(Graphics2D graphics) 
	{
		graphics.drawString(dataset.getTitle(), WINDOW_WIDTH/2, PADDING);
		graphics.drawString(dataset.getXLabel(), WINDOW_WIDTH/2, WINDOW_HEIGHT-((3*PADDING)/2));
		graphics.drawString(dataset.getYLabel(), 0, WINDOW_HEIGHT/2);
		graphics.drawString(dataset.getXMin().toString(), PADDING,getHeight()-(3*(PADDING/4)));
		graphics.drawString(dataset.getXMax().toString(), getWidth()-PADDING,getHeight()-(3*(PADDING/4)));
		graphics.drawString(dataset.getYMin().toString(), PADDING/4,getHeight()-(PADDING));
		graphics.drawString(dataset.getYMax().toString(), PADDING/4,PADDING);
	}

	/**
	 * Draws the points on to the scatter plot
	 * @param graphics The graphics component we are drawing to
	 */
	private void drawPoints(Graphics2D graphics) 
	{
		Double[][] data = dataset.getDataAsArray();
		int pointRadius = settings.getPointRadius();
		
		graphics.setPaint(settings.getPointsColor());
        for (int i = 0; i < data.length; i++)
        {
            int x = (int)(Math.round(xPointToPixels(data[i][0])));
            int y = (int)(Math.round(yPointToPixels(data[i][1])));
            graphics.fillOval(x-((3*pointRadius)/2), y-pointRadius, 2*pointRadius, 2*pointRadius);
        }
	}

	/**
	 * Draws the lines between the points
	 * @param graphics The graphics component we are drawing to
	 */
	private void drawLines(Graphics2D graphics) 
	{
		Double[][] data = dataset.getDataAsArray();
        for (int i = 0; i < data.length-1; i++)
        {
            int x1 = (int)(Math.round(xPointToPixels(data[i][0])));
            int y1 = (int)(Math.round(yPointToPixels(data[i][1])));
            int x2 = (int)(Math.round(xPointToPixels(data[i+1][0])));
            int y2 = (int)(Math.round(yPointToPixels(data[i+1][1])));
            graphics.drawLine(x1, y1, x2, y2);
        }
	}

	/**
	 * Draws the axes
	 * @param graphics Graphics component to draw them to
	 */
	private void drawAxes(Graphics2D graphics) 
	{
        int graphWidth = super.getWidth();
        int graphHeight = super.getHeight();
		graphics.drawLine(PADDING, PADDING, PADDING, graphHeight-PADDING);
        graphics.drawLine(PADDING, graphHeight-PADDING, graphWidth-PADDING, graphHeight-PADDING);
	}

	/**
	 * Converts an x data point to the corresponding x pixel on the graph
	 * @param point The point we are converting
	 * @return The pixel value of where this point should go
	 */
	private Double yPointToPixels(Double point) 
	{
        double yScale = (getHeight() - 2*PADDING) / (dataset.getYMax()-dataset.getYMin());
		return (getHeight()-PADDING) - (yScale * (point - dataset.getYMin()));
	}

	/**
	 * Converts an x data point to the corresponding x pixel on the graph
	 * @param point The point we are converting
	 * @return The pixel value of where this point should go
	 */
	private Double xPointToPixels(Double point) 
	{
        double xScale = (getWidth() - 2*PADDING) / (dataset.getXMax()-dataset.getXMin());
		return PADDING + (xScale * (point - dataset.getXMin()));
	}

	/**
	 * Given a Y value in pixels returns the nearest point value
	 * @param yPixel The pixel value to convert
	 * @return The given pixel value as a point value
	 */
	private Double yPixelsToPoint(int yPixel) 
	{
        double yScale = (getHeight() - 2*PADDING) / (dataset.getYMax()-dataset.getYMin());
        return dataset.getYMin() + (((getHeight()-((3*PADDING)/4))-yPixel) / yScale);
	}

	/**
	 * Given an X value in pixels returns the nearest point value
	 * @param xPixel The pixel value to convert
	 * @return The given pixel value as a point value
	 */
	private Double xPixelsToPoint(int xPixel) 
	{
        double xScale = (getWidth() - 2*PADDING) / (dataset.getXMax()-dataset.getXMin());
		return dataset.getXMin() + ((xPixel-PADDING) / xScale);
	}

	/**
	 * Finds the nearest point to the given x, y pixel values
	 * @param xPix X Value of what we want the nearest of
	 * @param yPix Y Value of what we want the nearest of
	 * @return An array of Doubles representing the nearest point found
	 */
	private Double[] findNearestPoint(int xPix, int yPix) 
	{
		Double xPoint = xPixelsToPoint(xPix);
		Double yPoint = yPixelsToPoint(yPix);
		
		boolean found = false;
		Double nearestDist = new Double(Double.MAX_VALUE);
		Double currentDist;
		int counter = 0;
		Double[] nearest = new Double[2];
		
		if (dataset.sizeOfData() != 0)
		{
			while (!found && counter != dataset.sizeOfData())
			{
				currentDist = distanceBetween(dataset.getDataAsArray()[counter][0], xPoint, 
						dataset.getDataAsArray()[counter][1], yPoint);
				if (currentDist < nearestDist)
				{
					nearestDist = currentDist;
					nearest = dataset.getDataAsArray()[counter];
				}
				else
				{
					found = true;
				}
				counter++;
			}
		}
		else
		{
			return new Double[]{0.0,0.0};
		}
		
		if (counter == dataset.sizeOfData()+1)
		{
			return dataset.getDataAsArray()[dataset.sizeOfData()-1];
		}
		else 
		{
			return nearest;
		}
	}

	/**
	 * Calculates the distance between two x, y points
	 * @param x1 x values of point1
	 * @param x2 x value of point2
	 * @param y1 y value of point1
	 * @param y2 y value of point2
	 * @return
	 */
	private Double distanceBetween(Double x1, Double x2,
			Double y1, Double y2) 
	{
		return Math.sqrt( (Math.pow(x2 - x1,2.0) + (Math.pow(y2 - y1,2.0))));
	}

	/**
	 * Checks whether the given x, y co-ordinate lies within the plot
	 * @param x x value to check
	 * @param y y value to check
	 * @return True if the co-ordinate is inside the plot, else false
	 */
	private boolean isInsidePlot(int x, int y) 
	{
		int leftSideX = PADDING;
		int rightSideX = getWidth()-PADDING;
		int bottomY = getHeight()-((3*PADDING)/4);
		int topY = 5*PADDING/4;
		
		return (x > leftSideX && x < rightSideX && y < bottomY && y > topY);
	}

	/**
	 * Deals with the mouse being dragged by dragging and dropping the nearest point
	 */
	@Override
	public void mouseDragged(MouseEvent event) 
	{
		int mouseX = event.getX();
		int mouseY = event.getY();
		
		boolean insidePlot = isInsidePlot(mouseX, mouseY);
		if (insidePlot)
		{
			Double[] nearest = findNearestPoint(mouseX, mouseY);
			dataset.remove(nearest[0], nearest[1]);
			dataset.add(xPixelsToPoint(mouseX),yPixelsToPoint(mouseY));
		}
	}

	/**
	 * Deals with the user clicking the mouse by adding a point.
	 */
	@Override
	public void mouseClicked(MouseEvent mouseClick) 
	{
		int mouseX = mouseClick.getX();
		int mouseY = mouseClick.getY();
		
		Double toAddX = xPixelsToPoint(mouseX);
		Double toAddY = yPixelsToPoint(mouseY);
		
		dataset.add(toAddX, toAddY);
	}

	/**
	 * Unused, necessary because we implement MouseListener
	 */
	@Override
	public void mouseExited(MouseEvent arg0) 
	{
	}

	/**
	 * Unused, necessary because we implement MouseListener
	 */
	@Override
	public void mousePressed(MouseEvent event) 
	{
	}

	/**
	 * Unused, necessary because we implement MouseListener
	 */
	@Override
	public void mouseReleased(MouseEvent event) 
	{
	}

	/**
	 * Unused, necessary because we implement MouseListener
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	}

	/**
	 * Unused, necessary because we implement MouseMotionListener
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {		
	}
}
