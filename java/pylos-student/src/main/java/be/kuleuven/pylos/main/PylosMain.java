package be.kuleuven.pylos.main;

import be.kuleuven.pylos.battle.Battle;
import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGame;
import be.kuleuven.pylos.game.PylosGameObserver;
import be.kuleuven.pylos.player.PylosPlayer;
import be.kuleuven.pylos.player.PylosPlayerObserver;
import be.kuleuven.pylos.player.codes.PylosPlayerBestFit;
import be.kuleuven.pylos.player.codes.PylosPlayerMiniMax;
import be.kuleuven.pylos.player.codes.PylosPlayerRandomFit;
import be.kuleuven.pylos.player.student.RuleWeights;
import be.kuleuven.pylos.player.student.StudentPlayerRandomFit;
import be.kuleuven.pylos.player.student.StudentPlayerRuleEngine;
import com.sun.org.apache.bcel.internal.generic.POP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Jan on 15/11/2016.
 */
public class PylosMain
{

    public PylosMain()
    {

    }

    public static void main( String[] args )
    {

		/* !!! vm argument !!! -ea */

        if( args.length != 1 )
        {
            System.out.println( "USAGE: java -jar pylos.jar weights.txt" );
            System.exit( -1 );
        }

        System.out.println( args[0] );
        RuleWeights.getInstance().init( args[0] );

        //new PylosMain().startSingleGame();
		new PylosMain().startBattle();
    }

    public void startSingleGame()
    {
        Random random = new Random( 0 );

        PylosPlayer randomPlayerCodes   = new PylosPlayerBestFit();
        PylosPlayer randomPlayerStudent = new StudentPlayerRuleEngine();

        PylosBoard pylosBoard = new PylosBoard();
        PylosGame  pylosGame  = new PylosGame( pylosBoard, randomPlayerCodes, randomPlayerStudent, random, PylosGameObserver.CONSOLE_GAME_OBSERVER, PylosPlayerObserver.NONE );

        pylosGame.play();
    }

    public void startBattle()
    {
        Random random = new Random( 0 );

        List<List<Pair<String,Integer>>> population = new ArrayList <>();

        final int POP_SIZE = 40;

        for( int i = 0; i < POP_SIZE; i++ )
        {
            List<Pair<String,Integer>> entity = RuleWeights.getInstance().getAsList();

            entity.stream().forEach( stringIntegerPair -> { stringIntegerPair.setValue( random.nextInt( 201 ) - 100 ); } );

            population.add( entity );
        }

        List<double[]> results = new ArrayList <>();

        for( int i = 0; i < POP_SIZE; i++ )
        {
            System.out.println( i );
            RuleWeights.getInstance().setParams( population.get( i ));
            PylosPlayer playerLight = new PylosPlayerBestFit();
            PylosPlayer playerDark  = new StudentPlayerRuleEngine();
            double weights[] = Battle.play( playerLight, playerDark, 100 );
            results.add( weights );
        }

        for( int i = 0; i < POP_SIZE; i++ )
        {
            System.out.println( Arrays.toString( results.get( i ) ) );
        }

        //System.out.println( weights[1] );
    }

}
