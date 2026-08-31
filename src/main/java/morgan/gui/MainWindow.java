package morgan.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import morgan.Morgan;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Morgan morgan;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image morganImage = new Image(this.getClass().getResourceAsStream("/images/Morgan.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    /** Injects the Morgan instance */
    public void setMorgan(Morgan m) {
        morgan = m;
        dialogContainer.getChildren().add(
                DialogBox.getMorganDialog("Meow~ I'm Morgan. What can I do for you? Meow~", morganImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Morgan's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        String response = morgan.getResponse(input);
        String commandType = morgan.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMorganDialog(response, morganImage, commandType)
        );
        userInput.clear();
    }
}
