package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.DataSet;
import model.InvalidRangeException;

/**
 * Action Listener for a ScatterPlotView.
 * Handles button presses.
 * @author Julian Jocque
 *
 */
public class ScatterPlotActionListener implements ActionListener
{
	private DataSet dataset;
	private JFrame mainWindow;
	
	public ScatterPlotActionListener(DataSet dataset, JFrame frame) 
	{
		this.dataset = dataset;
		this.mainWindow = frame;
	}

	/**
	 * Deals with a button being pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent buttonAction) 
	{
		if (!(buttonAction.getActionCommand().equals("SAVE") || buttonAction.getActionCommand().equals("LOAD")))
		{
			String popUpResults = JOptionPane.showInputDialog("Change to what?");
			if (popUpResults != null)
			{
				if (buttonAction.getActionCommand().equals("SET_X_MIN"))
				{
					try {
						dataset.setXMin(Double.parseDouble(popUpResults));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindow, "You must enter a number with a decimal point.");
					} catch (InvalidRangeException e) {
						JOptionPane.showMessageDialog(mainWindow, "Invalid range, X-Min cannot be greater than X-Max");
					}
				}
				else if (buttonAction.getActionCommand().equals("SET_X_MAX"))
				{
					try {
						dataset.setXMax(Double.parseDouble(popUpResults));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindow, "You must enter a number with a decimal point");
					} catch (InvalidRangeException e) {
						JOptionPane.showMessageDialog(mainWindow, "Invalid range, X-Max cannot be less than X-Min");
					}
				}
				else if (buttonAction.getActionCommand().equals("SET_Y_MIN"))
				{
					try {
						dataset.setYMin(Double.parseDouble(popUpResults));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindow, "You must enter a number with a decimal point");
					} catch (InvalidRangeException e) {
						JOptionPane.showMessageDialog(mainWindow, "Invalid range, Y-Min cannot be greater than Y-Max");
					}
				}
				else if (buttonAction.getActionCommand().equals("SET_Y_MAX"))
				{
					try {
						dataset.setYMax(Double.parseDouble(popUpResults));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindow, "You must enter a number with a decimal point");
					} catch (InvalidRangeException e) {
						JOptionPane.showMessageDialog(mainWindow, "Invalid range, Y-Max cannot be less than Y-Min");
					}
				}
				else if (buttonAction.getActionCommand().equals("SET_X_LABEL"))
				{
					dataset.setXLabel(popUpResults);
				}
				else if (buttonAction.getActionCommand().equals("SET_Y_LABEL"))
				{
					dataset.setYLabel(popUpResults);
				}
				else if (buttonAction.getActionCommand().equals("SET_TITLE"))
				{
					dataset.setTitle(popUpResults);
				}
			}
		}
		else
		{
			if (buttonAction.getActionCommand().equals("SAVE"))
			{
				String popUpResults = JOptionPane.showInputDialog("Save to what name?");
				try {
					dataset.save(popUpResults);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(mainWindow, "Unable to save for some reason");
				}
			}
			else
			{
				String popUpResults = JOptionPane.showInputDialog("Load which? " +
						"All files are in the data folder.");
				try {
					dataset.load(popUpResults);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(mainWindow, "Unable to find a file by that name.");
				} catch (InvalidRangeException e) {
					JOptionPane.showMessageDialog(mainWindow, "That file contains an invaid range, " +
							"please try another or make your own now.");
				}
			}
		}
	}

}
