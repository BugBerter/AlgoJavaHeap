import junit.framework.TestCase;

public class PriorityHeapTests extends TestCase {
	
	private Proc[] procs;
	private int heapSize;
	private PriorityHeap heap;
	
	
	private void initialize() {
		Proc[] procList = { new Proc(10, 13), new Proc(5, 7), new Proc(20, 10), new Proc(100, 9), new Proc(8, 21), new Proc(30, 11) };
		procs = procList;
		heapSize = 25;
		heap = new PriorityHeap(procs, heapSize);
	}
	
	public void testInitialization() {
		initialize();
		
		Proc temp1 = heap.getProc(0);
		assertEquals(temp1.getPriority(), 10);
		assertEquals(temp1.getProcID(), 13);
		
		Proc temp2 = heap.getProc(heap.heapsize() - 1);
		assertEquals(temp2.getPriority(), 30);
		assertEquals(temp2.getProcID(), 11);
	}
	
	public void testSiftdown() {
		initialize();
	}
	
	public void testExtract() {
		initialize();
	}
	
	public void testInsert() {
		initialize();
	}
	
	public void testWeightChange() {
		initialize();
	}
	
	public void testEnqueue() {
		initialize();
	}
	
	public void testDequeue() {
		initialize();
	}
	
	public void testSwap() {
		initialize();
	}
	
	public void testHeapSize() {
		initialize();
		
		assertEquals(heap.heapsize(), 6);
		assertEquals(heap.getMaxSize(), 25);
	}
	
	public void testTree() {
		initialize();
		
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
		heap.buildheap();

		assertEquals(heap.getProc(0).getPriority(), 100);
		assertEquals(heap.getProc(0).getProcID(), 9);
		
		assertEquals(heap.getProc(heap.heapsize() - 1).getPriority(), 20);
		assertEquals(heap.getProc(heap.heapsize() - 1).getProcID(), 10);
	}
	
	public void testIncreaseKey() {
		initialize();
	}
	
	public void testDecreaseKey() {
		initialize();
	}
}