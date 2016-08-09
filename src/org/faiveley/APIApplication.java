/*
 * Faiveley Transport License
 */
package org.faiveley;

import com.sun.javaws.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class contains main(String[] args)
 * 
 * @author 813308
 */
public class APIApplication extends Application {

    /**
     * Start main view
     *
     * @param mainStage main stage
     */
    @Override
    public void start(Stage mainStage) {
        try {
            // Charge main view
            AnchorPane MainView = (AnchorPane) FXMLLoader.load(APIApplication.class.getResource("view/MainView.fxml"));

            // Set up a scene
            Scene scene = new Scene(MainView, 700, 400);

            // Settings main stage
            mainStage.setTitle("API Application");
            mainStage.setScene(scene);
            mainStage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main launch application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Launch application
        Application.launch(APIApplication.class, (java.lang.String[]) null);
    }

}
