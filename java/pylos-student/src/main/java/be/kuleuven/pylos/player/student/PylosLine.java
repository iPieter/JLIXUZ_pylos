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
    private PylosPlayerColor     color;
    private List <PylosLocation> locations;
    private PylosLocation        middle;

    public PylosLine( List <PylosLocation> locations )
    {
        this.locations = locations;
    }

    public PylosLine( PylosPlayerColor color, PylosLocation... locationArray )
    {
        this.color = color;
        locations = Arrays.asList( locationArray );

        if ( locationArray.length % 2 == 1 )
        {
            middle = locations.get( locations.size() / 2 );
        }
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

    /* public methods --------------------------------------------------------------------------------------------- */

    /**
     * returns true if this line contains all the requires spheres
     *
     * @return
     */
    public boolean isFilledLine()
    {
        return getInLine() == locations.size();
    }

    public boolean isLineOwn()
    {
        return isLine( color );
    }

    public boolean isLineOther()
    {
        return isLine( color.other() );
    }


    /**
     * returns true if this line contains all the requiredspheres of 'color'
     *
     * @param color
     * @return
     */
    public boolean isLine( PylosPlayerColor color )
    {
        return getInLine( color ) == locations.size();
    }

    /**
     * returns the number of spheres in this line
     *
     * @return
     */
    public int getInLine()
    {
        return (int) locations.stream()
                .filter( PylosLocation::isUsed )
                .count();
    }

    public int getInLineOwn()
    {
        return getInLine( color );
    }

    public int getInLineOther()
    {
        return getInLine( color.other() );
    }

    public boolean isMMiddleOWn()
    {
        return middle.isUsed() && middle.getSphere().PLAYER_COLOR == color;
    }

    public boolean isMMiddleOther()
    {
        return middle.isUsed() && middle.getSphere().PLAYER_COLOR == color.other();
    }

    public PylosLocation getMiddle()
    {
        return middle;
    }

    public boolean isEmptyMiddle()
    {
        return middle != null && !middle.isUsed();
    }

    /**
     * returns the number of spheres of 'color' in this square
     *
     * @param color
     * @return
     */
    public int getInLine( PylosPlayerColor color )
    {
        return (int) locations.stream()
                .filter( l -> l.isUsed() && l.getSphere().PLAYER_COLOR == color )
                .count();
    }

    /**
     * returns an list containing the 4 locations in this square
     *
     * @return
     */
    public List <PylosLocation> getLocations()
    {
        return locations;
    }

    /**
     * @param board
     * @param color
     * @return
     * @deprecated
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
