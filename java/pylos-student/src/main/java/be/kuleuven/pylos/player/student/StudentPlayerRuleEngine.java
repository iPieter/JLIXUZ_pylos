package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.Move;
import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGameIF;
import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.game.PylosSphere;
import be.kuleuven.pylos.player.PylosPlayer;
import be.kuleuven.pylos.util.KnowledgeSessionHelper;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Ine on 25/02/2015.
 */
public class StudentPlayerRuleEngine extends PylosPlayer
{
    private static final Logger LOGGER = LoggerFactory.getLogger( StudentPlayerRuleEngine.class );

    private List <PylosLine>      lines;
    private List <PylosNeighbour> neighbours;

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
        if ( lines == null )
            generateLines( board );

        Map.Entry <Move, Integer> moveEntry = fireRules( game, board );

        Move move = null;
        //generate a random move if there is none from the rule engine
        if ( moveEntry == null )
        {
            List <PylosLocation> locations = Arrays.asList( board.getLocations() );

            Collections.shuffle( locations );
            PylosLocation location = locations.stream()
                    .filter( PylosLocation::isUsable )
                    .filter( l -> !game.moveSphereIsDraw( board.getReserve( this ), l ) )
                    .findFirst().get();

            move = new Move( location, board.getReserve( this ) );
        }
        else
        {
            move = moveEntry.getKey();
        }

        game.moveSphere( move.getSphere(), move.getLocation() );

    }

    @Override
    public void doRemove( PylosGameIF game, PylosBoard board )
    {
        Map.Entry <Move, Integer> moveEntry = fireRules( game, board );

        Move move = null;
        //generate a random move if there is none from the rule engine
        if ( moveEntry == null )
        {
            /* removeSphere a random sphere */
            Stream <PylosSphere> stream = Arrays.stream( board.getSpheres( this ) );
            Optional <PylosSphere> sphere = stream.filter( PylosSphere::canRemove )
                    .findFirst();

            move = new Move( null, sphere.get() );
        }
        else
        {
            move = moveEntry.getKey();
        }

        game.removeSphere( move.getSphere() );
    }

    @Override
    public void doRemoveOrPass( PylosGameIF game, PylosBoard board )
    {
        Map.Entry <Move, Integer> moveEntry = fireRules( game, board );

        Move move;

        //generate a random move if there is none from the rule engine
        if ( moveEntry == null )
        {
            /* removeSphere a random sphere */
            Stream <PylosSphere> stream = Arrays.stream( board.getSpheres( this ) );
            Optional <PylosSphere> sphere = stream.filter( PylosSphere::canRemove )
                    .findFirst();

            if ( sphere.isPresent() )
            {
                move = new Move( null, sphere.get() );
            }
            else
            {
                game.pass();
                return;
            }

        }
        else
        {
            move = moveEntry.getKey();
            if ( moveEntry.getValue() < 0 )
            {
                game.pass();
                return;
            }
        }

        game.removeSphere( move.getSphere() );


    }

    private void generateLines( PylosBoard board )
    {
        lines = new ArrayList <>();
        neighbours = new ArrayList <>();

        for (int j = 0; j < 2; j++)
        {
            PylosLine l1 = null; //first line for neighbours
            PylosLine l2 = null; //and the rotated second line

            for (int i = 0; i < 4; i++)
            {
                PylosLine l = new PylosLine( this.PLAYER_COLOR,
                        board.getBoardLocation( i, j + 0, 0 ),
                        board.getBoardLocation( i, j + 1, 0 ),
                        board.getBoardLocation( i, j + 2, 0 ) );
                lines.add( l );

                if ( l1 != null )
                    neighbours.add( new PylosNeighbour( l, l1 ) );

                l1 = l;

                l = new PylosLine( this.PLAYER_COLOR,
                        board.getBoardLocation( j + 0, i, 0 ),
                        board.getBoardLocation( j + 1, i, 0 ),
                        board.getBoardLocation( j + 2, i, 0 ) );

                lines.add( l );

                if ( l2 != null )
                    neighbours.add( new PylosNeighbour( l, l2 ) );

                l2 = l;
            }
        }

        for (int i = 0; i < 3; i++)
        {

            lines.add( new PylosLine( this.PLAYER_COLOR,
                    board.getBoardLocation( i, 0, 1 ),
                    board.getBoardLocation( i, 1, 1 ),
                    board.getBoardLocation( i, 2, 1 ) ) );

            lines.add( new PylosLine( this.PLAYER_COLOR,
                    board.getBoardLocation( 0, i, 1 ),
                    board.getBoardLocation( 1, i, 1 ),
                    board.getBoardLocation( 2, i, 1 ) ) );
        }
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
        if ( this.moveIntegerMap.containsKey( move ) )
            this.moveIntegerMap.put( move, this.moveIntegerMap.get( move ) + value );
        else
            this.moveIntegerMap.put( move, value );
    }

    private Map.Entry <Move, Integer> fireRules( PylosGameIF game, PylosBoard board )
    {
        //clear old list of moves
        this.moveIntegerMap = new HashMap <>();

        PylosLocation location = null;

        session = KnowledgeSessionHelper
                .getStatefulKnowledgeSession( kieContainer, "ksession-rules" );

        //insert this object itself as a receiver of Move facts
        session.setGlobal( "player", this );
        session.setGlobal( "ruleWeights", RuleWeights.getInstance() );

        session.insert( game );
        session.insert( board );

        lines.forEach( session::insert );
        neighbours.forEach( session::insert );
        Arrays.asList( board.getAllSquares() ).forEach( session::insert );

        session.fireAllRules();

        try
        {
            Map.Entry <Move, Integer> bestMove = moveIntegerMap.entrySet().stream()
                    .sorted( Map.Entry.comparingByValue( ( v1, v2 ) -> v2 - v1 ) )
                    .findFirst()
                    .get();

            session.dispose();

            LOGGER.info( "Destroying session, received {} moves. Selecting highest.", moveIntegerMap.size() );

            return bestMove;
        }
        catch ( NoSuchElementException ex )
        {
            LOGGER.warn( "No possible move received from rule engine." );
            //game.pass();
        }

        return null;
    }

    public void setSphere( PylosSphere sphere, Move move, int i )
    {
        move.setSphere( sphere );
        addMove( move, i );
    }
}