public class App {
    public static void main(String[] args) throws Exception {
        boolean[] initial13 = new boolean[13];
        boolean[] initial15 = new boolean[15];
        boolean[] initial17 = new boolean[17];
        int N = 192; 

        boolean[] z = new boolean[N];

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

        initial13 = getinit(13, LFSR13, z);
        initial15 = getinit(15, LFSR15, z);
        initial17 = getinit(17, LFSR17, z);

        System.out.println("13: " + initial13);
        System.out.println("15: " + initial15);
        System.out.println("17 " + initial17);

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

            int p = getP(L, init, LFSR, z);

            if(Math.abs(0.5 - p) > Math.abs(0.5 - currentP)) {
                currentP = p;
                highestP = init;
            }
        }

        return highestP;
    }

    private static int getP(int L, boolean[] init, boolean[] LFSR, boolean[] z){
        boolean[] sequence = getSequence(L, init, LFSR, z.length);

        int matches = 0;
        for(int i = 0; i<z.length; i++){
            if (sequence[i] == z[i]) matches++;
        }

        int p = 1 - matches/z.length;

        return p;
    }

    private static boolean[] getSequence(int L, boolean[] init, boolean[] LFSR, int N){
        boolean[] sequence = new boolean[N];
        // input the initial state
        System.arraycopy(init, 0, sequence, 0, init.length);

        int pos = init.length;

        while(pos<N){
            boolean result = false;
            for(int k = 0; k < L - 1; k++){
                result ^= (LFSR[k] && sequence[pos - L + 1 + k]);
            }
            sequence[pos] = result;
            pos++;
        }

        return sequence;
    }
}