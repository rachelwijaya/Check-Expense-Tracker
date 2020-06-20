package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtil;


public class LoginController implements Initializable {
    Connection connection;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    // Constructor
    public LoginController() {
        connection = ConnectionUtil.connection();
    }
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passText;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private Label errorlogs;

    public void handleButtonAction (ActionEvent event){
        // when user clicks on a button, this function figures out which button has been clicked
        if (event.getSource() == loginButton){
            if (loginButton()){
                try {
                    Parent pane = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                    Scene scene = new Scene(pane);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        if (event.getSource() == signupButton) {
            try {
                Parent pane = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
                Scene scene = new Scene(pane);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean loginButton() {

        // we first get the strings from both the username and password fields
        String username = usernameText.getText();
        String pass = passText.getText();
        // in the case of one or both fields are empty
        if (username.isEmpty() || pass.isEmpty()) {
            errorlogs.setTextFill(Color.RED);
            errorlogs.setText("Fill both fields please");

        } else {
            // one we get the strings, we search the database from mysql
            String sql = "SELECT * FROM usernames WHERE username = ? and password = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, pass);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) { // if user has put incorrect details
                    errorlogs.setTextFill(Color.RED);
                    errorlogs.setText("Incorrect username/password");
                    return false;
                } else {
                    // user puts correct detail
                    errorlogs.setText("Logging in...");
                    return true;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        if (connection == null){
            errorlogs.setText("Server not connected");
        }
        else {
            errorlogs.setText("Server is connected, go ahead and login");
        }
    }


}
