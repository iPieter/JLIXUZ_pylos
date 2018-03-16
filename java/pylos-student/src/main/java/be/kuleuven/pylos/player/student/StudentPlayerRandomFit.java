package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGameIF;
import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.game.PylosSphere;
import be.kuleuven.pylos.player.PylosPlayer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Ine on 5/05/2015.
 */
public class StudentPlayerRandomFit extends PylosPlayer
{

    @Override
    public void doMove( PylosGameIF game, PylosBoard board )
    {
        /* add a reserve sphere to a feasible random location */

        List <PylosLocation> locations = Arrays.asList( board.getLocations() );

        Collections.shuffle( locations );
        PylosLocation location = locations.stream()
                .filter( PylosLocation::isUsable )
                .filter( l -> !game.moveSphereIsDraw( board.getReserve( this ), l ) )
                .findFirst().get();


        game.moveSphere( board.getReserve( this ), location );
    }

    @Override
    public void doRemove( PylosGameIF game, PylosBoard board )
    {
        /* removeSphere a random sphere */
        Stream <PylosSphere> stream = Arrays.stream( board.getSpheres( this ) );
        Optional <PylosSphere> sphere = stream.filter( PylosSphere::canRemove )
                .findFirst();

        if ( sphere.isPresent() )
            game.removeSphere( sphere.get() );
        else
            game.pass();

    }

    @Override
    public void doRemoveOrPass( PylosGameIF game, PylosBoard board )
    {
        if (getRandom().nextBoolean())
            doRemove( game, board );
        else
            game.pass();
    }
}
