Homework 6

CSCI E-55

Nathan Hunt

2 December 2019

---

# Objectives

* Experiment with basic Hadoop jobs

# Solutions
### wordcount1
The `Text` object on line 28 of `WordCount.java` is just the current token that is being iterated over by the `StringTokenizer`. That is to say, each line of `mobydick.txt` is split into tokens (i.e. words) and each token is iterated over. 

The `Text` object is used to write to the `context` and increase the count by 1. 

### wordcount2
This wasn't too big of a change from `wordcount1`. Mostly, I had to change the type of the key in the `context`. The type signature of `MyMapper`'s `Mapper<>` went from `Object, Text, Text, IntWritable` in `wordcount1` to `Object, Text, IntWritable, IntWritable` in `wordcount2`. This indicates that the _length_ of the word (defined as an `IntWritable`) is being written to the `context`, rather than the word itself. To that end, I used an `IntWritable` called `length` and defined it like so:

```java
length.set(str.length());
context.write(length, one);
```
I also had to update `MyReducer` to accept an `IntWritable` rather than a `Text` object. Once that was done, I didn't have to change the `reduce` method at all (except for changing the type of `key` to also be `IntWritable`. 

Finally, I updated `job.setOutputKeyClass()` to be an `IntWritable` rather than a `Text` to account for the above changes. 

### seattle_library
This, too, did not involve too many changes from `wordcount2`. In `MyMapper.map()`, instead of tokenizing a line and iterating over each token, I simply followed Probal's example and split the line by commas. Then, I cleaned the title (found in index 6) slightly, and then wrote that to the `context`. 

After that, I could use the `MyReducer.reduce()` method from `wordcount1`, because nothing had changed (I was back to a Key/Value type of `Text`/`IntWritable`). 

_Note_: I experimented with only writing out the book title if it had been checked out more than once. I didn't end up using it in my final version, becuase it was unclear if it would have fulfilled the requirements. For now, it is commented out, at `SeattleBooks.java:46-52`.