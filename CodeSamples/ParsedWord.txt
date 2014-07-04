package com.pushpit.coding.concordance;

public final class ParsedWord {

    private String  word;
    private int     sentenceNumber;
    private boolean end;

    public ParsedWord( String word, int sentenceNumber, boolean end ) {
        this.word           = word;
        this.sentenceNumber = sentenceNumber;
        this.end            = end;
    }

    public String getWord() {
        return word;
    }

    public int getSentenceNumber() {
        return sentenceNumber;
    }

    public boolean isEnd() {
        return end;
    }

    public static ParsedWord getEndParsedWord() {
        return new ParsedWord( null, 0, true );
    }
}
