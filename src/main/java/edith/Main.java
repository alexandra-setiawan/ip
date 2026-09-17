package edith;

import edith.command.Command;
import edith.command.Parser;
import edith.task.Task;
import edith.task.TaskList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
    private TaskList tasks;
    private final ChatUi ui = new ChatUi();
    private TextField commandField;
    private Button sendButton;

    /** Builds the chat window and wires its input controls to Edith. */
    @Override
    public void start(Stage stage) {
        VBox messages = new VBox(12);
        messages.setFillWidth(true);
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

        HBox input = new HBox(8, commandField, sendButton);
        input.setAlignment(Pos.CENTER);
        input.getStyleClass().add("input-bar");
        HBox.setHgrow(commandField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(conversation);
        root.setBottom(input);
        root.getStyleClass().add("app");

        Scene scene = new Scene(root, 700, 620);
        scene.getStylesheets().add(Main.class.getResource("/edith/gui.css").toExternalForm());

        try {
            tasks = storage.load();
            ui.showWelcome();
        } catch (EdithException error) {
            ui.showError(error.getMessage());
            commandField.setDisable(true);
            sendButton.setDisable(true);
        }
        stage.setTitle("Edith • Direct Messages");
        stage.setMinWidth(520);
        stage.setMinHeight(480);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
        commandField.requestFocus();
    }

    /** Creates the compact header for the task assistant. */
    private VBox createHeader() {
        Label avatar = new Label("E");
        avatar.getStyleClass().add("header-avatar");

        Label name = new Label("Edith");
        name.getStyleClass().add("profile-name");
        Label status = new Label("Task assistant");
        status.getStyleClass().add("profile-status");
        VBox identity = new VBox(1, name, status);

        HBox profile = new HBox(8, avatar, identity);
        profile.setAlignment(Pos.CENTER_LEFT);

        VBox header = new VBox(profile);
        header.setPadding(new Insets(10, 18, 10, 18));
        header.getStyleClass().add("header");
        return header;
    }

    /** Sends the current command to Edith and displays its response. */
    private void sendCommand() {
        String commandText = commandField.getText();
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
        } catch (EdithException error) {
            ui.showError(error.getMessage());
        }
    }

    /** Adapts Edith's output methods from the console to direct-message bubbles. */
    private static class ChatUi extends Ui {
        private static final double BUBBLE_WIDTH_OFFSET = 120;
        private static final double MINIMUM_BUBBLE_WIDTH = 250;
        private static final double MAXIMUM_BUBBLE_WIDTH = 620;
        private static final String WELCOME_MESSAGE = "Hello! I'm Edith. 👋\n"
                + "Send \"help\" to see the list of commands.";
        private static final String GUI_HELP_TEXT = "How to use Edith\n\n"
                + "ADD TASKS\n"
                + "  todo <description>\n"
                + "  deadline <description> /by <date>\n"
                + "  event <description> /from <start> /to <end>\n\n"
                + "MANAGE TASKS\n"
                + "  list\n"
                + "  find <keyword>\n"
                + "  mark <number>\n"
                + "  unmark <number>\n"
                + "  delete <number>\n\n"
                + "SORT TASKS\n"
                + "  sort alph [desc]\n"
                + "  sort date [desc]\n"
                + "  sort status\n"
                + "  sort added [desc]\n\n"
                + "OTHER\n"
                + "  help\n"
                + "  bye";

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
            appendBubble(message, isUser, false);
        }

        /** Adds a message bubble with optional error styling and scrolls it into view. */
        private void appendBubble(String message, boolean isUser, boolean isError) {
            assert messages != null && conversation != null
                    : "Chat controls must be initialized before displaying messages";
            Label bubble = new Label(message);
            bubble.setWrapText(true);
            bubble.maxWidthProperty().bind(Bindings.createDoubleBinding(
                    this::calculateMessageMaxWidth, conversation.widthProperty()));
            bubble.getStyleClass().addAll("message-bubble", isUser ? "user-bubble" : "edith-bubble");
            if (isError) {
                bubble.getStyleClass().add("error-bubble");
            }

            HBox row;
            if (isUser) {
                row = new HBox(bubble);
            } else {
                Label sender = new Label(isError ? "COMMAND ERROR" : "EDITH");
                sender.getStyleClass().add(isError ? "error-label" : "sender-label");
                VBox response = new VBox(4, sender, bubble);
                row = new HBox(response);
            }
            row.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
            row.getStyleClass().add("message-row");
            messages.getChildren().add(row);
            Platform.runLater(() -> conversation.setVvalue(1.0));
        }

        /** Returns a readable bubble width for the current conversation size. */
        private double calculateMessageMaxWidth() {
            return Math.max(MINIMUM_BUBBLE_WIDTH,
                    Math.min(conversation.getWidth() - BUBBLE_WIDTH_OFFSET, MAXIMUM_BUBBLE_WIDTH));
        }

        /** Adds the welcome message, including Edith's banner, to the conversation. */
        private void appendWelcomeMessage() {
            assert messages != null && conversation != null
                    : "Chat controls must be initialized before displaying messages";
            Label sender = new Label("EDITH");
            sender.getStyleClass().add("sender-label");

            Label banner = new Label(EDITH_BANNER);
            banner.getStyleClass().add("welcome-banner");

            Label welcome = new Label(WELCOME_MESSAGE);
            welcome.setWrapText(true);
            welcome.maxWidthProperty().bind(Bindings.createDoubleBinding(
                    this::calculateMessageMaxWidth, conversation.widthProperty()));
            welcome.getStyleClass().addAll("message-bubble", "edith-bubble");

            VBox response = new VBox(4, sender, banner, welcome);
            HBox row = new HBox(response);
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("message-row");
            messages.getChildren().add(row);
            Platform.runLater(() -> conversation.setVvalue(1.0));
        }

        @Override
        public void showWelcome() {
            appendWelcomeMessage();
        }

        @Override
        public void showError(String message) {
            appendBubble(message, false, true);
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
        public void showHelp() {
            appendBubble(GUI_HELP_TEXT, false);
        }

        @Override
        public void showMatchingTasks(TaskList tasks, String keyword) {
            StringBuilder response = new StringBuilder("Here are the matching tasks in your list:");
            for (int index : tasks.findMatchingIndices(keyword)) {
                response.append("\n").append(index + 1).append(".").append(tasks.get(index));
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
