package assignment03.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Format for words in a wordcloud.
 * @param word
 * @param relHeight: size of the word in the wordcloud
 */
public record WordCloudItem (String word, double relHeight) implements Comparable<WordCloudItem> {

    /**
     * From a list of words, this generates a list of WordCloudItems.
     * The relHeight of each WordCloudItem is dependent on the relative frequency of the word in @param words.
     */
    public static ArrayList<WordCloudItem> computeItems(ArrayList<String> words) {
        //will store words and their numbers of occurrence
        HashMap<String, Double> occurrences = new HashMap<>();
        double highestOccurrence = 0; //number of occurrences of the most abundant word

        //count occurrences of each word
        for (String word : words) {
            occurrences.put(word, occurrences.getOrDefault(word, 0.0) + 1);
        }
        //find the highest number of occurrence
        for (Double occurrence : occurrences.values()) {
            if (occurrence > highestOccurrence) {
                highestOccurrence = occurrence;
            }
        }
        //construct result (ArrayList of WordCloudItem, using the formula for rel. height)
        ArrayList<WordCloudItem> result = new ArrayList<>();
        for (String word : occurrences.keySet()) {
            result.add(new WordCloudItem(word, Math.sqrt(
                    occurrences.get(word) / highestOccurrence
            )));
        }
        //sort result
        Sorting<WordCloudItem> sorter = new Sorting<>();
        result = sorter.mergeSort(result);
        return result;
    }

    public int compareTo(WordCloudItem o) throws NumberFormatException, ClassCastException{
        return Double.compare(this.relHeight, o.relHeight);
    }

}
