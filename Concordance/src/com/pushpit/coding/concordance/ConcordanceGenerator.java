package com.pushpit.coding.concordance;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This is the main class. It accepts two parameters
 * 1. FileName: path to the document. This is a mandatory argument
 * 2. Throughput: Throughput for the WordParser
 * @author pushpit
 *
 */
public class ConcordanceGenerator {

    private String fileName;
    private int    throughput;

    private void doConcordance() {
        WordParser wp     = new WordParser( fileName, throughput );
        Concordance con   = new Concordance( wp );
        ExecutorService s = Executors.newFixedThreadPool( 1 );
        Future<?> f       = s.submit(con);

        try {
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        s.shutdown();
        con.printConcordance();
    }

    private String setUp( String[] args ) {
        StringBuilder error = new StringBuilder("");

        if ( args.length == 0 ) {
            error.append( "FileName missing !!\n" );
        }

        fileName = args[0];

        if ( args.length == 2 ) {
            throughput = Integer.parseInt( args[1] );
        }
        else {
            throughput = Constants.DEFAULT_THROUGHPUT;
        }

        return error.toString();
    }


    public static void main( String[] args ) {
        ConcordanceGenerator cg = new ConcordanceGenerator();
        String setUp = cg.setUp( args );
        if ( setUp == null || setUp.isEmpty() ) {
            cg.doConcordance();
        } else {
            System.out.println(
                    "Error occurred while setting up the concordance generator "
                            + setUp );
        }
    }
}
