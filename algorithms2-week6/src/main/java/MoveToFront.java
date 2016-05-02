import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] sequence = makeSequence();
        
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for(int i = 0; i < sequence.length; i++) {
                exchange(sequence, 0, i);
                if(sequence[0] == c) {
                    BinaryStdOut.write((char) i);
                    BinaryStdOut.flush();
                    break;
                }
            }
        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] sequence = makeSequence();
        
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(sequence[c]);
            BinaryStdOut.flush();
            for(int i = 1; i <= c; i++) {
                exchange(sequence, 0, i);
            }
        }
    }


    private static void exchange(char[] sequence, int i, int j) {
        char t = sequence[j];
        sequence[j] = sequence[i];
        sequence[i] = t;
    }


    private static char[] makeSequence() {
        char[] sequence = new char[256];

        for(int i = 0; i < sequence.length; i++) {
            sequence[i] = (char) i;
        }

        return sequence;
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
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