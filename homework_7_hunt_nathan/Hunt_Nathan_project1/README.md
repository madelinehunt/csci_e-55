Homework 7, problem 1

CSCI E-55

Nathan Hunt

16 December 2019

---

# Objectives

* Demonstrate facility with Java Streams and Predicates

# Solution steps
1. `beerQuery`

    This method needed to be modified to use `filter()` on the stream, and to pass the predicate in as the argument to `filter()`. Then, because the return type of this method specifies `List<Beer>`, I had to add `collect(Collectors.toList())` as the terminal operation on the stream. This meant, of course, that I also had to import `java.util.stream.Collectors`.
    
1. `priceRangeQuery`

    This was a simple matter of adding parameters to the method (`float lowerBound, float upperBound`) and returning the elements that were greater than the `lowerBound` but less than the `upperBound`.
    
3. `countryQuery`

    For this, I had to add a `getCountry()` method to the `Beer` class. It's a bit odd that this method didn't already exist, but that's fine. From there, all I had to do was match the elements where the country `equals()` the country being filtered for. In the `main()` method, I chose Belgium.
