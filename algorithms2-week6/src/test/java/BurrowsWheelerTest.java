import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BurrowsWheelerTest {
    private static final byte[] PLAIN = "ABRACADABRA!".getBytes();
    private static final byte[] ENCODED = new byte[]{
        0x00, 0x00, 0x00, 0x03, 0x41, 0x52, 0x44, 0x21, 0x52, 0x43, 0x41, 0x41, 0x41, 0x41, 0x42, 0x42
    };
    private InputStream in;
    private PrintStream out;

    @Before
    public void setup() {
        in = System.in;
        out = System.out;
    }

    @After
    public void cleanup() {
        System.setIn(in);
        System.setOut(out);
    }

    @Test
    public void testEncode() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(PLAIN);
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        BurrowsWheeler.encode();

        for (int i = 0; i < byteArrayOutputStream.toByteArray().length; i++) {
            System.err.print(String.format("%02X ", byteArrayOutputStream.toByteArray()[i]));
        }

        assertTrue(Arrays.equals(ENCODED, byteArrayOutputStream.toByteArray()));
    }
    
    @Ignore
    @Test
    public void testDecode() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ENCODED);
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        BurrowsWheeler.decode();

        System.err.println(new String(byteArrayOutputStream.toByteArray()));

        assertTrue(Arrays.equals(PLAIN, byteArrayOutputStream.toByteArray()));
    }
}