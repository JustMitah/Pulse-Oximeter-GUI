package pulseoximeter.view;

import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import pulseoximeter.constructors.ModelTableStatus;
import pulseoximeter.constructors.MyConnection;

public class HealthReportController implements Initializable  {
    
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private int localID;
        @FXML 
    private WebView webView;
    public WebEngine engine;
    @FXML
    private AnchorPane HealthReport;
    @FXML
    private JFXButton DisplayDetails;
    @FXML
    private TableView<ModelTableStatus> Results;
    
    @FXML
    private TableView<ModelTableStatus> Details;
    @FXML
    private TableColumn<ModelTableStatus, String> HeartRate;

    @FXML
    private TableColumn<ModelTableStatus, String> OxySat;
    
    @FXML
    private TableColumn<ModelTableStatus, String>  HeartRateDisplay;

    @FXML
    private TableColumn<ModelTableStatus, String>  OxySatDisplay;

    @FXML
    private Label State;

    @FXML
    public Label Username;
    
    
    
    @FXML
    void RandomGen(MouseEvent event) throws SQLException {
        MyConnection cnx = new MyConnection();    
        Connection con = cnx.getConnection(); 
        int minOxy = 90;
        int maxOxy = 100;
        int minHeart = 50;
        int maxHeart = 120;
        int random_Oxy = (int)Math.floor(Math.random()*(maxOxy-minOxy+1)+minOxy);
        int random_Heart = (int)Math.floor(Math.random()*(maxHeart-minHeart+1)+minHeart);
          try {
        PreparedStatement pst;
        pst = con.prepareStatement("INSERT INTO datahealth(PatientID,HeartRate,OxySat) VALUES(?,?,?);");
        pst.setInt(1,localID);
        pst.setInt(2,random_Heart);
        pst.setInt(3,random_Oxy);
        pst.executeUpdate();
    }catch (SQLException ex) {
                Logger.getLogger(HealthReportController.class.getName()).log(Level.SEVERE, null, ex);
    } 
    }
    
    private void makeStageDragable() {
		HealthReport.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		HealthReport.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(1f);
		});
		HealthReport.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		HealthReport.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
    

    public void setID(int id) {
        this.localID = id;
    }
    public String getID() {
        return this.localID+"";
    }
    
    @FXML
    private void CloseApp(MouseEvent event){
        System.exit(0);
    }
    @FXML
    private void MinApp(MouseEvent event){
        stage.setIconified(true);
    }
    
    private void lecture(){
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
             try {           
            PreparedStatement st = con.prepareStatement("SELECT * FROM datahealth ORDER BY OrderID DESC;");      
            ResultSet rsGet= st.executeQuery();
            int i =0;
            while (rsGet.next() && i<=9) {
                if(rsGet.getInt(2)==localID){
                oblistd.add(new ModelTableStatus(rsGet.getString("HeartRate"),rsGet.getString("OxySat")));
                i++;
            }}}
         catch (SQLException ex) {
            Logger.getLogger(HealthReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    ObservableList <ModelTableStatus> oblistd = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        engine=webView.getEngine();
        final com.sun.webkit.WebPage webPage = com.sun.javafx.webkit.Accessor.getPageFor(engine);
        webPage.setBackgroundColor(0);
        makeStageDragable();
        HeartRateDisplay.setCellValueFactory(new PropertyValueFactory<>("HeartRate"));
        OxySatDisplay.setCellValueFactory(new PropertyValueFactory<>("OxySat"));
        HeartRate.setCellValueFactory(new PropertyValueFactory<>("HeartRate"));
        OxySat.setCellValueFactory(new PropertyValueFactory<>("OxySat"));
        Results.setItems(oblistd);   
        Details.setItems(oblistd);            
    }    
    @FXML
    void MeasureArduino(ActionEvent event) throws IOException {
        File file = new File("C:\\Users\\Kazwell\\Documents\\NetBeansProjects\\PulseOximeter\\src\\pulseoximeter\\arduino\\esp_max30100\\esp_max30100.ino");
        Desktop.getDesktop().open(file);
    }
    @FXML
    void ShowMore(ActionEvent event) {
        DisplayDetails.setDisable(true);
        lecture();              
        if (Integer.parseInt(oblistd.get(0).getHeartRate())<60 || Integer.parseInt(oblistd.get(0).getOxySat())<95){
           this.State.setText("Unhealthy");
           this.State.setStyle("-fx-text-fill: red");
        } else
        {
           this.State.setText("Healthy");
           this.State.setStyle("-fx-text-fill: green");
        }
        MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
             try {           
            PreparedStatement pst;
            pst = con.prepareStatement("UPDATE patient SET Status=? WHERE PatientID=?;");
            pst.setString(1,this.State.getText());
            pst.setInt(2,localID);
            pst.executeUpdate();
            } catch (SQLException ex) {
            Logger.getLogger(HealthReportController.class.getName()).log(Level.SEVERE, null, ex);
   }}

    
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("PoMain.fxml"));	
	Scene mainpo = new Scene(parent);
        Stage mainstage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainstage.setScene(mainpo);
	mainstage.show();
    }

}
