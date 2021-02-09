Homework 7, problem 2

CSCI E-55

Nathan Hunt

16 December 2019

---

# Objectives

* Demonstrate facility with JUnit, Apache CSVParser, and file I/O

# Solution

I ended up choosing the ArrayList Collection in the `Library` singelton—it fit my needs well. I didn't need anything fancy, and it worked with will the streams and filtering by predicate I was doing.

# Output

In the `target` sub-directory after a clean maven test, there should be three text files:

* authorsBeginWithTest.txt
* publishedInYearTest.txt
* mostPopularTest.txt

These will be sorted lists of the output of `Checkout.toString()` after the testing filters. 

The tests I applied were (respectively):

* Author's name starts with "Sak"
* Year published is 1987
* Item has been checked 32 or more times 