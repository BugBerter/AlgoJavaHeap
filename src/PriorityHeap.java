/**
 * 
 * This class is an implementation of a priority-queue based upon max-heaps.
 * 
 * @author Austin Sorrells, Jared Watkins
 * 
 */
public class PriorityHeap {

	private Proc[] currentHeap; // heap structure
	private Proc[] procList; //list of proc objects sorted by ID
	private int heapsize; // max array size
	private int currentSize; // How many elem in array

	public static void main(String[] args) {
		Proc[] procs = { new Proc(1, 23), new Proc(20, 15) };
		PriorityHeap heap = new PriorityHeap(procs, 50);
		heap.buildheap();
		System.out.println(heap.toString());
		heap.enqueue(0, 100);
		System.out.println(heap.toString());
		heap.enqueue(5, 100);
		System.out.println(heap.toString());
		heap.dequeue();
		System.out.println(heap.toString());
		heap.dequeue();
		System.out.println(heap.toString());
		heap.increaseKey(1, 100);
		System.out.println(heap.toString());
		heap.decreaseKey(0, 19);
		System.out.println(heap.toString());
		heap.insert(new Proc(500000, 46));
		System.out.println(heap.toString());
	}

	/**
	 * Creates a new priority queue with default values. Default heapsize = 50.
	 */
	public PriorityHeap() {
		heapsize = 50;
		currentSize = 0;
		currentHeap = new Proc[heapsize];
		procList = new Proc[heapsize];
		for(int i=0; i<heapsize; i++)
			procList[i]=null;
	}

	/**
	 * 
	 * @param ls
	 *            the array of Procs to initially include in the heap
	 * @param maxSize
	 *            the maximum size that the heap can grow to be
	 */
	public PriorityHeap(Proc[] ls, int maxSize) {
		// Cannot initialize the heap if the array passed is bigger than allowed
		if (maxSize < ls.length) {
			System.err
					.println("Maximum size cannot be less than the size of the array provided!");
			return;
		}
		heapsize = maxSize;
		currentSize = ls.length;
		currentHeap = new Proc[heapsize];
		procList = new Proc[heapsize];
		for(int i=0; i<heapsize; i++)
			procList[i]=null;
		
		// Copies the values from the passed array to the array
		// of max size
		System.arraycopy(ls, 0, currentHeap, 0, currentSize);
		
		// Copy values from the heap into the proper position in the ordered list, making sure
		// that no two Proc objects have the same ID.
		for(Proc p : currentHeap) {
			if(p == null) { ; }
			else if(procList[p.getProcID()] != null)
				System.err.println("A process with that ID has already been created");
			else if(p.getProcID() >= heapsize)
				System.err.println("The ID of this process is too large");
			else
				procList[p.getProcID()] = p;
		}
	}

	/**
	 * The max-heapify function. Maintains the heap property.
	 * 
	 * @param parent
	 *            index of the position to begin heapifying
	 */
	private void siftdown(int parent) {
		int max;

		if (leftChild(parent) < currentSize
				&& currentHeap[leftChild(parent)].getPriority() > currentHeap[parent]
						.getPriority()) {
			max = leftChild(parent);
		} else {
			max = parent;
		}

		if (rightChild(parent) < currentSize
				&& currentHeap[rightChild(parent)].getPriority() > currentHeap[max]
						.getPriority()) {
			max = rightChild(parent);
		}

		if (max != parent) {
			swap(parent, max);

			siftdown(max); // Max-Heapify until parent in correct location in
						   // heap
		}
	}

	/**
	 * Swaps two elements in the heap
	 * 
	 * @param firstIndex
	 *            index of the first element to be swapped
	 * @param otherIndex
	 *            index of the second element to be swapped
	 */
	private void swap(int firstIndex, int otherIndex) {
		// Swaps two indices
		if(0 > firstIndex || 0 > otherIndex || firstIndex >= currentSize || otherIndex >= currentSize) { return; }
		
		Proc temp = currentHeap[firstIndex];
		temp.setHeapIndex(otherIndex);//Update the position of the process in the heap
		currentHeap[firstIndex] = currentHeap[otherIndex];
		currentHeap[firstIndex].setHeapIndex(firstIndex);//Update the position of the other process
		currentHeap[otherIndex] = temp;
	}
	
	
	/**
	 * 
	 * @return Current number of elements in the heap
	 */
	public int heapsize() {
		return currentSize;
	}

	/**
	 * 
	 * @param index
	 *            index of element in the heap to be checked
	 * @return true if element at index is a leaf in the heap; false otherwise
	 */
	public boolean isLeaf(int index) {
		if (leftChild(index) < currentSize)
			return false; // Has at least a left child, so cannot be a leaf node
		return true; // Has no children, so is a leaf node
	}
	//We could just use index to make the check rather than calling a new function

	/**
	 * 
	 * @param parent
	 *            index of the parent node in the heap
	 * @return the position of the left child of the given node
	 */
	public int leftChild(int parent) {
		return (parent * 2) + 1;
	}

	/**
	 * 
	 * @param parent
	 *            index of the parent node in the heap
	 * @return the position of the right child of the given node
	 */
	public int rightChild(int parent) {
		return (parent * 2) + 2;
	}

	/**
	 * 
	 * @param child
	 *            position of the node to find the parent of
	 * @return position of the parent of the given node, null if the param was the root node
	 */
	public int parent(int child) {
		return (((int) Math.floor((child - 1) / 2)) >= 0)? ((int) Math.floor((child - 1) / 2)) : -1 ;
	}

	/**
	 * 
	 * @param newProc
	 */
	public void insert(Proc newProc) {
		if(newProc.getProcID() > heapsize)
		if(procList[newProc.getProcID()] != null) {
			System.err.println("A process with that ID has already been created.");
		}
		procList[newProc.getProcID()] = newProc;
		// To insert, essentially sift the element up based on its priority
		currentHeap[currentSize++] = newProc;
		increaseKey(currentSize - 1, newProc.getPriority());
	}

	/**
	 * 
	 * @return element at the top of the heap
	 */
	public Proc removeMax() {

		// Put the last element into the root position and put the root in a
		// temporary variable
		Proc temp = currentHeap[0];
		temp.setHeapIndex(currentSize-1);
		currentHeap[0] = currentHeap[--currentSize];
		currentHeap[0].setHeapIndex(0);

		// Max-heapify, starting with the root
		siftdown(0);
		
		// Remove the process from the ordered list
		procList[temp.getProcID()] = null;

		// Return the original root object
		return temp;
	}

	/**
	 * 
	 * @param index
	 *            position of the node to be removed
	 * @return element removed from given position
	 */
	public Proc remove(int index) {
		if (index > currentSize) {
			System.out
					.println("ERROR: Invalid index value. Not that many values in the current heap");
			return null;
		}

		// Put the last element into the desired position and put the original
		// object in a temporary variable
		Proc temp = currentHeap[index];
		temp.setHeapIndex(currentSize-1);//0-based
		currentHeap[index] = currentHeap[--currentSize];
		currentHeap[index].setHeapIndex(index);

		procList[temp.getProcID()] = null;//remove the Proc from the ordered list
		
		// Max-heapify, starting with the removal index
		siftdown(index);

		// Return the desired object
		return temp;
	}

	/**
	 * Builds a max heap
	 */
	public void buildheap() {
		for (int i = (int) Math.floor(currentSize / 2) - 1; i >= 0; i--) {
			siftdown(i);
		}
	}

	/**
	 * 
	 * @param value
	 *            value of the element being enqueued
	 * @param priority
	 *            priority of the element being enqueued
	 */
	public void enqueue(int id, int priority) {
		// Error checking; Check if there is any room to insert a new object.
		if (currentSize >= heapsize) {
			System.err
					.println("Cannot enqueue any more nodes, already at maximum capacity!");
			return;
		}
		// If there is no error, continue with insert
		insert(new Proc(priority, id));

	}

	/**
	 * 
	 * @return the object id of the element with highest priority
	 */
	public int dequeue() {
		return removeMax().getProcID();
	}

	/**
	 * 
	 * @param index
	 *            index of the element to have its priority changed
	 * @param newPriority
	 *            value to change element's priority to
	 */
	public void changeWeight(int index, int newPriority) {
		if(index < 0 || index >= currentSize) { return; }
		
		if (newPriority > currentHeap[index].getPriority()) {
			increaseKey(index, newPriority);
		} else if (newPriority < currentHeap[index].getPriority()) {
			decreaseKey(index, newPriority);
		} else { ; }
	}

	private void increaseKey(int index, int newPriority) {

		if(index >= currentSize || index < 0) { return; }
		// Error handling; Should not happen if called from the change weight method
		// But still must be checked if this is called in an undesired manner
		if (currentHeap[index].getPriority() > newPriority) {
			System.err.println("New key smaller than current key");
			return;
		}
		currentHeap[index].setPriority(newPriority);
		// Sift the new element up until it is in the correct position
		while (index > 0
				&& currentHeap[parent(index)].getPriority() < newPriority) {
			swap(index, parent(index));
			index = parent(index);
		}
	}

	private void decreaseKey(int index, int newPriority) {
		if (index >= currentSize || index < 0) { return; }
		// Error handling; Should not happen if called from the change weight
		// method
		// But still must be checked if this is called in an undesired manner
		if (currentHeap[index].getPriority() < newPriority) {
			System.err.println("New key greater than current key");
			return;
		}
		// Otherwise change value and heapify to maintain heap property
		currentHeap[index].setPriority(newPriority);
		siftdown(index);
	}
	
	/**
	 * 
	 * @param index index of the desired Node
	 * @return Node at index given
	 */
	public Proc getProc(int index)
	{
		return currentHeap[index];
	}

	public String toString() {
		StringBuilder heapString = new StringBuilder();
		// iterate through each element in the current heap
		for (int n = 0; n < currentSize; n++) {
			heapString.append(currentHeap[n].getPriority() + "," + currentHeap[n].getProcID() + "\t");
		}
		// Removes last comma value if the string is non-empty
		return heapString.length() >= 1 ? heapString.substring(0,
				heapString.length() - 1) : "";
	}

}
