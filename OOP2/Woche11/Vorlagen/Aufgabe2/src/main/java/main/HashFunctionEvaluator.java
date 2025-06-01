package main;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashFunctionEvaluator<K> {

    public static void main(String[] args) {
        HashFunctionEvaluator<Integer> evaluator = new HashFunctionEvaluator<>();
        /*
            Replace this with your own function HashFunctions::hashCode
         */
        evaluator.checkForCollisions(HashFunctions::hashCode, 4);
        /*
            Sha256 Hash function for cryptography
         */
//        HashFunctionEvaluator<String> shaEvaluator = new HashFunctionEvaluator<>();
//        shaEvaluator.checkForCollisions(s -> Hashing.sha256()
//                .hashString(s, StandardCharsets.UTF_8)
//                .toString());
    }

    public Map<K, Set<String>> checkForCollisions(Function<String, K> function, int maxWordLength) {
        var list = IntStream.rangeClosed(58, 122).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).boxed().collect(Collectors.toList());

        Map<K, Set<String>> map = new HashMap<>();
        Set<String> result = new HashSet<>();
        for (int i = 1; i < maxWordLength + 1; i++) {
            result(list, "", i, list.size(), result);
            for (String s : result) {
                K hash = function.apply(s);
                if (!map.containsKey(hash)) {
                    map.put(hash, new HashSet<>());
                }
                map.get(hash).add(s);
            }
        }
        long amountOfCollisions = map.entrySet().stream().filter(v -> v.getValue().size() > 1).count();
        System.out.println("The collisions are: ");
        int wordsWithCollisions = 0;
        K biggestCollisionHash = null;
        for (Map.Entry<K, Set<String>> entry : map.entrySet().stream().filter(v -> v.getValue().size() > 1).collect(Collectors.toList())) {
            System.out.println("The hash: " + entry.getKey() + " -> " + entry.getValue());
            wordsWithCollisions += entry.getValue().size();
            if (biggestCollisionHash == null || entry.getValue().size() > map.get(biggestCollisionHash).size()) {
                biggestCollisionHash = entry.getKey();
            }
        }
        System.out.println(result.size() + " Words were tested");
        System.out.println(amountOfCollisions + " hashes were calculated more than once");
        System.out.println(wordsWithCollisions + " Words have collisions");
        double collisions = wordsWithCollisions * 1.0 / result.size();
        System.out.println(collisions * 100 + "% collisions occurred");
        System.out.println("The Maximum number of collisions for one hash is " + map.get(biggestCollisionHash).size() + " for the hash " + biggestCollisionHash);
        return map;
    }

    public void result(List<Integer> record, String output, int n, int size, Set<String> result) {
        if (n == 0) {
            result.add(output);
            return;
        }
        for (int i = 0; i < size; ++i) {
            result(record, output + (char) (int) record.get(i), n - 1, size, result);
        }
    }
}
