package be.kuleuven.pylos;

import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.game.PylosSphere;

/**
 * Small wrapper class to provide a Move
 *
 * @author Pieter
 * @version 1.0
 */
public class Move
{
    private PylosLocation location;
    private PylosSphere sphere;

    public Move( PylosLocation location, PylosSphere sphere )
    {
        this.location = location;
        this.sphere = sphere;
    }

    public PylosLocation getLocation()
    {
        return location;
    }

    public void setLocation( PylosLocation location )
    {
        this.location = location;
    }

    public PylosSphere getSphere()
    {
        return sphere;
    }

    public void setSphere( PylosSphere sphere )
    {
        this.sphere = sphere;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Move move = (Move) o;

        if ( getLocation() != null ? !getLocation().equals( move.getLocation() ) : move.getLocation() != null )
            return false;
        return getSphere() != null ? getSphere().equals( move.getSphere() ) : move.getSphere() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getLocation() != null ? getLocation().hashCode() : 0;
        result = 31 * result + (getSphere() != null ? getSphere().hashCode() : 0);
        return result;
    }
}
