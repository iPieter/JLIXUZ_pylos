package be.kuleuven.pylos.player.student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RuleWeights
{
    private static RuleWeights ruleWeights;
    private static Map<String, Integer> weights;

    private RuleWeights()
    {
        weights = new HashMap<>();
    }

    public static RuleWeights getInstance()
    {
        if(ruleWeights == null)
        {
            ruleWeights = new RuleWeights();
        }

        return ruleWeights;
    }

    public void init( String filename )
    {
        try
        {
             BufferedReader reader = new BufferedReader( new FileReader( filename ) ) ;

             String line;
             while ( (line = reader.readLine()) != null )
             {
                 String [] parts = line.split( ":" );
                 if( parts.length == 2 )
                 {
                     weights.put( parts[0].substring( 1, parts[0].length() -1 ), Integer.valueOf( parts[1] ) );
                 }
             }

             System.out.println( weights.size() );


            for (Map.Entry<String, Integer> entry : weights.entrySet()) {
                String key = entry.getKey().toString();
                Integer value = entry.getValue();
                System.out.println(key + ":" + value);
            }
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public int getWeight( String ruleName )
    {
        System.out.println( "REQUESTING WEIGHT" + ruleName );

        if( weights.containsKey( ruleName ) )
            return weights.get( ruleName );

        //throw new Exception( "\"" + ruleName + "\" not found" );

        System.out.println( "RULE:\"" + ruleName + "\" has no weight." );
        System.exit( -1 );

        return 0;
    }
}
