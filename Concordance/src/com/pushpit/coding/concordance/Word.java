package com.pushpit.coding.concordance;

import java.util.ArrayList;
import java.util.List;

public class Word implements Comparable<Word> {

    private String        word;
    private int           count;
    private List<Integer> sentenceNum;

    public Word( String word, int sentenceNumber ) {
        this.word   = word;
        count       = 1;
        sentenceNum = new ArrayList<Integer>();

        sentenceNum.add( sentenceNumber );
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> getSentenceNum() {
        return sentenceNum;
    }

    public void setSentenceNum( List<Integer> sentenceNum ) {
        this.sentenceNum = sentenceNum;
    }

    public void incrementCount() {
        count = count + 1;
    }

    public void addToSentenceList( int sentenceNum ) {
        this.sentenceNum.add( sentenceNum );
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder().append( word )
                                             .append( " {" )
                                             .append( count )
                                             .append( ":" );
        for ( Integer i : sentenceNum ) {
            s.append( i ).append( "," );
        }

        s.deleteCharAt( s.length() - 1 );
        s.append( "}" );
        return s.toString();
    }

    @Override
    public int compareTo( Word o ) {
        return this.word.compareTo( o.word );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((word == null) ? 0 : word.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Word other = ( Word ) obj;
        if ( word == null ) {
            if ( other.word != null )
                return false;
        } else if ( !word.equals( other.word ) )
            return false;
        return true;
    }
}
