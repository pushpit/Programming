package com.pushpit.coding.concordance;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This class build the concordance for the data parsed by the wordParser.
 * The class maintains a TreeMap to hold the data
 * @author pushpit
 *
 */
public class Concordance implements Runnable {

    private WordParser wordParser;

    private Map<String, Word> concordanceMap;

    public Concordance ( WordParser wordParser ) {
        this.wordParser = wordParser;
        concordanceMap  = new TreeMap<String, Word>();
    }

    @Override
    public void run() {
        ExecutorService s = Executors.newFixedThreadPool(1);
        s.submit( wordParser );

        while ( true ) {
            ParsedWord pw = wordParser.getWordFromQueue();

            if ( pw.isEnd() ) {
                // return if the last word is reached
                break;
            }

            // Fetching the data from the map
            Word wordInMap = concordanceMap.get( pw.getWord() );

            if ( wordInMap != null ) {
                //
                // if the word already encountered, increment the count and
                // append the sentence number
                //
                wordInMap.incrementCount();
                wordInMap.addToSentenceList( pw.getSentenceNumber() );
            }
            else {
                // Create a new word and put it in the map
                wordInMap = new Word( pw.getWord(), pw.getSentenceNumber());
                concordanceMap.put( wordInMap.getWord(), wordInMap);
            }
        }

        s.shutdown();
    }

    /**
     * Prints the concordance. This method should be called once the task in
     * run method is finished
     */
    public void printConcordance () {
        Integer i = 1;
        for ( Map.Entry<String, Word> entry : concordanceMap.entrySet() ) {
            System.out.println( i.toString() + ". " + entry.getValue() );
            i++;
        }
    }
}
