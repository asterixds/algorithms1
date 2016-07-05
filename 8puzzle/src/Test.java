public class Test {
    public static void main(String[] args) {
       /* MaxPQ<Integer> pq = new MaxPQ<Integer>();
        //int[] a = { 98, 83, 71, 67, 61, 25, 30, 16, 27, 44 };
        int[] a = { 99,75, 78, 64, 67, 41, 65, 12, 63, 49};
        for (int i = 0; i < a.length; i++) {
            Integer key = new Integer(a[i]);
            pq.insert(key);
        }
        pq.insert(47);
        pq.insert(10);
        pq.insert(69);
        
        // print each key
        for (Integer s : pq)
            StdOut.print(s + " ");
        System.out.println();*/
        
        
        /*    RedBlackBST<Integer, Integer> st = new RedBlackBST<Integer, Integer>();
            int[] a = { 54 ,31 ,69 ,26, 46, 68, 85, 21, 28, 43, 53, 71, 18 };
            for (int i = 0; i < a.length; i++) {
                Integer key = new Integer(a[i]);
                st.put(key,i);
            }
            
            for (Integer s : st.keys())
                StdOut.println(s. + " " + st.get(s).isRed());
            StdOut.println();
        }
        */
        
        int N=100;
        int k=0;
        int sum = 0;
        for (int i = 1; i <= N; i = i*2)
            for (int j = 0; j < N; j++)
                k++;
        System.out.println(k);
    }
                
}
