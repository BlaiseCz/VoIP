package com.gui.components;

import com.models.RegistrationForm;
import com.rest_providers.UserProviderImpl;
import com.utils.IpUtils;
import com.utils.PasswordUtils;
import controllers.AlertController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.UnknownHostException;

@Controller
public class RegisterController {
    @Autowired
    private MainController mainController;

    @Autowired
    private UserProviderImpl userProvider;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void initialize() {

    }

    @FXML
    void register(ActionEvent event) throws
                                     UnknownHostException,
                                     InterruptedException {
        RegistrationForm registrationForm = RegistrationForm
                .builder()
                .username(usernameTF.getText())
                .email(emailTF.getText())
                .password(PasswordUtils.getPasswordHash(passwordTF.getText()))
                .IPAddress(IpUtils.getLocalIpAddr())
                .build();

        new Thread(() -> {
            Platform.runLater(() -> {

                try {
                    userProvider.register(registrationForm);
                    AlertController.showAlert(String.format("User %s registered successfully",
                                                            registrationForm.getUsername()),
                                              null,
                                              "Check your email and confirm. Then login into application.");
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertController.showAlert("Failed to registered!",
                                              null,
                                              "Try to use another user or email.");
                }
            });

        }).start();
    }


    @FXML
    void login(ActionEvent event) {
        mainController.switchToLogin();
    }
}
