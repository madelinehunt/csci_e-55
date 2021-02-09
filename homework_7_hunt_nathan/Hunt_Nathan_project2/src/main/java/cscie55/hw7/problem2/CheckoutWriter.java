package cscie55.hw7.problem2;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.nio.file.Files;

public class CheckoutWriter {
    
    public void write(Path filePathAndName, List<Checkout> checkouts){
        List<String> dataToWrite= (ArrayList<String>) checkouts.stream()
            .map(p -> p.toString())
            .distinct()
            .collect(Collectors.toList());
        try {
            Files.write(filePathAndName, dataToWrite, Charset.defaultCharset());
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
}