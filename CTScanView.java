import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CTScanView extends Stage 
{

    // Panes 
    GridPane gridPane;
    BorderPane borderPane;
    VBox vbox;

    // TextFields
    TextField patientIDField;
    TextField totalScoreField;
    TextField lmField;
    TextField ladField;
    TextField lcxField;
    TextField rcaField;
    TextField pdaField;

    // Labels
    Label patientIDLabel;
    Label totalScoreLabel;
    Label vesselLevelScoreLabel;
    Label lmLabel;
    Label ladLabel;
    Label lcxLabel;
    Label rcaLabel;
    Label pdaLabel;

    // Buttons
    Button saveButton;
    
    // Constructor
    public CTScanView() 
    {
        this.setTitle("CT Scan View");

        // Create a GridPane layout
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        // Add labels and text fields
        patientIDLabel = new Label("Patient ID:");
        patientIDField = new TextField();
        totalScoreLabel = new Label("The total Agatston CAC score:");
        totalScoreField = new TextField();
        vesselLevelScoreLabel = new Label("Vessel level Agatston CAC score:");
        lmLabel = new Label("LM:");
        lmField = new TextField();
        ladLabel = new Label("LAD:");
        ladField = new TextField();
        lcxLabel = new Label("LCX:");
        lcxField = new TextField();
        rcaLabel = new Label("RCA:");
        rcaField = new TextField();
        pdaLabel = new Label("PDA:");
        pdaField = new TextField();

        // Add components to the GridPane
        gridPane.add(patientIDLabel, 0, 0);
        gridPane.add(patientIDField, 1, 0);
        gridPane.add(totalScoreLabel, 0, 1);
        gridPane.add(totalScoreField, 1, 1);
        gridPane.add(vesselLevelScoreLabel, 0, 2);
        gridPane.add(lmLabel, 0, 3);
        gridPane.add(lmField, 1, 3);
        gridPane.add(ladLabel, 0, 4);
        gridPane.add(ladField, 1, 4);
        gridPane.add(lcxLabel, 0, 5);
        gridPane.add(lcxField, 1, 5);
        gridPane.add(rcaLabel, 0, 6);
        gridPane.add(rcaField, 1, 6);
        gridPane.add(pdaLabel, 0, 7);
        gridPane.add(pdaField, 1, 7);

        // Create a "Save" button
        saveButton = new Button("Save");
        saveButton.setPadding(new Insets(20));
        saveButton.setStyle("-fx-background-color: blue; -fx-min-width: 100px;-fx-font-weight: bold; -fx-padding: 10px;");
        saveButton.setOnAction(e -> 
        {
            if (validateFields(patientIDField, totalScoreField, lmField, ladField, lcxField, rcaField, pdaField)) 
            {
                saveCTScanData(patientIDField.getText(), totalScoreField.getText(), lmField.getText(), ladField.getText(), lcxField.getText(), rcaField.getText(), pdaField.getText());
                ((Stage) saveButton.getScene().getWindow()).close(); // Close the window after saving
            } 
            else 
            {
                showAlert("Error", "Please fill in all the fields.");
            }
        });

        // Create a VBox for the save button
        vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_RIGHT); // Align items to the bottom-right corner
        vbox.setSpacing(10); // Set spacing between items
        vbox.getChildren().add(saveButton);

        // Create a BorderPane to hold the GridPane and VBox
        borderPane = new BorderPane();
        borderPane.setRight(vbox); // Set the VBox to the right side of the BorderPane
        BorderPane.setMargin(vbox, new Insets(0, 50, 50, 0)); // Set margin for the VBox
        borderPane.setCenter(gridPane); // Set the GridPane to the center of the BorderPane

        // Create a scene and set it on the stage
        Scene scene = new Scene(borderPane, 600, 400);
        this.setScene(scene);
    }

    // Validate all the fields
    private boolean validateFields(TextField... fields) 
    {
        for (TextField field : fields) 
        {
            if (field.getText().isEmpty()) 
            {
                return false;
            }
        }
        return true;
    }

    // Save CT scan data to a file
    private void saveCTScanData(String patientID, String totalScore, String lm, String lad, String lcx, String rca, String pda) 
    {
        String fileName = patientID + "CTResults.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {
            writer.write(patientID + "\n");
            writer.write(totalScore + "\n");
            writer.write(lm + "\n");
            writer.write(lad + "\n");
            writer.write(lcx + "\n");
            writer.write(rca + "\n");
            writer.write(pda + "\n");

            // Clear the fields after saving
            patientIDField.setText("");
            totalScoreField.setText("");
            lmField.setText("");
            ladField.setText("");
            lcxField.setText("");
            rcaField.setText("");
            pdaField.setText("");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // Show an alert with the given title and message
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
