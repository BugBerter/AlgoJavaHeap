/**
 * Proc is a node object that is stored in a priority queue.
 * It consists of a priority value, and an id value.
 * @author Austin Sorrells, Jared Watkins
 *
 */
public class Proc {

	private int priority;
	private int procID;
	private int heapIndex;

	/**
	 * Creates a new Proc object with default values for priority, procID, and heapIndex
	 */
	public Proc() {
		priority = Integer.MIN_VALUE; /*
									 * This way new Proc objects will always
									 * have a default lowest priority which can
									 * be changed later
									 */
		procID = 0; // Default ID value that will likely be occupied
		heapIndex = -1; // Invalid value for a new Proc, to keep the process from overwriting any used locations
	}

	/**
	 * Creates a new Proc object with the given ID
	 * 
	 * @param id
	 *            desired ID of the new object
	 */
	public Proc(int id) {
		priority = Integer.MIN_VALUE;
		procID = id;
		heapIndex = -1;
	}

	/**
	 * Creates a new Proc object with the given ID and priority
	 * 
	 * @param priority
	 *            desired priority of the new object
	 * @param id
	 *            desired ID of the new object
	 */
	public Proc(int priority, int id) {
		this.priority = priority;
		this.procID = id;
	}

	/**
	 * Presents the current priority value for the given Proc
	 * 
	 * @return Integer priority of the given Proc object
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Presents the current ID of the given Proc
	 * 
	 * @return Integer ID of the given Proc object
	 */
	public int getProcID() {
		return procID;
	}

	/**
	 * Presents the current heap index for the given Proc
	 * 
	 * @return Integer max-heap index of the given Proc object
	 */
	public int getHeapIndex() {
		return heapIndex;
	}

	/**
	 * Modifies the current priority value for the given Proc
	 * 
	 * @param newPriority
	 *          Updated priority of the Proc object
	 */
	public void setPriority(int newPriority) {
		priority = newPriority;
	}

	/**
	 * Modifies the current ID of the given Proc
	 * 
	 * @param newID
	 *          Updated ID value of the Proc object
	 */
	public void setProcID(int newID) {
		procID = newID;
	}

	/**
	 * Modifies the current heap index for the given Proc
	 * 
	 * @param newID
	 *          New heap location of the Proc object
	 */
	public void setHeapIndex(int newIndex) {
		heapIndex = newIndex;
	}
}
