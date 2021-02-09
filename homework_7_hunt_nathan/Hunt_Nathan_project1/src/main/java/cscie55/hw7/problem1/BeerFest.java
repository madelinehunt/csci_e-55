/* Adapted from code in "Java Programming", Chapter 20
   by Yakov Fain
 */
 package cscie55.hw7.problem1;
 
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Note: This class will report errors on opening.
 * That is because the TODOs are not complete statements
 * Your job is to complete them.
 */
public class BeerFest {
    public  static class Beer {
        public final String name;
        public final String country;
        private float price;

        public Beer(String name, String country,float price){
            this.name=name;
            this.country=country;
            this.price=price;
        }
        
        public String toString(){
            return "Country: " + country +  " Name: " + name + ", price: " + price;
        }
        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }
        public String getCountry(){
            return this.country;
        }
    }
    
    public static List<Beer> beerQuery(List<Beer> beerList, Predicate <Beer> predicate) {
        List<Beer> result = beerList.stream()
            .filter(predicate)
            .collect(Collectors.toList());
        return result;
    }
    static List<Beer> loadCellar(){
        List<Beer> beerStock = new ArrayList<>();

        beerStock.add(new Beer("Stella", "Belgium", 7.75f));
        beerStock.add(new Beer("Sam Adams", "USA", 7.00f));
        beerStock.add(new Beer("Obolon", "Ukraine", 4.00f));
        beerStock.add(new Beer("Bud Light", "USA", 5.00f));
        beerStock.add(new Beer("Yuengling", "USA", 5.50f));
        beerStock.add(new Beer("Leffe Blonde", "Belgium", 8.75f));
        beerStock.add(new Beer("Chimay Blue", "Belgium", 10.00f));
        beerStock.add(new Beer("Brooklyn Lager", "USA", 8.25f));

        return beerStock;
    }
    static Predicate<Beer> priceRangeQuery(float lowerBound, float upperBound) {
        return p -> p.getPrice() > lowerBound && p.getPrice() < upperBound;
    }
    static Predicate<Beer> countryQuery(String country) {
        return p -> p.getCountry().equals(country);
    }
    public static void main(String argv[]) {
    List<Beer> beerList = loadCellar();
    beerQuery(beerList, countryQuery("Belgium")).forEach(System.out::println);
    beerQuery(beerList, priceRangeQuery(4.00f, 8.00f)).forEach(System.out::println);
    }
}
