import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MoveToFrontTest {
    private static final byte[] PLAIN = "ABRACADABRA!".getBytes();
    private static final byte[] ENCODED = new byte[]{0x41, 0x42, 0x52, 0x02, 0x44, 0x01, 0x45, 0x01, 0x04, 0x04, 0x02, 0x26};
    
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
    
    @Ignore
    @Test
    public void testEncode() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(PLAIN);
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        
        MoveToFront.encode();
        System.out.close();
        for (int i = 0; i < byteArrayOutputStream.toByteArray().length; i++) {
            System.err.print(String.format("%02X ", byteArrayOutputStream.toByteArray()[i]));
        }

        assertTrue(Arrays.equals(ENCODED, byteArrayOutputStream.toByteArray()));
    }

    @Ignore
    @Test
    public void testEncode2() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0xff, (byte) 0xff});
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        MoveToFront.encode();
        System.out.close();
        for (int i = 0; i < byteArrayOutputStream.toByteArray().length; i++) {
            System.err.print(String.format("%02X ", byteArrayOutputStream.toByteArray()[i]));
        }

        assertTrue(Arrays.equals(new byte[]{(byte) 0xff, (byte) 0x00}, byteArrayOutputStream.toByteArray()));
    }

    @Ignore
    @Test
    public void testDecode() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ENCODED);
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        MoveToFront.decode();
        
        System.err.println(new String(byteArrayOutputStream.toByteArray()));

        assertTrue(Arrays.equals(PLAIN, byteArrayOutputStream.toByteArray()));
    }

    @Ignore
    @Test
    public void testDecode2() throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0xff, (byte) 0x00});
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        MoveToFront.decode();

        assertTrue(Arrays.equals(new byte[]{(byte) 0xff, (byte) 0xff}, byteArrayOutputStream.toByteArray()));
    }
}