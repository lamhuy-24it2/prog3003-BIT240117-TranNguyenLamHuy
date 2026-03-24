package com.btjava.bai2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatApp extends Application {
    private WebSocketClient webSocketClient;
    private TextArea chatArea;
    private TextField inputField;
    private TextField nameField;

    @Override
    public void start(Stage primaryStage) {
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        nameField = new TextField();
        nameField.setPromptText("Nhập tên của bạn...");
        nameField.setPrefWidth(120);

        inputField = new TextField();
        inputField.setPromptText("Nhập tin nhắn...");
        HBox.setHgrow(inputField, Priority.ALWAYS);

        Button sendButton = new Button("Gửi");
        sendButton.setOnAction(e -> sendMessage());

        inputField.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, nameField, inputField, sendButton);
        VBox root = new VBox(10, chatArea, inputBox);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Local Chat App - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:8887")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Platform.runLater(() -> chatArea.appendText("Hệ thống: Đã kết nối tới server.\n"));
                }

                @Override
                public void onMessage(String message) {
                    Platform.runLater(() -> chatArea.appendText(message + "\n"));
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Platform.runLater(() -> chatArea.appendText("Hệ thống: Đã ngắt kết nối.\n"));
                }

                @Override
                public void onError(Exception ex) {
                    Platform.runLater(() -> chatArea.appendText("Hệ thống: Lỗi kết nối.\n"));
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String name = nameField.getText().trim();
        String message = inputField.getText().trim();

        if (name.isEmpty()) name = "Ẩn danh";

        if (!message.isEmpty() && webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(name + ": " + message);
            inputField.clear();
        }
    }

    @Override
    public void stop() throws Exception {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}