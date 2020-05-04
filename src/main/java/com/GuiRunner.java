package com;

import com.gui.components.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class GuiRunner extends Application {

    private static Logger logger = Logger.getLogger(GuiRunner.class);
    private Image icon = new Image("file:///icons/phone.jpg");
    private MainController component;

    private ConfigurableApplicationContext springContext;
    private FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws
                       Exception {
        springContext = SpringApplication.run(GuiRunner.class);
        loader = new FXMLLoader();
        loader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void stop() throws
                       Exception {
        springContext.stop();
    }

    @Override
    public void start(Stage primaryStage) throws
                                          IOException,
                                          URISyntaxException {

        URL mainFxmlFile = ResourceUtils.getURL("src/main/resources/fxml_files/main/main.fxml");

        loader.setLocation(mainFxmlFile);
        Parent root = loader.load();

        component = loader.getController();

        primaryStage.setTitle("Voip Caller");
        primaryStage.getIcons()
                    .add(icon);
        primaryStage.setScene(new Scene(root, 800, 800));

        primaryStage.show();
    }

}
