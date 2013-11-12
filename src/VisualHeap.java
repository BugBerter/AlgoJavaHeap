
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class VisualHeap extends JFrame {
	final int offset = 100;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;

	public VisualHeap(PriorityHeap heap, int currentLength) {
		// Give title
		super("Priority Queue");

		// set default middle value
		int currentX = 300;
		int currentY = 0;
		mxGraph graph = new mxGraph();

		graph.getModel().beginUpdate();
		mxCell firstNode = null;
		try {
			Proc firstProc = heap.getProc(0);
			firstNode = (mxCell)graph.insertVertex(graph.getDefaultParent(),
					new Integer(offset).toString(), 
					"ID: " + firstProc.getProcID() + "\n: " + firstProc.getProcID(),
					currentX, currentY, 50, 30);
			for (int n = 0; n < Math.floor(currentLength / 2); n++) {

				// get the parent node from graph
				mxGraphModel gm = (mxGraphModel) graph.getModel();
				mxCell parent = (mxCell) gm.getCell(new Integer(n + offset)
						.toString());
				currentX = (int) parent.getGeometry().getX();
				currentY = (int) parent.getGeometry().getY();

				int priority = 0;

				// draw left child
				Integer leftChildIndex = heap.leftChild(n);
				if(leftChildIndex < currentLength)
				{
				Proc leftChild = heap.getProc(leftChildIndex);
				priority = leftChild.getPriority();
				Object leftV = graph.insertVertex(
						graph.getDefaultParent(), // sets the parent
						Integer.toString(leftChildIndex + offset), // Unique id
						"ID: " + leftChild.getProcID() + "\nPriorty: " + priority, // Prints inside node
						0, 0, 50, 30); // Dimensions
				graph.insertEdge(parent, "edge" + Integer.toString(n), null, parent, leftV);
				}

				// draw right child
				Integer rightChildIndex = heap.rightChild(n);
				if (rightChildIndex < currentLength) {
					Proc rightChild = heap.getProc(rightChildIndex);
					priority = rightChild.getPriority();
					Object rightV = graph.insertVertex(
							graph.getDefaultParent(), // Set the parent
							Integer.toString(rightChildIndex + offset), // Unique ID
							"ID: " + rightChild.getProcID() + "\nPriorty: " + priority, // Prints inside node
							0, 0, 50, 30);// Dimensions
					graph.insertEdge(parent, "edge2" + Integer.toString(n), null, parent, rightV);
				}
				
			}
			mxHierarchicalLayout hl = new mxHierarchicalLayout(graph);
			hl.run(graph.getDefaultParent());

		} finally {
			graph.getModel().endUpdate();
		}
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(enqueueButton(heap, currentLength, (int)firstNode.getGeometry().getX()));
		getContentPane().add(graphComponent);
	}
	
	public JButton enqueueButton(final PriorityHeap heap, final int currentSize, int rootX)
	{
		JButton enqButton = new JButton("Enqueue");
		enqButton.setSize(100, 30);
		enqButton.setLocation(rootX + 150, 0);
		final int newSize = currentSize + 1;

		enqButton.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent evt) {
			dispose();
		    heap.enqueue(9000, 9000);
		    draw(heap, newSize);
		  }
		});
		return enqButton;
	}

	public static void draw(PriorityHeap heap, int currentSize) {
		VisualHeap frame = new VisualHeap(heap, currentSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 320);
		frame.setVisible(true);
	}

}
