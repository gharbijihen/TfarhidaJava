package Controllers;
import Entities.User;
import Service.ServiceUser;
import Service.generatepdf;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.DocumentException;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;

public class UsersCrudController implements Initializable {
    private final ServiceUser serviceUser = new ServiceUser();
    @FXML
    private ImageView imageView;

    private byte[] imageData;

    @FXML
    private AnchorPane bord;

    @FXML
    private Button btnAjouter;

    @FXML
    private Label nomPrenom;

    @FXML
    private TextField search_tv;

    @FXML
    private TextField filtrefield;

    @FXML
    private Button btnNaviguer2;

    @FXML
    private TableView<User> tableView;

    private FilteredList<User> filteredList;


    @FXML
    void goToNavigate(ActionEvent event) {
        RouterController router = new RouterController();
        router.navigate("/fxml/AdminDashboard.fxml");
    }

    @FXML
    void gotoAjouter(ActionEvent event) {
        RouterController router = new RouterController();
        router.navigate("/fxml/AddUser.fxml");
    }
    @FXML
    void goToNavigate1(ActionEvent event) {
        RouterController router =new RouterController() ;
        router.navigate("/fxml/statistique.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        updateUserList();
        filteredList = new FilteredList<>(tableView.getItems(), b -> true);

        // Set the filter Predicate whenever the filter changes.
        filtrefield.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableView();
        });
    }

    public void updateUserList() {
        try {
            List<User> users = serviceUser.ReadAll();

            tableView.getItems().clear();

            tableView.getItems().addAll(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void initializeTableColumns() {
        tableView.getColumns().clear();

        // imageColumn.setCellFactory(getImageCellFactory());
        TableColumn<User, Void> RoleColumn = new TableColumn<>("Role");
        RoleColumn.setCellFactory(column -> new TableCell<User, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    User user = getTableRow().getItem();
                    String role = user.getRoles();
                    String displayRole = role.substring(7, role.length() - 2); // Remove "[ROLE_" and "]"
                    setText(displayRole);
                }
            }
        });
        TableColumn<User, String> usernameColumn = new TableColumn<>("Nom d'utilisateur");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<User, String> lastname = new TableColumn<>("prénom");
        lastname.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        TableColumn<User, Boolean> isVerifiedColumn = new TableColumn<>("vérifier");
        isVerifiedColumn.setCellValueFactory(new PropertyValueFactory<>("is_verified"));

        isVerifiedColumn.setCellFactory(column -> new TableCell<User, Boolean>() {
            @Override
            protected void updateItem(Boolean isVerified, boolean empty) {
                super.updateItem(isVerified, empty);
                setText(null);
                setGraphic(null);

                if (isVerified != null) {
                    Button button = new Button();
                    button.setPrefSize(20, 20);

                    if (isVerified) {
                        Image isverifiedimage = new Image(getClass().getResourceAsStream("../assets/done1.png"));
                        ImageView isverifiedicon = new ImageView(isverifiedimage);
                        isverifiedicon.setFitWidth(20);
                        isverifiedicon.setFitHeight(20);
                        button.setGraphic(isverifiedicon);
                        button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                    } else {
                        Image isverifiedimage = new Image(getClass().getResourceAsStream("../assets/failed1.png"));
                        ImageView isverifiedicon = new ImageView(isverifiedimage);
                        isverifiedicon.setFitWidth(20);
                        isverifiedicon.setFitHeight(20);
                       button.setGraphic(isverifiedicon);
                       button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                    }

                    // Add click listener to the button
                    button.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("User ban");
                        if (user.getIs_verified())
                            alert.setContentText("Vous etes sur de bloque cette utilisateur ?");
                        else
                            alert.setContentText("Vous etes sur de verifier cette utilisateur ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            try {
                                user.setIs_verified(!user.getIs_verified());
                                serviceUser.update(user);
                                updateUserList();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Error");
                                errorAlert.setHeaderText("Error Base des données");
                                errorAlert.setContentText("Un erreur en updaing user.");
                                errorAlert.showAndWait();
                            }
                        }
                    });

                    setGraphic(button);
                }

            }
        });


// Add the column to your TableView
        TableColumn<User, String> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(getButtonCellFactory());

        tableView.getColumns().addAll(emailColumn, usernameColumn, nameColumn, lastname, isVerifiedColumn, numeroColumn, RoleColumn, actionColumn);
    }


    private Callback<TableColumn<User, Void>, TableCell<User, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                    private final Button modifyButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                        ImageView modifyIcon = new ImageView(modifyImage);
                        modifyIcon.setFitWidth(20);
                        modifyIcon.setFitHeight(20);
                        modifyButton.setGraphic(modifyIcon);

                        modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                       deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                        Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                        ImageView deleteIcon = new ImageView(deleteImage);
                        deleteIcon.setFitWidth(16);
                        deleteIcon.setFitHeight(16);
                        deleteButton.setGraphic(deleteIcon);

                        // Set button actions
                        modifyButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                        });

                        deleteButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(5);
                            buttons.getChildren().addAll(modifyButton, deleteButton); // Add buttons to HBox

                            modifyButton.setFocusTraversable(false);
                            deleteButton.setFocusTraversable(false);

                            modifyButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");
                            deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5px; -fx-padding: 5px 10px;");

                            Image modifyImage = new Image(getClass().getResourceAsStream("../assets/modify.png"));
                            ImageView modifyIcon = new ImageView(modifyImage);
                            modifyIcon.setFitWidth(20);
                            modifyIcon.setFitHeight(20);
                            modifyButton.setGraphic(modifyIcon);
                            Image deleteImage = new Image(getClass().getResourceAsStream("../assets/delete.png"));
                            ImageView deleteIcon = new ImageView(deleteImage);
                            deleteIcon.setFitWidth(20);
                            deleteIcon.setFitHeight(20);
                            deleteButton.setGraphic(deleteIcon);
                            modifyButton.setOnAction((ActionEvent event) -> {
                                User user = getTableView().getItems().get(getIndex());
                                ModifyUserController.user = user;
                                RouterController.navigate("/fxml/ModifyUser.fxml");
                            });


                            deleteButton.setOnAction((ActionEvent event) -> {
                                User user = getTableView().getItems().get(getIndex());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Delete User");
                                alert.setContentText("Vous etes sur ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        serviceUser.delete(user);

                                        updateUserList();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setTitle("Error");
                                        errorAlert.setHeaderText("Error Base des données");
                                        errorAlert.setContentText("Un erreur en supprimant user.");
                                        errorAlert.showAndWait();
                                    }
                                }
                            });

                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
    }

    public void searchquery(KeyEvent keyEvent) {
    }

    @FXML
    void GeneratePdf(ActionEvent event) throws DocumentException, SQLException {
        try {
            ArrayList<User> users = (ArrayList<User>) new ServiceUser().ReadAll();
            generatepdf.generatePDF(users, new FileOutputStream("C:\\Users\\MSI\\IdeaProjects\\Users.pdf"), "C:\\Users\\MSI\\Downloads\\tfarhida2\\src\\main\\resources\\assets\\tfarhida.png");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF  a été généré avec succès!");
            alert.showAndWait();
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la génération du PDF des Utilisateurs: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void filterTableView() {
        // Créer un prédicat pour filtrer les éléments de la TableView
        Predicate<User> filterPredicate = user -> {
            // Vérifier si le texte de filtrage correspond à l'une des propriétés de l'offre
            return user.getUsername().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || user.getRoles().toLowerCase().contains(filtrefield.getText().toLowerCase())
                    || String.valueOf(user.getRoles()).contains(filtrefield.getText())
                    || String.valueOf(user.getUsername()).contains(filtrefield.getText());
        };

        // Mettre à jour la liste filtrée
        filteredList.setPredicate(filterPredicate);

        // Lier la liste filtrée à la TableView
        SortedList<User> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}