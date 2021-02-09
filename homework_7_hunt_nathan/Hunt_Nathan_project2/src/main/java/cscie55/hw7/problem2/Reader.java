package cscie55.hw7.problem2;

import org.apache.commons.csv.*;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;


public class Reader {
    private Library library;
    
    public Reader(){
        this.library = Library.getInstance();
    }
    
    public void readAndAddToLibrary(String filepath){
        Path path = Paths.get(filepath);
        
        if(Files.exists(path)) {
            try {
                Checkout checkoutRecord;
                String author;
                String year;
                int yearInt;
                int nTimesCheckedOut;
                InputStream csvData = new FileInputStream(filepath);
                CSVParser parser = CSVFormat.DEFAULT.withHeader(
                        "Type",
                        "Holding",
                        "Format",
                        "Date Added",
                        "nCopies",
                        "Times Checked Out",
                        "Title",
                        "Author",
                        "Keywords",
                        "Publisher",
                        "Date Published"
                    )
                    .parse(new InputStreamReader(csvData)
                );
                for (CSVRecord csvRecord: parser) {
                    // strip out non-numerical characters from year field and truncate to only four number places
                    year = csvRecord.get("Date Published");
                    year = year.replaceAll("[^[0-9]]", "");
                    yearInt = year.length() > 1 ? Integer.parseInt(year.substring(0,4)) : 0;

                    // account for missing authors
                    author = csvRecord.get("Author").length() > 1 ? csvRecord.get("Author") : "no author";
                    
                    nTimesCheckedOut = Integer.parseInt(csvRecord.get("Times Checked Out"));
                    
                    
                    checkoutRecord = new Checkout(
                        csvRecord.get("Title"),
                        author,
                        yearInt,
                        nTimesCheckedOut,
                        csvRecord.get("Type")
                    );
                    library.addToCheckouts(checkoutRecord);
                    // System.out.println(checkoutRecord.toString());
                }
            } catch(Exception e) {
                System.out.println("\t\t\t\t*****error in CSV parsing!");
                System.out.println(e);
            }
        } else {
            System.out.println(path.toString());
            System.out.println("file " + filepath + " not found!");
            System.out.println("Working dir = " + System.getProperty("user.dir"));
            File workingDir = new File(System.getProperty("user.dir"));
            String[] filesInDir = workingDir.list();
            for(String s: filesInDir) {
                System.out.println(s);
            }
        }
    }
    
    
}