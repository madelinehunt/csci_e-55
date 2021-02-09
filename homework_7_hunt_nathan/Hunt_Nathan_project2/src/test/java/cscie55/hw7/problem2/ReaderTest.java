package cscie55.hw7.problem2;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReaderTest {
    Library library;
    Reader reader;
    CheckoutWriter writer;
    
    @Before
    public void setup(){
        this.library = Library.getInstance();
        this.reader = new Reader();
        this.writer = new CheckoutWriter();
        reader.readAndAddToLibrary("smaller_seattle.csv");
    }
    
    @Test
    public void authorsBeginWithTest(){
        List<Checkout> matches = library.getAuthorsBeginsWith("Sak");
        Path writeLocation = Paths.get("target/authorsBeginWithTest.txt");
        writer.write(writeLocation, matches);
    }
    
    @Test
    public void publishedInTest(){
        List<Checkout> matches = library.getAllPublishedIn(1987);
        Path writeLocation = Paths.get("target/publishedInYearTest.txt");
        writer.write(writeLocation, matches);
    }
    
    @Test
    public void mostPopularTest(){
        List<Checkout> matches = library.getMostPopular(32);
        Path writeLocation = Paths.get("target/mostPopularTest.txt");
        writer.write(writeLocation, matches);
    }
}