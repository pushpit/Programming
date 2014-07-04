package com.pushpit.coding.concordance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class parse the input text file. And keep adding the words to the
 * wordsQueue for the consumption.
 * 
 * @author pushpit
 */
public class WordParser implements Runnable {

    private BlockingQueue<ParsedWord> wordsQueue;
    private String                    fileName;
    private int                       throughput;
    private AtomicInteger             currentSentenceNumber;

    public WordParser( String fileName, int throughput ) {
        this.throughput       = throughput;
        wordsQueue            = new ArrayBlockingQueue<ParsedWord>( this.throughput );
        this.fileName         = fileName;
        currentSentenceNumber = new AtomicInteger( 1 );
    }

    @Override
    public void run() {
        boolean testForSentenceChange = false;
        ParsedWord lastWordInLine     = null;
        BufferedReader br             = null;
        try {
            // Opening a file to read the data
            br = new BufferedReader( new FileReader( fileName ) );

            String line;

            // The loop will run till the lines are there in the file
            while( ( line = br.readLine() ) != null ) {

                if ( line.isEmpty() ) {
                    continue;
                }

                // Getting the current sentence number which is initialized to 1
                int currentSenNum = currentSentenceNumber.get();

                // Fetching the words in the current line
                String[] words = line.split( Constants.SPACES );
                for ( int i = 0; i< words.length; i++ ) {
                    String word = words[i];

                    // Removing all the punctuation except '.' & '-'
                    word = word.replaceAll( Constants.REM_PUNC_REGEX,
                                            Constants.EMPTY_STR );

                    if ( word.isEmpty() ) {
                        continue;
                    }

                    //
                    // Checking for the case in which the last word of the previous
                    // line contains a dot. If the last word of the previous line
                    // contains a dot, then for this line there might be a 
                    // sentence number change.
                    //
                    if ( i == 0 && testForSentenceChange
                            && lastWordInLine != null )
                    {
                        String lastWord = lastWordInLine.getWord();
                        //
                        // Checking the first word of the next line starts
                        // with an upper case letter than its a sentence change
                        //
                        if ( Character.isUpperCase( word.charAt( 0 ) ) ) {
                            lastWord =
                                    lastWord.replaceFirst( Constants.FULL_STOP,
                                                           Constants.EMPTY_STR );
                            currentSentenceNumber.addAndGet( 1 );
                            currentSenNum = currentSentenceNumber.get();
                        }

                        //
                        // putting the last word from the last sentence to the
                        // word queue
                        //
                        ParsedWord updatedLastWord =
                                new ParsedWord(
                                        lastWord,
                                        lastWordInLine.getSentenceNumber(),
                                        false );
                        addToQueue( updatedLastWord );

                        // Resetting the flags
                        testForSentenceChange = false;
                        lastWordInLine        = null;
                    }

                    //
                    // If last Character in the word is a dot, then check for
                    // sentence changes.
                    //
                    if ( word.charAt( word.length() - 1 ) == Constants.DOT ) {
                        //
                        // if this is the last word in the current line, then
                        // we need to read the next line to figure out the
                        // sentence changes.
                        //
                        if ( i == words.length - 1 ) {
                            testForSentenceChange = true;
                        }
                        else {
                            // Check if the next word starts with an upper case
                            // character.
                            String nextWord = words[ i+1 ];
                            if ( Character.isUpperCase( nextWord.charAt( 0 ) ) )
                            {
                                word = word.replaceFirst( Constants.FULL_STOP,
                                                          Constants.EMPTY_STR );
                                currentSentenceNumber.addAndGet( 1 );
                            }
                        }

                        ParsedWord pw =
                                new ParsedWord( word.toLowerCase(),
                                                currentSenNum,
                                                false );

                        if ( !testForSentenceChange ) {
                            addToQueue( pw );
                        }
                        else {
                            lastWordInLine = pw;
                        }
                    }
                    else {
                        ParsedWord pw =
                                new ParsedWord( word.toLowerCase(),
                                                currentSenNum,
                                                false );
                        addToQueue( pw );
                    }
                }
            }
        }
        catch ( FileNotFoundException e ) {
            System.out.println( "Unable to open file [" + fileName + "]" );
        }
        catch ( IOException ex ) {
            ex.printStackTrace();
        }
        finally {
            //
            // Checking and adding of there is any last word in the last line
            // and yet not added to the queue.
            //
            if ( testForSentenceChange && lastWordInLine != null ) {
                String lastWord = lastWordInLine.getWord();
                lastWord        = lastWord.replaceFirst( Constants.FULL_STOP,
                                                         Constants.EMPTY_STR );

                addToQueue(
                        new ParsedWord( lastWord,
                                        lastWordInLine.getSentenceNumber(),
                                        false ) );
            }

            // Adding a dummy ParsedWord to mark the end for the consumers
            addToQueue( ParsedWord.getEndParsedWord() );

            // Closing the buffered reader
            if ( br != null ) {
                try {
                    br.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ParsedWord getWordFromQueue() {
        ParsedWord word = null;
        
        try {
            word = wordsQueue.take();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return word;
    }
    private void addToQueue( ParsedWord pw ) {
        try {
            wordsQueue.put( pw );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
