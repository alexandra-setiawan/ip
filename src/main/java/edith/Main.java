package edith;

import edith.command.Command;
import edith.command.Parser;
import edith.task.Task;
import edith.task.TaskList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Provides a graphical direct-message interface for interacting with Edith. */
public class Main extends Application {
    private static final String TASK_FILE = "./data/edith.txt";
    private static final String EDITH_BANNER = " _____    _ _ _   _     \n"
            + "| ____|__| (_) |_| |__  \n"
            + "|  _| / _` | | __| '_ \\ \n"
            + "| |__| (_| | | |_| | | |\n"
            + "|_____\\__,_|_|\\__|_| |_|";

    private final Storage storage = new Storage(TASK_FILE);
    private final TaskList tasks = storage.load();
    private final ChatUi ui = new ChatUi();
    private TextField commandField;
    private Button sendButton;

    /** Builds the chat window and wires its input controls to Edith. */
    @Override
    public void start(Stage stage) {
        VBox messages = new VBox(10);
        messages.getStyleClass().add("messages");

        ScrollPane conversation = new ScrollPane(messages);
        conversation.setFitToWidth(true);
        conversation.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        conversation.getStyleClass().add("conversation");
        ui.setConversation(messages, conversation);

        commandField = new TextField();
        commandField.setPromptText("Message Edith...");
        commandField.setOnAction(event -> sendCommand());
        commandField.getStyleClass().add("command-field");

        sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendCommand());
        sendButton.getStyleClass().add("send-button");

        HBox input = new HBox(10, commandField, sendButton);
        input.setAlignment(Pos.CENTER);
        input.getStyleClass().add("input-bar");
        HBox.setHgrow(commandField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(conversation);
        root.setBottom(input);
        root.getStyleClass().add("app");

        Scene scene = new Scene(root, 680, 620);
        scene.getStylesheets().add(Main.class.getResource("/edith/gui.css").toExternalForm());

        ui.showWelcome();
        stage.setTitle("Edith • Direct Messages");
        stage.setMinWidth(480);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
        commandField.requestFocus();
    }

    /** Creates the profile header and Edith banner. */
    private VBox createHeader() {
        Label avatar = new Label("E");
        avatar.getStyleClass().add("header-avatar");

        Label name = new Label("Edith");
        name.getStyleClass().add("profile-name");
        Label status = new Label("Active now");
        status.getStyleClass().add("profile-status");
        VBox identity = new VBox(1, name, status);

        HBox profile = new HBox(10, avatar, identity);
        profile.setAlignment(Pos.CENTER_LEFT);

        Label banner = new Label(EDITH_BANNER);
        banner.getStyleClass().add("edith-banner");

        VBox header = new VBox(10, profile, banner);
        header.setPadding(new Insets(12, 18, 10, 18));
        header.getStyleClass().add("header");
        return header;
    }

    /** Sends the current command to Edith and displays its response. */
    private void sendCommand() {
        String commandText = commandField.getText().trim();
        if (commandText.isEmpty()) {
            return;
        }

        ui.showUserMessage(commandText);
        commandField.clear();

        try {
            Command command = Parser.parse(commandText);
            command.execute(tasks, ui, storage);
            if (command.isExit()) {
                commandField.setDisable(true);
                sendButton.setDisable(true);
            }
        } catch (EdithException | RuntimeException error) {
            ui.showError(error instanceof EdithException
                    ? error.getMessage()
                    : "OOPS!!! Please enter a valid command.");
        }
    }

    /** Adapts Edith's output methods from the console to direct-message bubbles. */
    private static class ChatUi extends Ui {
        private VBox messages;
        private ScrollPane conversation;

        /** Sets the controls used for the chat transcript. */
        void setConversation(VBox messages, ScrollPane conversation) {
            this.messages = messages;
            this.conversation = conversation;
        }

        /** Adds a right-aligned user message. */
        void showUserMessage(String message) {
            appendBubble(message, true);
        }

        /** Adds a message bubble and scrolls it into view. */
        private void appendBubble(String message, boolean isUser) {
            Label bubble = new Label(message);
            bubble.setWrapText(true);
            bubble.setMaxWidth(410);
            bubble.getStyleClass().addAll("message-bubble", isUser ? "user-bubble" : "edith-bubble");

            HBox row = new HBox(bubble);
            row.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
            row.getStyleClass().add("message-row");
            messages.getChildren().add(row);
            Platform.runLater(() -> conversation.setVvalue(1.0));
        }

        @Override
        public void showWelcome() {
            appendBubble("Hello! I'm Edith. 👋\nWhat can I do for you?", false);
        }

        @Override
        public void showError(String message) {
            appendBubble(message, false);
        }

        @Override
        public void showList(TaskList tasks) {
            StringBuilder response = new StringBuilder("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                response.append("\n").append(i + 1).append(".").append(tasks.get(i));
            }
            appendBubble(response.toString(), false);
        }

        @Override
        public void showAddedTask(Task task, int count) {
            appendBubble("Got it. I've added this task:\n  " + task
                    + "\nNow you have " + count + " tasks in the list.", false);
        }

        @Override
        public void showMarked(Task task) {
            appendBubble("Nice! I've marked this task as done:\n  " + task, false);
        }

        @Override
        public void showUnmarked(Task task) {
            appendBubble("OK, I've marked this task as not done yet:\n  " + task, false);
        }

        @Override
        public void showDeletedTask(Task task, int count) {
            appendBubble("Noted. I've removed this task:\n  " + task
                    + "\nNow you have " + count + " tasks in the list.", false);
        }

        @Override
        public void showBye() {
            appendBubble("Bye. Hope to see you again soon!", false);
        }
    }
}
