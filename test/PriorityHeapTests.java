import junit.framework.TestCase;

public class PriorityHeapTests extends TestCase {
	
	private Proc[] procs;
	private int heapSize;
	
	
	private void initialize() {
		Proc[] procList = { new Proc(10, 13), new Proc(5, 7), new Proc(20, 10), new Proc(100, 9), new Proc(8, 21), new Proc(30, 11) };
		procs = procList;
		heapSize = 25;
	}
	
	public void testInitialization() {
		initialize();
		
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
		Proc temp1 = heap.getProc(0);
		assertEquals(temp1.getPriority(), 10);
		assertEquals(temp1.getProcID(), 13);
		
		Proc temp2 = heap.getProc(heap.heapsize() - 1);
		assertEquals(temp2.getPriority(), 30);
		assertEquals(temp2.getProcID(), 11);
	}
	
	public void testSiftdown() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testExtract() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testInsert() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testWeightChange() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testEnqueue() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testDequeue() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testSwap() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testHeapSize() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
		
		assertEquals(heap.heapsize(), 6);
		assertEquals(heap.getMaxSize(), 25);
	}
	
	public void testTree() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
		
		assertFalse(heap.isLeaf(0)); //root
		assertTrue(heap.isLeaf(heap.heapsize() - 1)); //last node
		assertFalse(heap.isLeaf((heap.heapsize() - 1) / 2)); //last parent
		assertTrue(heap.isLeaf((heap.heapsize() + 1) /2)); //first leaf
		
		assertEquals(heap.rightChild(0), 2);
		assertEquals(heap.leftChild(1), 3);
		assertEquals(heap.leftChild(0), 1);
		assertEquals(heap.rightChild(7), 16);
		
		assertEquals(heap.parent(5), 2);
		assertEquals(heap.parent(7), 3);
		assertEquals(heap.parent(0), -1);
		//test isLeaf, leftChild, rightChild, and parent
	}
	
	public void testBuildHeap() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
		heap.buildheap();

		assertEquals(heap.getProc(0).getPriority(), 100);
		assertEquals(heap.getProc(0).getProcID(), 9);
		
		assertEquals(heap.getProc(heap.heapsize() - 1).getPriority(), 20);
		assertEquals(heap.getProc(heap.heapsize() - 1).getProcID(), 10);
	}
	
	public void testIncreaseKey() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testDecreaseKey() {
		initialize();
		PriorityHeap heap = new PriorityHeap(procs, heapSize);
	}
}