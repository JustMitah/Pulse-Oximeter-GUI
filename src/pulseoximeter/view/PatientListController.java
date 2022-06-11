/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pulseoximeter.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pulseoximeter.constructors.ModelTable;
import pulseoximeter.constructors.MyConnection;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class PatientListController implements Initializable {
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane PatientList;

    @FXML
    public Label docname;
    @FXML
    private TableView<ModelTable> DisplayPatients;

    @FXML
    private TableColumn<ModelTable,String> pid;

    @FXML
    private TableColumn<ModelTable,String> name;

    @FXML
    private TableColumn<ModelTable,String> sex;

    @FXML
    private TableColumn<ModelTable,String> dob;

    @FXML
    private TableColumn<ModelTable,String> status;

    
    
    ObservableList <ModelTable> oblistd = FXCollections.observableArrayList();
    private void lecture(){
        try{
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
            rsGet = con.createStatement().executeQuery("Select * from patient");
            while (rsGet.next()) {
                oblistd.add(new ModelTable(rsGet.getString(1),rsGet.getString(2),rsGet.getString(4),rsGet.getString(5),rsGet.getString(6)));                   
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PatientListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lecture();
        makeStageDragable();
        pid.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        dob.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        DisplayPatients.setItems(oblistd);
    }    
    @FXML
    private void CloseApp(MouseEvent event){
        System.exit(0);
    }
    @FXML
    private void MinApp(MouseEvent event){
        stage.setIconified(true);
    }
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("PoMain.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    private void makeStageDragable() {
		PatientList.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		PatientList.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		PatientList.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		PatientList.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
    
}
