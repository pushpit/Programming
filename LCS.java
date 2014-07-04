public class LCS {
	public int max ( int a, int b) {
		return (a>b) ? a: b;
	}
	public int lcs (String a, String b) {
		int m = a.length();
		int n = b.length();
		int[][] l = new int[m + 1][n + 1];
		for ( int i = 0; i <= m; i++ ) {
			for (int j = 0; j <= n; j++ ) {
				if ( i == 0 || j == 0 ) {
					l[i][j] = 0;
				}
				else if ( a.charAt(i -1) == b.charAt(j-1)) {
					l[i][j] = l[i-1][j-1] + 1;
				}
				else {
					l[i][j] = max(l[i-1][j], l[i][j-1]);
				}
			}
		}
		return l[m][n];
		
	}
	public static void main( String[] argvs ) {
		String x = "AGGTAB";
		String y = "GXTXAYB";
		LCS lcs = new LCS();
  
		System.out.println( "Length of LCS is " + lcs.lcs( x, y ) );
  
  	}
}
