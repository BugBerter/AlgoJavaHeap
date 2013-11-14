
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * This class visually displays a priority
 * queue in a graph format, and allows user
 * interaction to enqueue, dequeue, and change
 * the weight of elements
 * 
 * @author Austin Sorrells
 *
 */
public class VisualHeap extends JFrame {
	// Offset is used in creating unique ids for nodes
	final int offset = 100; 
	private static final long serialVersionUID = -2707712944901661771L;

	/**
	 * Constructor that draws a graph based upon a heap
	 * @param heap
	 */
	public VisualHeap(PriorityHeap heap) {
		// Sets the title of the window
		super("Priority Queue");

		// store the heap size
		int currentLength = heap.heapsize();
		// Initialize the graph
		mxGraph graph = new mxGraph();
		graphSettings(graph);

		// Set default node width
		int nodeWidth = 100;
		int nodeHeight = 30;

		// Start updating the graph
		graph.getModel().beginUpdate();
		mxCell firstNode = (mxCell) graph.getDefaultParent();
		try {
			// If the heap contains nodes, set the head initially
			if (currentLength > 0) {
				Proc firstProc = heap.getProc(0);
				firstNode = (mxCell) graph.insertVertex(
						graph.getDefaultParent(),
						new Integer(offset).toString(),
						"ID: " + firstProc.getProcID() + "\nPriority: "
								+ firstProc.getPriority(), 0, 0, nodeWidth,
						nodeHeight);
			}

			// loop through all of the parents, adding children nodes to the
			// graph as it goes
			for (int n = 0; n < Math.floor(currentLength / 2); n++) {

				// get the parent node from graph
				mxGraphModel gm = (mxGraphModel) graph.getModel();
				mxCell parent = (mxCell) gm.getCell(new Integer(n + offset)
						.toString());

				// Default priority is 0
				int priority = 0;

				// draw left child
				Integer leftChildIndex = heap.leftChild(n);
				if (leftChildIndex < currentLength) {
					Proc leftChild = heap.getProc(leftChildIndex);
					priority = leftChild.getPriority();
					
					// Sets the left vertex up, creates a unique ID using the index of the child
					Object leftV = graph.insertVertex(graph.getDefaultParent(), 
							Integer.toString(leftChildIndex + offset), 
							"ID: " + leftChild.getProcID() + "\nPriority: "
									+ priority, // Prints inside node
							0, 0, nodeWidth, nodeHeight); // Dimensions
					graph.insertEdge(parent, "edge" + Integer.toString(n),
							null, parent, leftV);
				}

				// draw right child
				Integer rightChildIndex = heap.rightChild(n);
				if (rightChildIndex < currentLength) {
					Proc rightChild = heap.getProc(rightChildIndex);
					priority = rightChild.getPriority();
					Object rightV = graph.insertVertex(
							graph.getDefaultParent(), // Set the parent
							Integer.toString(rightChildIndex + offset), // Unique
																		// ID
							"ID: " + rightChild.getProcID() + "\nPriority: "
									+ priority, // Prints inside node
							0, 0, nodeWidth, nodeHeight);// Dimensions
					graph.insertEdge(parent, "edge2" + Integer.toString(n),
							null, parent, rightV);
				}

			}
			
			// Creates a hierarchical structure and auto-formats the nodes
			mxHierarchicalLayout hl = new mxHierarchicalLayout(graph);
			hl.run(graph.getDefaultParent());

		} finally {
			// Stop updating the graph
			graph.getModel().endUpdate();
		}
		mxGraphComponent graphComponent = new mxGraphComponent(graph);

		// Create Swing Objects (Text Boxes and Buttons)

		if (firstNode.getGeometry() == null) // Checks case where firstNode does
												// not exist
			firstNode.setGeometry(new mxGeometry());


		JLabel priorityLabel = new JLabel("Priority");
		priorityLabel.setBounds((int) (50 + firstNode.getGeometry().getX() + firstNode.getGeometry().getWidth()),0, 50, 15);
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setBounds(priorityLabel.getX(), IDLabel.getY() + 50, 25, 15);
		JFormattedTextField priorityTextField = addTextField(priorityLabel.getX());
		JFormattedTextField IDTextField = addIDTextBox(IDLabel.getX());
		IDTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		JButton button = enqueueButton(heap, currentLength,
				priorityTextField.getX(), priorityTextField, IDTextField);
		JButton deqbutton = dequeueButton(heap, button.getX());
		JButton chngWeightButton = changeWeightButton(heap, button.getX(),
				priorityTextField, graph);

		// Add items to visible pane
		getContentPane().add(priorityTextField);
		getContentPane().add(IDTextField);
		getContentPane().add(IDLabel);
		getContentPane().add(priorityLabel);
		getContentPane().add(button);
		getContentPane().add(deqbutton);
		getContentPane().add(chngWeightButton);
		getContentPane().add(graphComponent);

	}

	/**
	 * Sets the desired attributes of the graph.
	 * 
	 * @param graph
	 *            Graph used in drawing
	 */
	private void graphSettings(mxGraph graph) {
		graph.setCellsEditable(false);
		graph.setCellsMovable(false);
		graph.setCellsResizable(false);
		graph.setEdgeLabelsMovable(false);
		graph.setAutoSizeCells(true);
		graph.setVertexLabelsMovable(false);
		graph.setConnectableEdges(false); // don't allow user to create edges
	}

	/**
	 * Button that when pressed, enqueues a new element in the heap
	 * with the priority value supplied in the priority text box
	 * 
	 * @param heap
	 *            Priority heap to be modified by enqueue
	 * @param currentSize
	 *            Current number of elements in the heap
	 * @param textFieldX
	 *            The x coordinate of the text box
	 * @param iDTextField
	 * 			Text field to contain integer value for priority
	 * @return
	 */
	public JButton enqueueButton(final PriorityHeap heap,
			final int currentSize, int textFieldX,
			final JFormattedTextField textField,
			final JFormattedTextField iDTextField) {
		JButton enqButton = new JButton("Enqueue");
		enqButton.setSize(100, 30);
		enqButton.setLocation(textFieldX + 100, 0);

		// Create a listener for button click
		enqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// if the heap is at maximum capacity
				if(heap.heapsize() >= heap.getMaxSize())
				{
					JOptionPane.showMessageDialog(null, "Cannot enqueue any more elements.");
					return;
				}

				// Check if user has entered priority and ID
				if(textField.getValue() == null || iDTextField.getValue() == null)
				{
					JOptionPane.showMessageDialog(null, "Must enter a priority and ID.");
					return;
				}
				
				// Parse the text fields to integer
				// Text formatter ensures there will be numbers only
				int priority = Integer.parseInt(textField.getValue().toString());
				int id = Integer.parseInt(iDTextField.getValue().toString());

				if(id >= heap.getMaxSize() || id < 0)
				{
					JOptionPane.showMessageDialog(null, "ID must be between 0 and " + ( heap.getMaxSize() -1 ) + "." );
				}
				
				heap.enqueue(priority, id);
				dispose(); // close the current window
				draw(heap); // open a new window with the
												// new heap
			}
		});
		return enqButton;
	}

	/**
	 * Button that, when pressed, dequeues the top-most element
	 * from the heap
	 * 
	 * @param heap heap to perform dequeue on
	 * @param enqueueTextFieldX x coordinate of the enqueue text field
	 * @return
	 */
	public JButton dequeueButton(final PriorityHeap heap, int enqueueTextFieldX) {
		JButton deqButton = new JButton("Dequeue");
		deqButton.setSize(100, 30);
		deqButton.setLocation(enqueueTextFieldX, +50);

		// Create a listener for button click
		deqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if(heap.heapsize() == 0)
				{
					JOptionPane.showMessageDialog(null, "Cannot dequeue any more nodes");
					return;
				}
				int deqID = heap.dequeue(); // get ID from dequeued element
				JOptionPane.showMessageDialog(null, "Dequeued element with ID: " + deqID);
				dispose(); // close the current window
				draw(heap); // open a new window with the
												// new heap
			}
		});
		return deqButton;
	}

	/**
	 * This button allows a user to change the priority of the selected node
	 * in the graph to the priority in the priority text box.
	 * 
	 * @param heap heap to perform change weight operation on
	 * @param enqueueButtonX X coordinate of the enqueue button
	 * @param priorityText text box that will contain the priority to use
	 * @param graph graph that is currently in use, to get selected node
	 * @return
	 */
	public JButton changeWeightButton(final PriorityHeap heap,
			int enqueueButtonX, final JFormattedTextField priorityText,
			final mxGraph graph) {
		JButton chngWeightButton = new JButton("Change Weight (Priority)");
		chngWeightButton.setSize(180, 30);
		chngWeightButton.setLocation(enqueueButtonX + 115, 0);

		// Create a listener for button click
		chngWeightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mxCell selectedCell = (mxCell) graph.getSelectionCell();
				
				// if the user has not selected a cell
				if (selectedCell == null) {
					JOptionPane.showMessageDialog(null,
							"Please Select a Node First");
					return;
				} else if (priorityText.getValue() == null) {

				} else {
					// IDs are unique (index + offset)
					int index = Integer.parseInt(selectedCell.getId()); 
					// remove this offset to get the correct index
					index = index - offset; 
					heap.changeWeight(index, Integer.parseInt(priorityText
							.getValue().toString()));
					dispose(); // close the current window
					draw(heap); // open a new window with the
													// new heap
				}

			}
		});
		return chngWeightButton;
	}

	/**
	 * Creates a textField for user to enter Priority
	 * @param labelX the x coordinate of the corresponding label
	 * @return
	 */
	public JFormattedTextField addTextField(int labelX) {
		JFormattedTextField textBox = new JFormattedTextField(createNumberFormat());
		textBox.setSize(80, 20);
		textBox.setLocation(labelX + 50, 0);
		return textBox;
	}

	/**
	 * Creates a textField for the user to enter a node ID
	 * @param labelX the x coordinate of the label 
	 * @return
	 */
	public JFormattedTextField addIDTextBox(int labelX) {
		JFormattedTextField textBox = new JFormattedTextField(createNumberFormat());
		textBox.setSize(80, 20);
		textBox.setLocation(labelX + 50, 50);
		return textBox;
	}

	/**
	 * initializes the window on the user's screen and calls
	 * the constructor to draw the graph
	 * 
	 * @param heap heap to draw as a graph
	 */
	public static void draw(PriorityHeap heap) {
		VisualHeap frame = new VisualHeap(heap);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.setVisible(true);
		
	}
	
	/**
	 * 
	 * @return Format that allows integer values only
	 */
	private NumberFormatter createNumberFormat()
	{
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter numFormat = new NumberFormatter(format);
		numFormat.setValueClass(Integer.class);
		numFormat.setMaximum(Integer.MAX_VALUE);
		numFormat.setMinimum(Integer.MIN_VALUE);
		numFormat.setCommitsOnValidEdit(true);
		return numFormat;
	}

}
