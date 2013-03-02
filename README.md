Scatter Plotter
==============

**About**

Scatter Plotting software to learn Swing, JUnit and Document-View. To run, simple load up the project and run Client.java 
in the main package. When you do that it will open a window for the scatter plotter and a window for the table.
The table only displays information, it cannot edit the model in anyway.

**Features**  
* Can click to add points.  
* Points automatically draw lines between them.  
* Press space to toggle showing lines.  
* Press period to toggle showing points.
* Can modify the range of acceptable values for X and Y.  
* Modifying X, Y ranges changes what is displayed on the plot.  
* Clicking and dragging drags nearest point.  
* Can modify title of the plot.  
* Can modify labels for X and Y axes.  
* Saving and loading to a customized file format.  
* Table for showing data precisely.


**What I learned**  
* The Document-View design pattern is centrally used.  
* All data is stored in a DataSet model which is listened to by both ScatterPlotView and TableView.  
* JUnit syntax and testing methodology.  
* Swing components like buttons, frames, panels.  
* FileIO, learned to create a file format and parse it as well as write files in the proper formatting.  
