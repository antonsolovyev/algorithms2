import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String input = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(input);
        
        int first = 0;
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < circularSuffixArray.length(); i++) {
            if(circularSuffixArray.index(i) == 0) {
                first = i;
                output.append(input.charAt(input.length() - 1));    
            } else {
                output.append(input.charAt(circularSuffixArray.index(i) - 1));
            }
        }
                
        BinaryStdOut.write(first);
        BinaryStdOut.write(output.toString());
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String input = BinaryStdIn.readString();
        
//        System.err.println("first: " + first);
//        System.err.println("input: " + Arrays.asList(ArrayUtils.toObject(input.toCharArray())));
        
        int[] next = new int[input.length()];
        for(int i = 0; i < next.length; i++) {
            next[i] = i;
        }
        
        // key indexed counting
        int[] tmp = new int[input.length()];
        int[] count = new int[256 + 1];
        for (int i = 0; i < next.length; i++) {
            int c = input.charAt(i);
            count[c + 1]++;
        }

        // transform counts to indicies
        for (int r = 0; r < 256; r++) {
            count[r + 1] += count[r];
        }

        // distribute
        for (int i = 0; i < next.length; i++) {
            int c = input.charAt(i);;
            tmp[count[c]++] = next[i];
        }
        next = tmp;
        
//        System.err.println("next: " + Arrays.asList(next));

        StringBuilder output = new StringBuilder();
        int n = next[first];
        for(int i = 0; i < input.length(); i++) {
            output.append(input.charAt(n));
            n = next[n];
        }
//        System.err.println("output: " + Arrays.asList(ArrayUtils.toObject(output.toString().toCharArray())));
        
        BinaryStdOut.write(output.toString());
        BinaryStdOut.flush();        
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if(args.length != 1) {
            throw new IllegalArgumentException("Must be one argument, +/-");
        }

        switch (args[0].charAt(0)) {
            case '+':
                decode();
                break;
            case '-':
                encode();
                break;
            default:
                throw new IllegalArgumentException("Must be one argument, +/-");
        }
    }
}