public class App {
    public static void main(String[] args) throws Exception {
        boolean[] initial13 = new boolean[13];
        boolean[] initial15 = new boolean[15];
        boolean[] initial17 = new boolean[17];
        int N = 192; 

        boolean[] z = readBooleanArray("task30.txt");

        //LFSR:s
        boolean[] LFSR13 = new boolean[] {
        // index:  0     1     2     3     4     5     6     7     8     9     10    11    12
            true, true, false, true, false, true, true, false, false, true,  true, false, true
        };
        boolean[] LFSR15 = new boolean[] {
        // index:  0     1     2     3     4     5     6     7     8     9     10    11    12    13    14
                false, true,  false, true,  false, true,  true,  false, false, true,  true,  false, true,  false, true
        };
        boolean[] LFSR17 = new boolean[] {
            // index:  0     1     2     3     4     5     6     7     8     9     10    11    12    13    14    15    16
                    false, true,  false, true,  true,  false, false, true,  false, true,  false, false, true,  false, false, true,  true
        };



        initial13 = getinit(13, LFSR13)

    }

    private static boolean[] getinit(int L, boolean[] LFSR, boolean[] z){
        boolean[] highestP = new boolean[L];
        int currentP = 0;

        // go over all initial states
        for(int i = 0; i < (1 << L); i++){
            // convert integer to boolean array
            boolean[] init = new boolean[L];
            for(int j = 0; j < L; j++){
                init[j] = (i & (1 << j)) != 0;
            }

            int p = getP(init, z);
        }

        return highestP;
    }
}