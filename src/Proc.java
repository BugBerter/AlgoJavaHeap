public class Proc {

	private int priority;
	private int procID;
	private int heapIndex;

	public Proc() {
		priority = Integer.MIN_VALUE; /*
									 * This way new Proc objects will always
									 * have a default lowest priority which can
									 * be changed later
									 */
		procID = 0;
		heapIndex = -1; //Invalid value for a new Proc, to keep the process from overwriting any other locations
	}

	public Proc(int id) {
		priority = Integer.MIN_VALUE;
		procID = id;
		heapIndex = -1;
	}

	public Proc(int priority, int id) {
		this.priority = priority;
		this.procID = id;
	}

	public int getPriority() {
		return priority;
	}

	public int getProcID() {
		return procID;
	}
	
	public int getHeapIndex() {
		return heapIndex;
	}

	public void setPriority(int newPriority) {
		priority = newPriority;
	}

	public void setProcID(int newID) {
		procID = newID;
	}
	
	public void setHeapIndex(int newIndex) {
		heapIndex = newIndex;
	}
}