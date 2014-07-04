public class MatrixSpiral {
	public void printSpiral ( int[][] m ) {
		int row = m.length;
		int col = row;
		int k = 0;
		int l = 0;
		int i = 0;
		
		while ( k < row && l < col ) {
			for ( i = l; i< col; ++i) {
				System.out.println(A[k][i]);
			}
			k++;
			
			for ( i = k ; i < row; ++i)
				System.out.println(A[i][col-1]);
			col--;
			
			if ( k < row ) { 
				for ( i = col - 1; i >= l;  --i)
					System.out.println(A[row - 1][i]);
				row--;
			}
			
			if ( l < col ) {
				for ( i = row - 1; i>= k; --i)
					System.out.println(A[i][l]);
				l++;
			}
		}
	}
}
				
		
