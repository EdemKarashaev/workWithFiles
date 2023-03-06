import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class ClientLog {
    StringJoiner strJ = new StringJoiner(" ")
            .add("productNum").add("amount");

    public void log(int productNum, int amount) {
        String val = Integer.toString(productNum);
        String val2 = Integer.toString(amount);
        strJ.add(val).add(val2);
    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            String[] str = strJ.toString().split(" ");
            String[] str2 = new String[str.length];
            for (int i = 0; i < str.length; i++) {
                if (i % 2 != 0) {
                    str2[i] = str[i] + "\n";
                } else str2[i] = str[i];
            }
            writer.writeNext(str2);
        }
    }
}
