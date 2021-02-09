package cscie55.hw7.problem4;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class Anagramizer {
    
    private static Map<String, List<String>> buildAnagramMap(String line, Map<String, List<String>> aMap){
        String encoding = deriveCharacterKeyString(line);
        List<String> match = aMap.get(encoding);
        if(match == null) {
            List<String> tempList = new ArrayList<>();
            tempList.add(line);
            aMap.put(encoding, tempList);
        } else {
            match.add(line);
            aMap.put(encoding, match);
        }
        return aMap;
    }
    
    private static String deriveCharacterKeyString(String line){
        // normalize to lowercase and remove all non-alphabetic characters
        String newLine = line.toLowerCase();
        newLine = newLine.replaceAll("[^[A-z]]", "");
        
        // convert to sorted string
        String parsed = newLine.chars()
            .mapToObj(p -> (char)p)
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining());
        return parsed;
    }
    
    public static void main(String argv[]) {
        Map<String, List<String>> anagramMap = new HashMap<>();
        
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("anagram-data.txt"));
            while ((line = reader.readLine()) != null){
                anagramMap = buildAnagramMap(line, anagramMap);
            }
        } catch(Exception e) {
          System.out.println("file not found!");
          System.out.println(e);
          System.out.println("Working Directory = " + System.getProperty("user.dir"));
        }
        
        // filter matches by anagrams list > length 1
        anagramMap = anagramMap.entrySet()
            .stream()
            .filter(p -> p.getValue().size() > 1)
            .collect(Collectors.toMap(
                p -> p.getKey(),
                p -> p.getValue()
            ));
        
        List<String> textOutput = anagramMap.entrySet()
            .stream()
            .map(p -> p.getKey() + "->" + String.join(" ", p.getValue()))
            .collect(Collectors.toList());
            
        textOutput.forEach(System.out::println);
        
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            for(String line: textOutput.subList(textOutput.size()-20, textOutput.size())) {
                fileWriter.write(line + System.lineSeparator());
            }
            fileWriter.close();
        } catch(Exception e) {
            System.out.println("file can't be written!");
            System.out.println(e);
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
        }
        
        
        
        // Old solution involving purer focus on streams below--
        // MUCH SLOWER than my newer solution
        // List<String> uniques = anagrams.stream()
        //     .map(p -> p.get(1))
        //     .distinct()
        //     .sorted()
        //     .collect(Collectors.toList());
        // 
        // anagramMap = uniques.stream()
        //     .collect(Collectors.toMap(
        //         p -> p,
        //         p -> findMatches(p, anagrams)
        //     ));
        // 
        // 
        // List<List<String>> newResults = anagrams.stream()
        //     .map(p -> p.set(1, findMatches(p.get(0), anagrams)))
        //     .filter(p -> p.get(1).size > 1)
        //     .map(p -> p.set(1, String.join(",", p.get(1)) ) )
        //     .collect(Collectors.toList());
      
    }

}