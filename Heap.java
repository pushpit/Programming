public class Heap {
	private int[] num;

	private Heap() {
	}
	
	private int getLeftChildIndex(int i) {
		return 2*i + 1;
	}
	
	
	private int getRightChildIndex(int i) {
		return 2*i + 2;
	}
	
	private int getParentIndex(int i) {
		return i/2;
	}
	public static Heap getHeap(int[] a) {
		Heap h = new Heap();
		num    = a;		
	}
}
	
	
	
