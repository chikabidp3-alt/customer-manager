package com.chikabi.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * ICT261 Customer Manager classroom lab.
 *
 * Requirements covered:
 * 1. Form with name field and province list.
 * 2. Customer class and ObservableList<Customer>.
 * 3. TableView with name and province columns.
 * 4. Input validation before adding a customer.
 * 5. Confirmation before deleting a selected customer.
 * 6. Invalid-input testing and keyboard access.
 */
public class CustomerManagerApp extends Application {

    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    private TextField nameField;
    private ComboBox<String> provinceBox;
    private TableView<Customer> customerTable;

    @Override
    public void start(Stage stage) {
        Label title = new Label("Customer Manager");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("ICT261 Classroom Lab");
        subtitle.setStyle("-fx-text-fill: #666666;");

        VBox heading = new VBox(4, title, subtitle);

        // ---------------- FORM ----------------
        nameField = new TextField();
        nameField.setPromptText("Enter customer name");
        nameField.setAccessibleText("Customer name");

        provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );
        provinceBox.setPromptText("Select province");
        provinceBox.setMaxWidth(Double.MAX_VALUE);

        Label nameLabel = new Label("_Name:");
        nameLabel.setMnemonicParsing(true);
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("_Province:");
        provinceLabel.setMnemonicParsing(true);
        provinceLabel.setLabelFor(provinceBox);

        Button addButton = new Button("_Add Customer");
        addButton.setMnemonicParsing(true);
        addButton.setDefaultButton(true);
        addButton.setOnAction(e -> addCustomer());

        Button clearButton = new Button("_Clear");
        clearButton.setMnemonicParsing(true);
        clearButton.setOnAction(e -> clearForm());

        Button deleteButton = new Button("_Delete Selected");
        deleteButton.setMnemonicParsing(true);
        deleteButton.setOnAction(e -> deleteSelectedCustomer());

        HBox formButtons = new HBox(10, addButton, clearButton, deleteButton);
        formButtons.setAlignment(Pos.CENTER_LEFT);

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(provinceLabel, 0, 1);
        form.add(provinceBox, 1, 1);
        form.add(formButtons, 1, 2);

        ColumnConstraints labelColumn = new ColumnConstraints();
        ColumnConstraints inputColumn = new ColumnConstraints();
        inputColumn.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().addAll(labelColumn, inputColumn);

        // ---------------- TABLE ----------------
        customerTable = new TableView<>(customers);
        customerTable.setPlaceholder(
                new Label("No customers added yet.")
        );
        customerTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");
        provinceColumn.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );

        customerTable.getColumns().addAll(nameColumn, provinceColumn);
        customerTable.setPrefHeight(350);

        // ---------------- KEYBOARD ACCESS ----------------
        nameField.setOnAction(e -> provinceBox.requestFocus());

        provinceBox.setOnAction(e -> addCustomer());

        Scene scene = new Scene(
                new VBox(18, heading, form, new Label("Customers"), customerTable),
                700,
                600
        );

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE &&
                    customerTable.getSelectionModel().getSelectedItem() != null) {
                deleteSelectedCustomer();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                clearForm();
            }
        });

        VBox root = (VBox) scene.getRoot();
        root.setPadding(new Insets(25));

        stage.setTitle("Customer Manager - ICT261");
        stage.setScene(scene);
        stage.show();

        nameField.requestFocus();
    }

    /**
     * Validate the form and add a customer.
     */
    private void addCustomer() {
        String name = nameField.getText().trim();
        String province = provinceBox.getValue();

        if (name.isEmpty()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Input",
                    "Please enter the customer name."
            );
            nameField.requestFocus();
            return;
        }

        if (province == null || province.isBlank()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Input",
                    "Please select a province."
            );
            provinceBox.requestFocus();
            return;
        }

        // Prevent a name containing only numbers.
        if (!name.matches(".*[A-Za-z].*")) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Input",
                    "Customer name must contain letters."
            );
            nameField.requestFocus();
            return;
        }

        customers.add(new Customer(name, province));

        clearForm();

        showAlert(
                Alert.AlertType.INFORMATION,
                "Customer Added",
                "Customer \"" + name + "\" was added successfully."
        );
    }

    /**
     * Clear the input controls.
     */
    private void clearForm() {
        nameField.clear();
        provinceBox.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    /**
     * Confirm and delete the selected customer.
     */
    private void deleteSelectedCustomer() {
        Customer selected =
                customerTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Please select a customer to delete."
            );
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Delete selected customer?");
        confirmation.setContentText(
                "Are you sure you want to delete "
                        + selected.getName()
                        + " from "
                        + selected.getProvince()
                        + "?"
        );

        ButtonType result = confirmation.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            customers.remove(selected);
        }
    }

    /**
     * Display an alert message.
     */
    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
