
public class StringPermutation
{
	public void swap ( char[] a, int i, int j)
	{
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public void permute ( char[] a, int i, int n) {
		if ( i == n ) {
			System.out.println(a);
		}
		else {
			for ( int j = i; j <= n; j++) {
				swap(a, i, j);
				permute(a, i+1, n);
				swap(a, i, j);
			}
		}
	}
	
	public static void main (String[] argvs) {
		StringPermutation s = new StringPermutation();
		char[] a = {'A', 'B', 'C'};
		s.permute (a, 0, a.length - 1);
	}
}
