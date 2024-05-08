//package edu.esprit.controller;
//
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import edu.esprit.entites.Reponse;
//import edu.esprit.entites.Reponse;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import edu.esprit.servies.ReponseCrud;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Callback;
//
//
//public class AfficherReponseBackController implements Initializable {
//    @FXML
//    TableView<Reponse> ReponseTable;
//
//    @FXML
//    TableColumn<Reponse, String> idCol;
//
//    @FXML
//    TableColumn<Reponse, String> cinCol;
//
//    @FXML
//    TableColumn<Reponse, String> genreCol;
//
//    @FXML
//    TableColumn<Reponse, String> dateproCol;
//
//    @FXML
//    TableColumn<Reponse, String> datederCol;
//
//    @FXML
//    TableColumn<Reponse, String> groupesangCol;
//
//    @FXML
//    TableColumn<Reponse, String> typedonCol;
//    @FXML
//    TableColumn<Reponse, String> actionCol;
//
//    @FXML
//    TableColumn<Reponse, String> etatmarCol;
//
//    ObservableList<Reponse> DonList = FXCollections.observableArrayList();
//    ReponseCrud service = new ReponseCrud();
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        loadDate();
//    }
//
//    @FXML
//    private void close(MouseEvent event) {
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.close();
//    }
//
//    private void RefreshTable() {
//        DonList.clear();
//        List<Reponse> dons = service.afficher();
//        DonList.addAll(dons);
//        ReponseTable.setItems(DonList);
//    }
//
//    @FXML
//    private void getAddView(MouseEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDon.fxml"));
//            Parent parent = loader.load();
//            AjouterReponseController ajouterReponseController = loader.getController();
//            ajouterReponseController.setParentFXMLLoader(this);
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(AjouterReponseController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void getRefrashable() {
//        DonList.clear();
//        List<Reponse> reponses = service.afficher();
//        DonList.addAll(reponses);
//        ReponseTable.setItems(DonList);
//    }
//
//    @FXML
//    private void print(MouseEvent event) {
//    }
//
//    private void loadDate() {
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
//        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
//        dateproCol.setCellValueFactory(new PropertyValueFactory<>("datePro"));
//        datederCol.setCellValueFactory(new PropertyValueFactory<>("dateDer"));
//        groupesangCol.setCellValueFactory(new PropertyValueFactory<>("groupeSanguin"));
//        typedonCol.setCellValueFactory(new PropertyValueFactory<>("typeDeDon"));
//        etatmarCol.setCellValueFactory(new PropertyValueFactory<>("etatMarital"));
//
//        Callback<TableColumn<Reponse, String>, TableCell<Reponse, String>> cellFoctory = (TableColumn<Reponse, String> param) -> {
//            final TableCell<Reponse, String> cell = new TableCell<Reponse, String>() {
//                @Override
//                public void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty) {
//                        setGraphic(null);
//                        setText(null);
//                    } else {
//                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
//                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
//                        FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
//
//
//                        deleteIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#ff1744;");
//                        editIcon.setStyle("-fx-cursor: hand ; -glyph-size:28px; -fx-fill:#00E676;");
//                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
//                            try {
//                                Reponse reponse= ReponseTable.getSelectionModel().getSelectedItem();
//                                service.supprimer(reponse);
//                                RefreshTable();
//                            } catch (SQLException ex) {
//                                Logger.getLogger(edu.esprit.controller.AfficherReponseBackController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        });
//                        editIcon.setOnMouseClicked((MouseEvent event) -> {
//                            Reponse activitePhysique = ReponseTable.getSelectionModel().getSelectedItem();
//                            System.out.println(activitePhysique);
//                            if (activitePhysique != null) {
//                                try {
//                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDon.fxml"));
//                                    Parent parent = loader.load();
//
//                                    // Get the controller of ModifierActiviteFXML
////                                    ModifierDonFXML modifierDonFXML = loader.getController();
//
//                                    // Pass the selected activity to ModifierDonFXML to populate its fields
////                                    modifierDonFXML.setTextField(
////                                            activitePhysique.getId(),
////                                            activitePhysique.getCin(),
////                                            activitePhysique.getGenre(),
////                                            activitePhysique.getDateDer(),
////                                            activitePhysique.getDatePro(),
////                                            activitePhysique.getTypeDeDon(),
////                                            activitePhysique.getGroupeSanguin(),
////                                            activitePhysique.getEtatMarital()
////                                    );
//
//                                    Scene scene = new Scene(parent);
//                                    Stage stage = new Stage();
//                                    stage.setScene(scene);
//                                    stage.initStyle(StageStyle.UTILITY);
//                                    stage.show();
//                                } catch (IOException ex) {
//                                    Logger.getLogger(edu.esprit.controller.AfficherReponseBackController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            } else {
//                                // Handle case when no activity is selected
//                                // You can show an alert or message to inform the user to select an activity
//                            }
//                        });
//
//
//                        HBox managebtn = new HBox(editIcon, deleteIcon);
//                        managebtn.setStyle("-fx-alignment:center");
//                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
//                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
//                        setGraphic(managebtn);
//                        setText(null);
//                    }
//                }
//            };
//            return cell;
//        };
//        actionCol.setCellFactory(cellFoctory);
//        ReponseTable.setItems(DonList);
//    }
//}