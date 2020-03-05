/*
Herve Ingende
Jacob Dangler
Erwin Joubert

CS4308: Concept of Programming Languages
Section W01
1st Project Deliverable
March 3rd, 2020
*/

import java.io.FileNotFoundException;

public class ScannerTest {

    public static void main(String[] args) {
        try {
            /*  Both files scanned and displayed successfully
                Please place filepath to file as String scl if
                path isn't found */
            String scl = "src/sclex1.scl";

            System.out.println("Start reading to scan file: " + scl + "\n");

            // Create a scanner using the input file
            SCLScanner sc = new SCLScanner(scl);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
