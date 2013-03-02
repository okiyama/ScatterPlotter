package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.DataSet;

/**
 * Table view for displaying a DataModel as a table.
 * @author Julian Jocque
 */
public class TableView implements DataView
{
	private DataSet data;
	private JFrame mainWindow;
	private JTable mainTable;
	private	JScrollPane scrollPane;
	
	/**
	 * Default constructor, creates an invisible window and an empty table.
	 * Uses the provided DataModel.
	 * @param dataToUse The data this table will observe.
	 */
	public TableView(DataSet dataToUse)
	{
		mainWindow = new JFrame();
		mainTable = new JTable();
		
		data = dataToUse;
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the GUI and displays it
	 */
	public void createAndDisplay()
	{
		mainWindow.setTitle(data.getTitle());
		mainWindow.setSize(300, 200);
		mainWindow.setLocation(1000, 100);
		mainWindow.setBackground(Color.gray);

		String columns[] = getColumnNames();
		Double[][] dataAsDoubles = data.getDataAsArray();
		String[][] rowData = new String[dataAsDoubles.length][2];
		for (int i = 0; i < dataAsDoubles.length; i++)
		{
			rowData[i][0] = dataAsDoubles[i][0].toString();
			rowData[i][1] = dataAsDoubles[i][1].toString();
		}
		mainTable = new JTable(rowData,columns);
		
		scrollPane = new JScrollPane(mainTable);
		mainWindow.add(scrollPane, BorderLayout.CENTER);
		
		mainWindow.setVisible(true);
	}
	
	private String[] getColumnNames() 
	{
		String[] columnNames;
		columnNames = new String[] { data.getXLabel(), data.getYLabel() };
		return columnNames;
	}

	@Override
	public void update() 
	{
		createAndDisplay();
	}

}
