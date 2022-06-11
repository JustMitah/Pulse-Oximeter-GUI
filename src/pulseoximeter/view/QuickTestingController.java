/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pulseoximeter.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class QuickTestingController implements Initializable {
        /**
     * Initializes the controller class.
     */
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    @FXML
    private AnchorPane QuickTest;
    private int localID;
    public void setID(int id) {
        this.localID = id;
    }
    
   
    
    @FXML
    private void CloseApp(MouseEvent event){
        System.exit(0);
        
    }
    @FXML
    private void MinApp(MouseEvent event){
        stage = (Stage)QuickTest.getScene().getWindow();
        stage.setIconified(true);
    }
    private void makeStageDragable() {
		QuickTest.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		QuickTest.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		QuickTest.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		QuickTest.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("PoMain.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    @FXML
    void MeasureArduino(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\Kazwell\\Documents\\NetBeansProjects\\PulseOximeter\\src\\pulseoximeter\\arduino\\esp_max30100\\esp_max30100.ino");
        Desktop.getDesktop().open(file);
    }
    
    @FXML 
    private WebView webView;
    public WebEngine engine;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                engine=webView.getEngine();
		makeStageDragable();
}  
}
