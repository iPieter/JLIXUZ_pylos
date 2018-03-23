package be.kuleuven.pylos.main;

public class Pair<L,T>
{
    private L key;
    private T value;

    public Pair(L key, T value)
    {
        this.key = key;
        this.value = value;
    }

    public L getKey()
    {
        return key;
    }

    public void setKey( L key )
    {
        this.key = key;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue( T value )
    {
        this.value = value;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Pair <?, ?> pair = ( Pair <?, ?> ) o;

        if ( !key.equals( pair.key ) ) return false;
        return value.equals( pair.value );
    }

    @Override
    public int hashCode()
    {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public Pair<L,T> copy()
    {
        return new Pair<L,T>( key, value);
    }

    @Override
    public String toString()
    {
        return "{" +
                 key +
                ":" + value +
                '}';
    }
}
