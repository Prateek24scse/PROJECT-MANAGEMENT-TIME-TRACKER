package com.timetracker.util;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Custom logo component for Project Management Time Tracker
 * Creates a unique logo with clock and progress bar design
 */
public class LogoComponent extends StackPane {
    
    public LogoComponent(double size) {
        super();
        createLogo(size);
    }
    
    private void createLogo(double size) {
        Group logoGroup = new Group();
        
        // Create a circular background (clock face)
        Circle outerCircle = new Circle(size / 2, size / 2, size * 0.4);
        outerCircle.setFill(Color.WHITE);
        outerCircle.setStroke(Color.valueOf("#667eea"));
        outerCircle.setStrokeWidth(2);
        
        // Create inner circle
        Circle innerCircle = new Circle(size / 2, size / 2, size * 0.3);
        innerCircle.setFill(Color.valueOf("#667eea"));
        
        // Create clock hands (hour and minute)
        // Hour hand (shorter)
        Rectangle hourHand = new Rectangle(size / 2 - 1, size / 2 - size * 0.15, 2, size * 0.15);
        hourHand.setFill(Color.WHITE);
        hourHand.setRotate(45); // Pointing to 3 o'clock
        
        // Minute hand (longer)
        Rectangle minuteHand = new Rectangle(size / 2 - 1, size / 2 - size * 0.25, 2, size * 0.25);
        minuteHand.setFill(Color.WHITE);
        minuteHand.setRotate(90); // Pointing to 12 o'clock
        
        // Create progress bars on the right side (representing time tracking)
        double barWidth = size * 0.08;
        double barSpacing = size * 0.12;
        double startX = size * 0.65;
        double startY = size * 0.25;
        
        // Bar 1 (shortest)
        Rectangle bar1 = new Rectangle(startX, startY, barWidth, size * 0.15);
        bar1.setFill(Color.valueOf("#48bb78"));
        bar1.setArcWidth(3);
        bar1.setArcHeight(3);
        
        // Bar 2 (medium)
        Rectangle bar2 = new Rectangle(startX, startY + barSpacing, barWidth, size * 0.25);
        bar2.setFill(Color.valueOf("#4299e1"));
        bar2.setArcWidth(3);
        bar2.setArcHeight(3);
        
        // Bar 3 (tallest)
        Rectangle bar3 = new Rectangle(startX, startY + barSpacing * 2, barWidth, size * 0.35);
        bar3.setFill(Color.valueOf("#667eea"));
        bar3.setArcWidth(3);
        bar3.setArcHeight(3);
        
        // Add all components to the group
        logoGroup.getChildren().addAll(
            outerCircle,
            innerCircle,
            hourHand,
            minuteHand,
            bar1,
            bar2,
            bar3
        );
        
        this.getChildren().add(logoGroup);
    }
    
    /**
     * Creates a simpler text-based logo with styling
     */
    public static StackPane createTextLogo() {
        StackPane logoPane = new StackPane();
        
        // Background circle
        Circle bgCircle = new Circle(20);
        bgCircle.setFill(Color.valueOf("#667eea"));
        
        // Clock icon text
        Text clockText = new Text("⏱");
        clockText.setFont(Font.font("System", FontWeight.BOLD, 24));
        clockText.setFill(Color.WHITE);
        
        logoPane.getChildren().addAll(bgCircle, clockText);
        
        return logoPane;
    }
}

