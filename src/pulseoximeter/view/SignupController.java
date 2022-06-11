/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pulseoximeter.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import pulseoximeter.constructors.MyConnection;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class SignupController implements Initializable {

    @FXML
    private AnchorPane signup;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private TextField Username;

    @FXML
    private TextField Password;

    @FXML
    private TextField Rpassword;

    @FXML
    private DatePicker DoB;

    @FXML
    private ToggleGroup group;
    
  

    private void makeStageDragable() {
		signup.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		signup.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		signup.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		signup.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
    public void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("PoMain.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
    }    

    @FXML
    private void CloseApp(MouseEvent event){
        System.exit(0);
        
    }
    @FXML
    private void MinApp(MouseEvent event){
        stage = (Stage)signup.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void register(ActionEvent event) throws SQLException{        
        //Display the selection
        //Username.setText(rb.getText() + " is confirmed.");
        RadioButton rb = (RadioButton) group.getSelectedToggle();
        if(Username.getText().isEmpty() || Password.getText().isEmpty() || !(rb.isSelected()) || Rpassword.getText().isEmpty() || DoB.getValue()==null ){
            JOptionPane.showMessageDialog(null,"You must fill in all the fields!");
        } 
        if(!(Password.getText().equals(Rpassword.getText()))){
           JOptionPane.showMessageDialog(null,"Passwords does not match!");
        }   else{
        MyConnection cnx = new MyConnection();    
        Connection con = cnx.getConnection();
        try{
        
        PreparedStatement pst;
        pst = con.prepareStatement("INSERT INTO PATIENT(Username,Password,Sex,DateOfBirth) VALUES(?,?,?,?);");
        pst.setString(1,Username.getText());
        pst.setString(2,Password.getText());
        pst.setString(3,rb.getText());
        pst.setString(4,((TextField)DoB.getEditor()).getText());
        pst.executeUpdate();
        Username.setText("");
        Password.setText("");
        Rpassword.setText("");
        group.selectToggle(null);
        DoB.getEditor().clear();
        DoB.setValue(null);      
    } catch (SQLException ex){
        JOptionPane.showMessageDialog(null,ex);
    }}
 }
    
}
