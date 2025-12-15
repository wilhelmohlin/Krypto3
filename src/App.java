import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        boolean[] initial13 = new boolean[13];
        boolean[] initial15 = new boolean[15];
        boolean[] initial17 = new boolean[17];

        // Read binary file task30.txt into boolean[] z
        String content = new String(Files.readAllBytes(Paths.get("task30.txt")));
        ArrayList<Boolean> zList = new ArrayList<>();
        for (char c : content.toCharArray()) {
            if (c == '0') {
                zList.add(false);
            } else if (c == '1') {
                zList.add(true);
            }
        }
        boolean[] z = new boolean[zList.size()];
        for (int i = 0; i < zList.size(); i++) {
            z[i] = zList.get(i);
        }


        //LFSR:s
        boolean[] LFSR13 = new boolean[] {
        // index:  0     1     2     3     4     5     6     7     8     9     10    11    12
                  true, true, false, true, false, true, true, false, false, true, true, false, true
        };
        boolean[] LFSR15 = new boolean[] {
        // index:  0     1     2     3     4     5     6     7     8     9     10    11    12    13    14
                  false, true, false, true, false, true, true, false, false, true, true, false, true, false, true
        };
        boolean[] LFSR17 = new boolean[] {
            // index:  0     1     2     3     4     5     6     7     8     9     10    11    12    13    14    15    16
                  false, true, false, true, true, false, false, true, false, true, false, false, true, false, false, true, true
        };

        initial13 = getinit(13, LFSR13, z);
        initial15 = getinit(15, LFSR15, z);
        initial17 = getinit(17, LFSR17, z);

        boolean[] initialState = new boolean[13 + 15 + 17];
        System.arraycopy(initial13, 0, initialState, 0, 13);
        System.arraycopy(initial15, 0, initialState, 13, 15);
        System.arraycopy(initial17, 0, initialState, 28, 17);

        // print initial states
        System.out.print("\nInitial State: ");
        for(boolean bit : initialState){
            System.out.print(bit ? "1" : "0");
        }

        boolean[] seq13 = getSequence(13, initial13, LFSR13, z.length);
        boolean[] seq15 = getSequence(15, initial15, LFSR15, z.length);
        boolean[] seq17 = getSequence(17, initial17, LFSR17, z.length);

        boolean[] combinedSeq = new boolean[z.length];

        for(int i = 0; i<z.length; i++){
            if((seq15[i] && seq17[i]) || (seq13[i] && seq17[i]) || (seq13[i] && seq15[i])){
                combinedSeq[i] = true;
            } else {
                combinedSeq[i] = false;
            }
        }

        // print combined sequence
        System.out.print("\nCombined Sequence: ");
        for(boolean bit : combinedSeq){
            System.out.print(bit ? "1" : "0");
        }

        // check if combinedSeq matches z
        boolean matches = true;
        for(int i = 0; i<z.length; i++){
            if(combinedSeq[i] != z[i]){
                matches = false;
                break;
            }
        }

        System.out.println("\nMatches: " + matches);

    }

    private static boolean[] getinit(int L, boolean[] LFSR, boolean[] z){
        boolean[] highestP = new boolean[L];
        double currentP = 0.5;

        // go over all initial states
        for(int i = 1; i < (1 << L); i++){
            // convert integer to boolean array
            boolean[] init = new boolean[L];
            for(int j = 0; j < L; j++){
                init[j] = (i & (1 << j)) != 0;
            }

            double p = getP(L, init, LFSR, z);

            if(Math.abs(0.5 - p) > Math.abs(0.5 - currentP)) {
                currentP = p;
                highestP = init;
            }
        }

        System.out.print("\nBest p for L=" + L + ": " + currentP );
        return highestP;
    }

    private static double getP(int L, boolean[] init, boolean[] LFSR, boolean[] z){
        boolean[] sequence = getSequence(L, init, LFSR, z.length);

        int matches = 0;
        for(int i = 0; i<z.length; i++){
            if (sequence[i] == z[i]) matches++;
        }

        double p = 1 - (double)matches / z.length;

        return p;
    }

    private static boolean[] getSequence(int L, boolean[] init, boolean[] LFSR, int N){
        boolean[] sequence = new boolean[N];
        // input the initial state
        System.arraycopy(init, 0, sequence, 0, init.length);

        int pos = init.length;

        while(pos<N){
            boolean result = false;
            for(int k = 0; k < L; k++){
                if(LFSR[k] && sequence[pos - 1 - k]) result = !result;
            }
            sequence[pos] = result;
            pos++;
        }

        return sequence;
    }
}