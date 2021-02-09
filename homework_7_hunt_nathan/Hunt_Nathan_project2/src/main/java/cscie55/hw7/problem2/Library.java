package cscie55.hw7.problem2;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Comparator;

public class Library {
    private static Library library = new Library();
    private List<Checkout> checkouts;

    // private no-argument constructor
    private Library(){
        this.checkouts = new ArrayList<Checkout>();
    }
        
    public void addToCheckouts(Checkout checkoutToAdd){
        this.checkouts.add(checkoutToAdd);
    }
    
    public static Library getInstance(){
        return library;
    }
    
    public List<Checkout> getMostPopular(int limit){
        return this.getFilteredBy(popSearch(limit));
    }
    
    public List<Checkout> getAuthorsBeginsWith(String s){
        return this.getFilteredBy(authorsSearchStringBeginning(s));
    }
    
    public List<Checkout> getAllPublishedIn(int year){
        return this.getFilteredBy(yearSearch(year));
    }
    
    public List<Checkout> getFilteredBy(Predicate p){
        List<Checkout> result = (List<Checkout>) this.checkouts.stream()
            .filter(p)
            .collect(Collectors.toList());
        result.sort(Comparator.comparing(item -> item.getTitle()));
        return result;
    }
    
    private static Predicate<Checkout> authorsSearchStringBeginning(String s) {
        return p -> p.getAuthor() != "no author" && p.getAuthor().startsWith(s);
    }
    
    private static Predicate<Checkout> yearSearch(int year) {
        return p -> p.getPublicationDate() == year && p.getPublicationDate() != 0;
    }
    
    private static Predicate<Checkout> popSearch(int nTimes) {
        return p -> p.getTimesCheckedOut() > nTimes;
    }
    
}