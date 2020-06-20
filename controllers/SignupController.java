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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController implements Initializable{

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passText;

    @FXML
    private Button submitButton;

    @FXML
    private Label submitInsert;

    @FXML
    private Button backToLogin;

    Connection connection;
    PreparedStatement preparedStatement = null;
    int resultSet;

    public SignupController(){
        connection = ConnectionUtil.connection();
    }

    public void handleButtonAction(ActionEvent event) {
        String username = userText.getText();
        String password = passText.getText();
        if (event.getSource() == submitButton) {
            if (username.isEmpty() || password.isEmpty()) {
                submitInsert.setTextFill(Color.RED);
                submitInsert.setText("Fill both fields please");

            }
            else {
                String insert = "INSERT INTO usernames (username, password) VALUES (?, ?)";
                try {
                    preparedStatement = connection.prepareStatement(insert);
                    submitInsert.setText("Data inserted!");
                    preparedStatement.setString(1, userText.getText());
                    preparedStatement.setString(2, passText.getText());
                    resultSet = preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        else if (event.getSource() == backToLogin){
            try {
                Parent pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}