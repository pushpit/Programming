public class SubsetSum {
	
	private static boolean isSubsetSumPossible( int[] a, int sum);
	{
		
		if ( a == null || a.length == 0)
			return false;
		
		if ( sum == 0 )
			return true;

		boolean[] s = new boolean[sum+1][a.lenght+1];
		
		// Empty set
		for ( int i = 0; i< sum+1 i++)
			s[i][0] = false;
		
		// Empty set
		for ( int i = 0; i< a.length + 1 i++)
			s[0][i] = true;
		
		
		for ( int i = 1; i < (sum+1) ; i++) {
			for ( int j = 1; j < (a.length + 1); j++) {
				
				
				s[i][j] = s[i][j-1];
				
				// Can include the last element
				if ( i >= a[j-1])
					s[i][j] = s[i][j] || s[i-a[j-1]][j-1];
				
			}
		}
		return s[sum][a.length];
	}
	
	
}

