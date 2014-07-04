public class BubbleSort {

	public static int[] sort( int[] a) {	
		for ( int i = 0; i < a.length; i++ ) {
			for ( int j = 0; j < a.length - i - 1; j++) {
				if ( a[j] > a[j+1] ) {
					swap ( a[j], a[j+1] );
				}
			}
		}
		
		return a;
	}
}
