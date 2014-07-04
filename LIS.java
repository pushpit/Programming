public class LIS {
	public int lis ( int[] a) {
		int m = a.length;
		int[] l = new int[m+1];
		l[0] = 0;
		for ( int i = 0; i< m; i++) {
			l[ i+1 ] = 1;
			int max = 0;
			for ( int j = i-1; j>= 0; j--) {
				if ( a[j] < a[i] && max < l[ j + 1 ]) {
					max = l[ j + 1 ];
				}
			}
			l[i + 1] += max;
		}
		
		return l[m];
	}
	
	public static void main(String[] argvs) {
		LIS lis = new LIS();
		int[] a = {10, 22, 9, 33, 21, 50, 41, 60, 80};
		System.out.println("The length of LIS is: " + lis.lis(a));
	}
}
