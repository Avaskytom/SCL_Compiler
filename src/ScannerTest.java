import java.io.FileNotFoundException;

public class ScannerTest {

    public static void main(String[] args) {
        try {
            /*  Both files scanned and displayed successfully
                Please place filepath to file as String scl if
                path isn't found */
            String scl = "src/sclex1.scl";

            System.out.println("Start reading to scan file: " + scl + "\n");
            SCLScanner sc = new SCLScanner(scl);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
