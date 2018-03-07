package be.kuleuven.pylos.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PylosGuiMain extends Application
{

    public static void main( String[] args )
    {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) throws Exception
    {

        FXMLLoader fxmlLoader = new FXMLLoader( PylosGuiMain.class.getResource( "PylosGui.fxml" ) );
        Parent     root       = fxmlLoader.load();
        primaryStage.setMaximized( true );
        primaryStage.setTitle( "Pylos - CODeS" );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
        primaryStage.setOnCloseRequest( ( evt ) -> System.exit( 0 ) );

    }

}
