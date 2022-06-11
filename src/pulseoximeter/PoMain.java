/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package pulseoximeter;

import javafx.stage.StageStyle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Kazwell
 */
public class PoMain extends Application {
    public static Stage stage = null;
    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("view/PoMain.fxml"));
            Scene scene = new Scene(root,600,332);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Oximeter Application");
            stage.setScene(scene);          
            stage.show();
        }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}