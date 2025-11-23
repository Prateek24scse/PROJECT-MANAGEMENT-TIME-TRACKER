package com.timetracker;

import com.timetracker.util.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TimeTrackerApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database
        DatabaseManager.getInstance().initializeDatabase();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        
        primaryStage.setTitle("PROJECT MANAGEMENT TIME TRACKER");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        // Try to set application icon/logo
        try {
            // Create a simple logo programmatically if image file doesn't exist
            // For now, we'll use a Unicode symbol as logo in the UI
            Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            // Logo file not found, will use text-based logo in FXML
            System.out.println("Logo image not found, using text-based logo");
        }
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}