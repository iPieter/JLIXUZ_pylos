package be.kuleuven.pylos.player.student;

/**
 * @author Pieter
 * @version 1.0
 */
public class PylosNeighbour
{
    private PylosLine line1;
    private PylosLine line2;

    public PylosNeighbour( PylosLine line1, PylosLine line2 )
    {
        this.line1 = line1;
        this.line2 = line2;
    }

    public PylosLine getLine1()
    {
        return line1;
    }

    public void setLine1( PylosLine line1 )
    {
        this.line1 = line1;
    }

    public PylosLine getLine2()
    {
        return line2;
    }

    public void setLine2( PylosLine line2 )
    {
        this.line2 = line2;
    }
}
