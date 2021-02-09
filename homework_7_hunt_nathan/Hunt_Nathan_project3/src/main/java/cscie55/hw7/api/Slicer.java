package cscie55.hw7.api;

import java.util.List;

@FunctionalInterface
public interface Slicer<T, V, R> {
    
    R slice(T list, V start, V end);

}