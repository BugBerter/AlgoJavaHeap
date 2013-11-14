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

	public void testExtract() {
		//Test on heap before it is sorted
		initialize();
		Proc temp = heap.removeMax();
		assertEquals(temp.getPriority(), 10);
		assertEquals(temp.getProcID(), 13);
		assertEquals(heap.getProc(0).getProcID(), 11);
		
		temp = heap.remove(heap.heapsize() - 1);
		assertEquals(temp.getPriority(), 8);
		assertEquals(temp.getProcID(), 21);
		
		//Test on a sorted heap
		initialize(); //reset the heap
		heap.buildheap();
		temp = heap.removeMax();
		assertEquals(temp.getPriority(), 100);
		assertEquals(temp.getProcID(), 9);
		assertEquals(heap.getProc(0).getProcID(), 11);
		
		temp = heap.remove(heap.heapsize() - 1);
		assertEquals(temp.getPriority(), 8);
		assertEquals(temp.getProcID(), 21);
	}
	
	public void testInsert() {
		initialize();
		
		heap.buildheap();
		
		boolean didInsert = heap.insert(new Proc(Integer.MAX_VALUE, 40)); //Proc with an ID that is too large
		assertFalse(didInsert); //Proc was not inserted
		
		didInsert = heap.insert(new Proc(Integer.MAX_VALUE, 10)); //Proc with ID that already exists
		assertFalse(didInsert); //Proc was not inserted
		
		didInsert = heap.insert(new Proc(Integer.MAX_VALUE, 1)); //Add valid Proc to the heap
		assertTrue(didInsert);
		//Make sure the heap property was maintained correctly
		assertEquals(heap.getProc(0).getPriority(), Integer.MAX_VALUE);
		
	}
	
	public void testWeightChange() {
		initialize();
		heap.buildheap();
		int temp;
		
		//This test consists of four cases:
		//1. Increase the weight of the root
		//This will not change the heap
		temp = heap.getProc(0).getProcID();
		heap.changeWeight(0, 200);
		assertEquals(heap.getProc(0).getProcID(), temp);
			//Make sure the root did not change
		
		//2. Decrease the weight of a leaf
		//This will not change the heap
		temp = heap.getProc(heap.heapsize() - 1).getProcID();
		heap.changeWeight(heap.heapsize() - 1, 0);
		assertEquals(heap.getProc(heap.heapsize() - 1).getProcID(), temp);
		
		//3. Increase the weight of a leaf to the largest value
		//This will make the leaf root and change the heap structure
		temp = heap.getProc((heap.heapsize() / 2)).getProcID();
		heap.changeWeight(heap.heapsize() / 2, Integer.MAX_VALUE);
		assertEquals(heap.getProc(0).getProcID(), temp);
		
		//4. Decrease the weight of a parent to the smallest value
		//This will make the parent a leaf node and change the heap
		temp = heap.getProc((heap.heapsize() - 1) / 2).getProcID();
		heap.changeWeight((heap.heapsize() - 1) / 2, Integer.MIN_VALUE);
		assertNotSame(heap.getProc((heap.heapsize() - 1) / 2).getProcID(), temp);
			//Makes sure the Proc is not in the same location as before
	}
	
	public void testEnqueue() {
		initialize();
		heap.buildheap();
		
		heap.enqueue(200, 5); //Will become the new root
		assertEquals(heap.getProc(0).getProcID(), 5);
		
		heap.enqueue(Integer.MIN_VALUE, 12); //Will become a leaf at index = heapsize() - 1
		assertEquals(heap.getProc(heap.heapsize() - 1).getProcID(), 12);
		
		heap.enqueue(25, 1); //Insert a middle value.
			//From by-hand calculations, will end up at index = 1
		assertEquals(heap.getProc(1).getProcID(), 1);
	}
	
	public void testDequeue() {
		initialize();
		heap.buildheap();
		int temp;
		
		//Will first dequeue Proc with ID = 9, priority = 100
		temp = heap.dequeue(); //Returns the process ID
		assertEquals(temp, 9);
		//Will contain Proc with ID = 11, priority = 30 as new root
		assertEquals(heap.getProc(0).getProcID(), 11);
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
}