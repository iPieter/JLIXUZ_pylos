package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.Move;
import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGameIF;
import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.player.PylosPlayer;
import be.kuleuven.pylos.util.KnowledgeSessionHelper;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Ine on 25/02/2015.
 */
public class StudentPlayerRuleEngine extends PylosPlayer
{
    private static final Logger LOGGER = LoggerFactory.getLogger( StudentPlayerRuleEngine.class );

    private List <PylosLine> lines;

    private Map <Move, Integer> moveIntegerMap;

    private KieSession session;

    private KieContainer kieContainer;

    public StudentPlayerRuleEngine()
    {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }


    @Override
    public void doMove( PylosGameIF game, PylosBoard board )
    {
        //clear old list of moves
        this.moveIntegerMap = new HashMap <>();

        PylosLocation location = null;

        session = KnowledgeSessionHelper
                .getStatefulKnowledgeSession( kieContainer, "ksession-rules" );

        //insert this object itself as a receiver of Move facts
        session.setGlobal( "player", this );

        session.insert( game );
        session.insert( board );

        session.fireAllRules();

        try
        {
            Move bestMove = moveIntegerMap.entrySet().stream()
                    .sorted()
                    .findFirst()
                    .get().getKey();

            session.dispose();

            LOGGER.info( "Destroying session, received {} moves. Selecting highest.", moveIntegerMap.size() );

            game.moveSphere( bestMove.getSphere(), bestMove.getLocation() );

        }
        catch ( NoSuchElementException ex )
        {
            LOGGER.error( "No possible move received from rule engine." );
            game.pass();
        }
    }

    private void generateLines( PylosBoard board )
    {
        lines = new ArrayList <>();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                lines.add( new PylosLine(
                        board.getBoardLocation( i, j + 0, 0 ),
                        board.getBoardLocation( i, j + 1, 0 ),
                        board.getBoardLocation( i, j + 2, 0 ) ) );

                lines.add( new PylosLine(
                        board.getBoardLocation( j + 0, i, 0 ),
                        board.getBoardLocation( j + 1, i, 0 ),
                        board.getBoardLocation( j + 2, i, 0 ) ) );
            }
        }

        for (int i = 0; i < 3; i++)
        {

            lines.add( new PylosLine(
                    board.getBoardLocation( i, 0, 1 ),
                    board.getBoardLocation( i, 1, 1 ),
                    board.getBoardLocation( i, 2, 1 ) ) );

            lines.add( new PylosLine(
                    board.getBoardLocation( 0, i, 1 ),
                    board.getBoardLocation( 1, i, 1 ),
                    board.getBoardLocation( 2, i, 1 ) ) );
        }
    }

    @Override
    public void doRemove( PylosGameIF game, PylosBoard board )
    {

    }

    @Override
    public void doRemoveOrPass( PylosGameIF game, PylosBoard board )
    {

    }

    public Map <Move, Integer> getMoveIntegerMap()
    {
        return moveIntegerMap;
    }

    public void setMoveIntegerMap( Map <Move, Integer> moveIntegerMap )
    {
        this.moveIntegerMap = moveIntegerMap;
    }

    public void addMove( Move move, int value )
    {
        this.moveIntegerMap.put( move, value );
    }
}