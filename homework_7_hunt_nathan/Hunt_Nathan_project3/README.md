Homework 7, problem 3

CSCI E-55

Nathan Hunt

16 December 2019

---

# Objectives

* Demonstrate facility with Java Functional Interfaces, as well as Collectors and more Streams.

# Solution
This one was frustrating. The instructions and code had quite a few typos ("getVegetatianMenu"?) and unclear instructions. Too, the `pom.xml` file was missing the dependencies for `com.fasterxml.jackson.core`, `org.apache.logging.log4j`, and `org.apache.logging.log4j`, so I had to add those to get anything to work. 

In the end, I followed the advice in Prof. Evers announcement about `slice` and had TakeOutShop implement the `Slicer` functional interface. From there, it was a simple matter to use the `slice` method to return a `List<Dish>`, using the `subList` method to handle the list slicing. `getVegetarianMenu()` and `partitionByCalorieLimit()` were also straightforward enough to write with streams, using `filter()` and `collect(Collectors.partitioningBy())`, respectively. 

I also looked to `testReadStreamToJson()` and adapted its code for use in the `TakeOutShop` constructor, to read the array of dishes form S3. This was a very helpful example to work from, and I ended up not having to change it very much. 