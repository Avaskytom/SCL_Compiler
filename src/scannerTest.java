import java.io.FileNotFoundException;
public class scannerTest {

        public static void main(String[] args) {
            try {
            /*  Both files scanned and displayed successfully
                Please place filepath to file as String scl if
                path isn't found */
                String scl = "src\\welcome2.scl";

                System.out.println("Start reading to scan file: " + scl + "\n");
                scanner sc = new scanner(scl);

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
