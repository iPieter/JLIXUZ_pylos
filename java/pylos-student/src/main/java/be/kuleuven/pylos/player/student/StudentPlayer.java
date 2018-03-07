package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGameIF;
import be.kuleuven.pylos.player.PylosPlayer;

/**
 * Created by Jan on 20/02/2015.
 */
public class StudentPlayer extends PylosPlayer
{

    @Override
    public void doMove( PylosGameIF game, PylosBoard board )
    {
        /* player color reference
			* this
			* this.OTHER */
		
		/* random object
			* getRandom() */

		/* board methods
			* PylosLocation[] allLocations = board.getLocations();
			* PylosSphere[] allSpheres = board.getSpheres();
			* PylosSphere[] mySpheres = board.getSpheres(this);
			* PylosSphere myReserveSphere = board.getReserve(this); */

		/* location methods
			* location.isUsable();
			* location.getMaxInSquare(this.other)*/

		/* game methods
			* game.moveSphere(myReserveSphere, allLocations[0]); */
    }

    @Override
    public void doRemove( PylosGameIF game, PylosBoard board )
    {
		/* game methods
			* game.removeSphere(mySphere); */
    }

    @Override
    public void doRemoveOrPass( PylosGameIF game, PylosBoard board )
    {
		/* game methods
			* game.removeSphere(mySphere);
			* game.pass() */
    }
}
