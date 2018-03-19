package be.kuleuven.pylos.player.student;

import be.kuleuven.pylos.game.PylosBoard;
import be.kuleuven.pylos.game.PylosGameIF;
import be.kuleuven.pylos.game.PylosLocation;
import be.kuleuven.pylos.game.PylosSphere;
import be.kuleuven.pylos.player.PylosPlayer;
import be.kuleuven.pylos.util.KnowledgeSessionHelper;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ine on 25/02/2015.
 */
public class StudentPlayerBestFit extends PylosPlayer
{
    private List <PylosLine> lines;

    private KieSession session;


    @Override
    public void doMove( PylosGameIF game, PylosBoard board )
    {
        PylosLocation location = null;

        switch (board.getNumberOfSpheresOnBoard())
        {
            case 0:
                generateLines( board );
                location = board.getBoardLocation( 1, 1, 0 );
                break;

            case 1:
                generateLines( board );
                PylosSphere sphere = Arrays.stream( board.getSpheres() )
                        .filter( s -> !s.isReserve() )
                        .findFirst().get();
                location = board.getBoardLocation( sphere.getLocation().X == 1 ? 2 : 1, sphere.getLocation().Y == 1 ? 2 : 1, 0 );
                break;
            default:

        }


        game.moveSphere( board.getSpheres( this )[0], location );
    }

    private void generateLines( PylosBoard board )
    {
        lines = new ArrayList <>();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                lines.add( new PylosLine( this.PLAYER_COLOR,
                        board.getBoardLocation( i, j + 0, 0 ),
                        board.getBoardLocation( i, j + 1, 0 ),
                        board.getBoardLocation( i, j + 2, 0 ) ) );

                lines.add( new PylosLine( this.PLAYER_COLOR,
                        board.getBoardLocation( j + 0, i, 0 ),
                        board.getBoardLocation( j + 1, i, 0 ),
                        board.getBoardLocation( j + 2, i, 0 ) ) );
            }
        }

        for (int i = 0; i < 3; i++)
        {

            lines.add( new PylosLine( this.PLAYER_COLOR,
                    board.getBoardLocation( i,  0, 1),
                    board.getBoardLocation( i,  1, 1),
                    board.getBoardLocation( i,  2, 1) ) );

            lines.add( new PylosLine( this.PLAYER_COLOR,
                    board.getBoardLocation(  0, i, 1 ),
                    board.getBoardLocation(  1, i, 1 ),
                    board.getBoardLocation(  2, i, 1 ) ) );
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

}