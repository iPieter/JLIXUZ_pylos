package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.game.PylosPlayerColor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pieter
 * @version 1.0
 */
public class PylosLine
{
    private List <PylosLocation> locations;

    public PylosLine( List <PylosLocation> locations )
    {
        this.locations = locations;
    }

    public PylosLine( PylosLocation... locationArray )
    {
        locations = Arrays.asList( locationArray );
    }

    /**
     * Calculates the score, which is a distance metric to line locations.
     * <p>
     * Lower scores represent a line closer to the location.
     *
     * @param location The location to calculate the distance from.
     * @return An integer representing the score.
     */
    public int score( PylosLocation location )
    {
        int sum = 0;
        for (PylosLocation l : locations)
        {
            sum += manhattanDistance( l, location );
        }

        return sum;
    }

    /**
     *
     * @param board
     * @param color
     * @return
     */
    public int usedLocations( PylosBoard board, PylosPlayerColor color )
    {
        return (int) Arrays.stream( board.getSpheres() )
                .filter( s -> !s.isReserve() )
                .filter( s -> s.PLAYER_COLOR == color )
                .filter( s -> locations.contains( s.getLocation() ) )
                .count();
    }


    private static int manhattanDistance( PylosLocation l1, PylosLocation l2 )
    {
        return Math.abs( l1.X - l2.X ) +
                Math.abs( l1.Y - l2.Y );

    }
}
