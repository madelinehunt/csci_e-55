package cscie55.hw7.problem2;

import java.util.List;
import java.util.ArrayList;

public class Checkout {
    private String title;
    private String author;
    private int publicationDate;
    private int nTimesCheckedOut;
    private enum Kind {PHYSICAL, DIGITAL, OTHER};
    private Kind kind;
    
    public Checkout(){
        // no argument constructor
    }
        
    public Checkout(String title, String author, int publicationDate, int nTimesCheckedOut, String kind){
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.nTimesCheckedOut = nTimesCheckedOut;
        this.kind = Kind.valueOf(kind.toUpperCase());
    }
    
    @Override
    public String toString(){
        return this.title + "---by " + this.author + "---published in " + Integer.toString(this.publicationDate) + "---of kind " + this.kind.name();
    }
    
    public boolean equals(Checkout otherCheckout){
        // using String comparison to decide if Checkout objects are equal
        if(this.toString().equals(otherCheckout.toString())) {
            return true;
        } else {
            return false;
        }
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getTimesCheckedOut(){
        return this.nTimesCheckedOut;
    }
    
}