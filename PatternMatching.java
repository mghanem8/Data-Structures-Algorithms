import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various pattern matching algorithms.
 *
 * @author Mohamed Ghanem
 * @version 1.0
 * @userid mghanem8
 * @GTID 903533880
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Brute force pattern matching algorithm to find all matches.
     *
     * You should check each substring of the text from left to right,
     * stopping early if you find a mismatch and shifting down by 1.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> bruteForce(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot pattern match null or empty pattern.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Cannot pattern match with null comparator.");
        } else if (text == null) {
            throw new IllegalArgumentException("Cannot pattern match null text.");
        }
        List<Integer> matches = new LinkedList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        for (int j = 0; j <= text.length() - pattern.length(); j++) {
            int i = 0;
            while (i < pattern.length()) {
                if (comparator.compare(pattern.charAt(i), text.charAt(i + j)) == 0) {
                    i++;
                    if (i >= pattern.length()) {
                        matches.add(j);
                    }
                } else {
                    i = pattern.length();
                }
            }
        }
        return matches;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Cannot build failure table with null pattern.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Cannot pattern match with null comparator.");
        }
        int[] failureTable = new int[pattern.length()];
        if (failureTable.length == 0) {
            return failureTable;
        }
        failureTable[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j++] = ++i;
            } else {
                if (i == 0) {
                    failureTable[j++] = 0;
                } else {
                    i = failureTable[i - 1];
                }
            }
        }
        return failureTable;
    }


    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this
     * method. The amount to shift by upon a mismatch will depend on this table.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot pattern match null or empty pattern.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Cannot pattern match with null comparator.");
        } else if (text == null) {
            throw new IllegalArgumentException("Cannot pattern match null text.");
        }
        List<Integer> matches = new LinkedList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int[] table = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (text.length() - j >= pattern.length() - i) {
            if (comparator.compare(pattern.charAt(i), text.charAt(j)) == 0) {
                i++;
                j++;
                if (i == pattern.length()) {
                    matches.add(j - i);
                    i = table[i - 1];
                }
            } else {
                if (i == 0) {
                    j++;
                } else {
                    i = table[i - 1];
                }
            }
        }
        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Cannot build last table with null pattern.");
        }
        Map<Character, Integer> table = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *

     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Cannot pattern match null or empty pattern.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Cannot pattern match with null comparator.");
        } else if (text == null) {
            throw new IllegalArgumentException("Cannot pattern match null text.");
        }
        List<Integer> matches = new LinkedList<Integer>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        Map<Character, Integer> table = buildLastTable(pattern);
        int j = 0;
        while (j <= text.length() - pattern.length()) {
            int i = pattern.length() - 1;
            while (i >= 0 && comparator.compare(pattern.charAt(i), text.charAt(i + j)) == 0) {
                i--;
            }
            if (i == -1) {
                matches.add(j++);
            } else {
                int shift = table.getOrDefault(text.charAt(i + j), -1);
                if (shift < i) {
                    j = i + j - shift;
                } else {
                    j++;
                }
            }
        }
        return matches;
    }
}
