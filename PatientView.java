import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PatientView extends Stage
{
    // Variables
    GridPane gridPane; // GridPane to organize the labels

    String patientInfoFileName; // File name for patient information
    String ctResultsFileName; // File name for CT scan results
    String patientName; // Patient's name
    String totalScore; // Total Agatston CAC score
    String lm; // LM value
    String lad; // LAD value
    String lcx; // LCX value
    String rca; // RCA value
    String pda; // PDA value
    String temp; // Temporary string for reading file lines

    Label patientNameLabel; // Label to display patient's name
    Label totalScoreLabel; // Label to display total Agatston CAC score
    Label lmLabel; // Label to display LM value
    Label ladLabel; // Label to display LAD value
    Label lcxLabel; // Label to display LCX value
    Label rcaLabel; // Label to display RCA value
    Label pdaLabel; // Label to display PDA value

    // Constructor
    public PatientView(String patientID) 
    {
        this.setTitle("Patient View"); // Set the title of the stage

        // Create a GridPane layout
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20)); 

        // Try to read patient information from files
        patientInfoFileName = patientID + "_PatientInfo.txt";
        ctResultsFileName = patientID + "CTResults.txt";
        patientName = "";
        totalScore = "";
        lm = "";
        lad = "";
        lcx = "";
        rca = "";
        pda = "";
        temp = "";

        // reading the data from personal information
        try (BufferedReader reader = new BufferedReader(new FileReader(patientInfoFileName))) 
        {
            temp = reader.readLine();
            patientName = reader.readLine();
        } 
        catch (IOException e) 
        {
            displayErrorMessage("Patient information file not found: " + patientInfoFileName);
            return;
        }

        // reading the data from CT scan file
        try (BufferedReader reader = new BufferedReader(new FileReader(ctResultsFileName))) 
        {
            temp = reader.readLine();
            totalScore = reader.readLine();
            lm = reader.readLine();
            lad = reader.readLine();
            lcx = reader.readLine();
            rca = reader.readLine();
            pda = reader.readLine();
        } 
        catch (IOException e) 
        {
            displayErrorMessage("CT results file not found: " + ctResultsFileName);
            return;
        }

        // Add labels with patient information
        patientNameLabel = new Label("Hello " + patientName);
        patientNameLabel.setPadding(new Insets(20)); 
        
        totalScoreLabel = new Label("The total Agatston CAC score: " + totalScore);
        lmLabel = new Label("LM: " + lm);
        ladLabel = new Label("LAD: " + lad);
        lcxLabel = new Label("LCX: " + lcx);
        rcaLabel = new Label("RCA: " + rca);
        pdaLabel = new Label("PDA: " + pda);

        // Add components to the GridPane
        gridPane.add(patientNameLabel, 0, 0, 2, 1);
        gridPane.add(totalScoreLabel, 0, 1);
        gridPane.add(lmLabel, 0, 2);
        gridPane.add(ladLabel, 0, 3);
        gridPane.add(lcxLabel, 0, 4);
        gridPane.add(rcaLabel, 0, 5);
        gridPane.add(pdaLabel, 0, 6);

        // Creating the borderpane and alinging the panes
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setTop(patientNameLabel);
        BorderPane.setAlignment(patientNameLabel, Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(borderPane, 600, 400);
        this.setScene(scene);
    }

    // Display error message
    private void displayErrorMessage(String message) 
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
