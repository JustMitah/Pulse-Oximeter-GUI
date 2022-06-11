/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pulseoximeter.view;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
public class PoMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
  
    PreparedStatement pst;
    ResultSet rs;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    @FXML
    private AnchorPane parent;
    @FXML
    private JFXTextField Username;
    
    @FXML
    private JFXButton loginButton;

    @FXML
    private Hyperlink forgot;
    
    @FXML
    private AnchorPane LoginPane;
    
    @FXML
    private JFXPasswordField Password;
    
    @FXML
    private JFXButton doc;

    @FXML
    private Label question;
    
    @FXML
    private ImageView Img;
    
    @FXML
    private ImageView ImgB;
    
    @FXML
    private Label HeadLabel;
    
    private boolean doctor = false;
    private String ip_address = "localhost";
    private String urlParameters = "0";
    
    @FXML    
    public void goToTest(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("QuickTesting.fxml"));	
            Parent QuickTest = loader.load();
            Scene quickscene = new Scene(QuickTest);
            QuickTestingController qtc = loader.getController();
            qtc.engine.load("http://"+this.ip_address+"/SensorData/ ?pid="+0);                          
            Stage quickstage = (Stage)((Node) event.getSource()).getScene().getWindow();
            quickstage.setScene(quickscene);
            quickstage.show();
    }
    
    @FXML
    public void signup(ActionEvent event) throws IOException {
        Parent signup = FXMLLoader.load(getClass().getResource("Signup.fxml"));	
	Scene SignPage = new Scene(signup);
        Stage signstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        signstage.setScene(SignPage);
	signstage.show();
    }
    
    @FXML
    void changeLogintoDoctor(MouseEvent event) {
        if (this.doctor==false){
        this.doctor=true;
        LoginPane.setStyle("-fx-background-color: #34847c; -fx-background-radius : 30 ");
        loginButton.setStyle("-fx-background-color: #0e6e64;-fx-background-radius : 30");
        forgot.setStyle("-fx-background-color: #34847c");
        question.setText("Go Back Here");
        Img.setOpacity(0.0);
        ImgB.setOpacity(1.0);
        } else {
        this.doctor=false;
        LoginPane.setStyle("-fx-background-color:  #66A4EA; -fx-background-radius : 30 ");
        loginButton.setStyle("-fx-background-color:  #000850;-fx-background-radius : 30");
        forgot.setStyle("-fx-background-color:  #66A4EA");
        question.setText("");
        ImgB.setOpacity(0.0);
        Img.setOpacity(1.0);
        }
    }
    @FXML
    public void login(ActionEvent event) throws IOException{

        String username = Username.getText();
        String password = Password.getText();
    if (this.doctor==true){
        if (username.equals("") || password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password needed");
            Username.setText("");
            Password.setText(""); 
            Username.requestFocus();    
        }
        else
        {
            try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("select * from `doctor` where name=? and pass=?");
                pst.setString(1,username);
                pst.setString(2,password);
                rs = pst.executeQuery();
                if (rs.next()){
                    //JOptionPane.showMessageDialog(null,"Login Success");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("PatientList.fxml"));
                    Parent PatientList = loader.load();
                    Scene patientscene = new Scene(PatientList);
                    PatientListController plc = loader.getController();
                    plc.docname.setText(rs.getString("name"));
                    Stage patientstage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    patientstage.setScene(patientscene);
                    patientstage.show();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Username or Password invalid");
                    Username.setText("");
                    Password.setText(""); 
                    Username.requestFocus();
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(PoMainController.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }   
    } 
    else if (this.doctor==false){
        if (username.equals("") || password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password needed");
            Username.setText("");
            Password.setText(""); 
            Username.requestFocus();
        }
        else
        {
            
            try{
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();  
                pst = con.prepareStatement("select * from `patient` where Username=? and Password=?");
                pst.setString(1,username);
                pst.setString(2,password);
                rs = pst.executeQuery();
                if (rs.next()){
                    //JOptionPane.showMessageDialog(null,"Login Success");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("HealthReport.fxml"));	
                    Parent HealthReport = loader.load();
                    Scene healthscene = new Scene(HealthReport);
                    HealthReportController hrc = loader.getController();
                    hrc.Username.setText(rs.getString("Username"));
                    hrc.setID(rs.getInt("PatientID"));   
                    hrc.engine.load("http://"+this.ip_address+"/SensorData/?pid="+rs.getInt("PatientID"));
                    /*try {
                           URL url = new URL("http://"+this.ip_address+"/SensorData/post-esp-data.php");
                           URLConnection conn = url.openConnection();
                           // activate the output
                           conn.setDoOutput(true);
                           PrintStream ps = new PrintStream(conn.getOutputStream());
                           // send your parameters to your site
                           ps.print("pid="+rs.getInt("PatientID"));
                           conn.getInputStream();
                           ps.close();
                        } catch (MalformedURLException e) {
                                 e.printStackTrace();
                        } catch (IOException e) {
                                 e.printStackTrace();
                         } */
                    Stage healthstage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    healthstage.setScene(healthscene);
                    healthstage.show();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Username or Password invalid");
                    Username.setText("");
                    Password.setText(""); 
                    Username.requestFocus();
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(PoMainController.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }       
        }   
    } else {
            JOptionPane.showMessageDialog(null,"Username or Password invalid");
        }}
    @FXML
    private void CloseApp(MouseEvent event){
        System.exit(0);
        
    }
    @FXML
    private void MinApp(MouseEvent event){
        stage = (Stage)parent.getScene().getWindow();
        stage.setIconified(true);
    }
    private void makeStageDragable() {
		parent.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parent.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		parent.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parent.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
	
   @Override
    public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
}  
}
