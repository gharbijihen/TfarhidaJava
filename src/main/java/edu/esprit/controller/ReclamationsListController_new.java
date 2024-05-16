package edu.esprit.controller;

//import Controllers.GuiLoginController;
//import Entities.User;
import Controllers.GuiLoginController;
import edu.esprit.entites.Notification;
import edu.esprit.entites.Reclamation;
import edu.esprit.servies.NotificationService;
import edu.esprit.servies.ReclamationCrud;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import javax.net.ssl.SSLContext;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class ReclamationsListController_new implements Initializable {

    public HBox addReviewsModel;
    //  public HBox updateBtnContainer;
    public TextArea commentInput;
    public TextField titleInput;
    public ComboBox<String> TypeInput;
    public HBox updateBtnContainer;
    public HBox submitBtn;
    public HBox updateBtn;
    public HBox viewReplyModel;
    public TextArea replyInput;
    public Label error;
    public HBox nameInputErrorHbox;
    public Text nameInputError;
    public Text TitleError;
    public Text TypeError;
    public Text DescriptionError;
    public TextField champRecherche;

    @FXML
    private GridPane commentsListContainer;

    @FXML
    private VBox content_area;
    @FXML
    private ImageView img;
    @FXML
    private ComboBox<String> filtreTypeLog;



    // private User user = null;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.viewReplyModel.setVisible(false);
        this.error.setVisible(false);
        if (filtreTypeLog!=null ) {
            filtreTypeLog.valueProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    handleTypeSelection();
                }
            });
            ObservableList<String> items = FXCollections.observableArrayList(
                    "Activite", "Logement", "Transport", "Restaurant"
            );
            filtreTypeLog.setItems(items);        // recuperer user connecté

        }
        updateBtnContainer.setVisible(false);
        addReviewsModel.setVisible(false);
        TypeInput.getItems().addAll("Activité", "Logement", "Restaurant", "Transport");
        // Créez une liste observable contenant les types


        // Définissez les éléments du ComboBox en utilisant la liste observable
//        User user = new User();
//        user = GuiLoginController.user;
        System.out.println("Setting reclamations");
        ReclamationCrud ps = new ReclamationCrud();

        List<Reclamation> reclamationList = ps.afficher();

        // Set Reclamations List
        int ReclamationColumn = 0;
        int ReclamationRow = 1;
        try {
            for (int i = 0; i < reclamationList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/RecItem/ReclamationItem.fxml"));
                VBox commentItem = fxmlLoader.load();
                ReclamationItem ReclamationItemController = fxmlLoader.getController();
                ReclamationItemController.setReviewData(reclamationList.get(i));

                if (ReclamationColumn == 1) {
                    ReclamationColumn = 0;
                    ++ReclamationRow;
                }
                commentsListContainer.add(commentItem, ReclamationColumn++, ReclamationRow);
                GridPane.setMargin(commentItem, new Insets(0, 10, 15, 10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reclamations are ready");
    }


//    public void afficherType(ObservableList<Reclamation> reclamationList) {
////        User user = new User();
////        user = GuiLoginController.user;
//        System.out.println("Setting reclamations");
//        ReclamationCrud ps = new ReclamationCrud();
//
//     //   ObservableList<Reclamation> reclamationList = ReclamationCrud.getAllByType(filtreTypeLog.getValue());
//
//        // Set Reclamations List
//        int ReclamationColumn = 0;
//        int ReclamationRow = 1;
//        try {
//            for (int i = 0; i < reclamationList.size(); i++) {
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/RecItem/ReclamationItem.fxml"));
//                VBox commentItem = fxmlLoader.load();
//                ReclamationItem ReclamationItemController = fxmlLoader.getController();
//                ReclamationItemController.setReviewData(reclamationList.get(i));
//
//                if (ReclamationColumn == 1) {
//                    ReclamationColumn = 0;
//                    ++ReclamationRow;
//                }
//                commentsListContainer.add(commentItem, ReclamationColumn++, ReclamationRow);
//                GridPane.setMargin(commentItem, new Insets(0, 10, 15, 10));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Reclamations are ready");
//
//
//
//
//
//    }

    public void afficherType(ObservableList<Reclamation> reclamationList) {
        // Clear the previous items in the commentsListContainer if required
        commentsListContainer.getChildren().clear();

        try {
            for (int i = 0; i < reclamationList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RecItem/ReclamationItem.fxml"));
                VBox commentItem = fxmlLoader.load();
                ReclamationItem ReclamationItemController = fxmlLoader.getController();
                ReclamationItemController.setReviewData(reclamationList.get(i));

                commentsListContainer.getChildren().add(commentItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Reclamations are ready");
    }
    @FXML
    private void handleTypeSelection() {

        String selectedType = filtreTypeLog.getValue(); // Récupérer le type de logement sélectionné
        if (selectedType != null && !selectedType.isEmpty()) {
            // Effectuer une action en fonction du type sélectionné, par exemple, afficher les logements de ce type
            // Vous pouvez appeler une méthode de votre service LogementCrud pour récupérer et afficher les logements de ce type
            System.out.println(selectedType);
            ObservableList<Reclamation> reclamations = ReclamationCrud.getAllByType(selectedType, GuiLoginController.user.getId());
            System.out.println(reclamations);
            afficherType(reclamations); // Mettre à jour l'affichage avec les logements du type sélectionné
        } else {
            // Gérer le cas où aucun type n'est sélectionné
            System.out.println("Veuillez sélectionner un type de logement.");
        }
    }
    @FXML
    void open_addReviewModel(MouseEvent event) throws SQLException {
        System.out.println("Opening modify/addview modal");
        HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
        submitBtn.setVisible(true);
        submitBtn.setDisable(false);

        addReviewsModel.setVisible(true);
    }

    public void close_addReviewsModel(MouseEvent mouseEvent) {
        this.commentInput.setText("");
        this.titleInput.setText("");
        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
        addReviewsModel.setVisible(false);

    }
    @FXML
    DatePicker dateAjout;
    //submit add/update action
    public void add_new_comment(MouseEvent mouseEvent) throws Exception {
        Reclamation reclamation = new Reclamation();
        System.out.println("controle saisie");
        this.nameInputErrorHbox.setVisible(false);
        this.DescriptionError.setText("*");
        this.TitleError.setText("*");
        this.TypeError.setText("*");
        if( titleInput.getText().isEmpty()){
            this.nameInputError.setText("Title cannot be empty");
            this.nameInputErrorHbox.setVisible(true);
            System.out.println("name");
            return;
        }
        if(commentInput.getText().isEmpty())
        {
            this.DescriptionError.setText("* Description cannot be empty.");
            return;
        }

        if(titleInput.getText().length() < 5){
            //      this.error.setText("Title must contain at least 5 characters.");
            //  this.error.setVisible(true);
            this.nameInputError.setText("Title must contain at least 5 characters");

            this.nameInputErrorHbox.setVisible(true);

            return;

        }
        if(TypeInput.getSelectionModel().getSelectedIndex()==-1)
        {
            //     this.error.setText("Please select a type for Reclamation");
            //   this.error.setVisible(true);
            this.TypeError.setText("* Please select a type for Reclamation");

            return;
        }

        if (commentInput.getText().length() < 10) {
            this.DescriptionError.setText("* Description must contain at least 10 characters.");
            return;
        }
        if (commentInput.getText().length() > 255) {
            this.DescriptionError.setText("* Description must contain at most 255 characters.");
            return;
        }
        if (titleInput.getText().length() > 15) {
            this.TitleError.setText("* Title must contain at most 15 characters.");
            return;
        }
        if (selectedImageFile == null)
        {
            error.setText("Please select an image.");
            this.error.setVisible(true);
            return;
        }

        uploadImage(selectedImageFile);
        reclamation.setImage(selectedImageFile.getName());
        String description = BadWordFilter.filterText(this.commentInput.getText().trim());
        int colonIndex = description.indexOf(":");
        int braceIndex = description.indexOf("}");
        System.out.println(description);
        String extractedContent = description.substring(colonIndex + 2, braceIndex - 1).trim();

        reclamation.setDescription_reclamation(extractedContent);
        reclamation.setTitre(titleInput.getText());
        reclamation.setType(TypeInput.getValue());
//        System.out.println("Signed in user = "+GuiLoginController.user.getId());
//        reclamation.setUserId(GuiLoginController.user.getId());

        reclamation.setDate(java.sql.Date.valueOf(LocalDate.now()));
        reclamation.setEtat(false);
      /*  if (containsBadWords(reclamation.getDescription_reclamation())) {
            // Le commentaire contient des mots inappropriés, ne pas l'ajouter à la review
            // Afficher un message à l'utilisateur pour lui demander de modifier son
            // commentaire
            TrayNotificationAlert.notif("Bad Word Detected", "you should not use bad words.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
            return;
        } else {
            review.setComment(comment);
        }*/

        ReclamationCrud rc = new ReclamationCrud();

        int id= rc.ajouterReturnsID(reclamation);
        NotificationService ns=new NotificationService();
        Notification newnotif=new Notification();
        newnotif.setCreatedAt(new java.util.Date());
        newnotif.setMessage("Vous avez réçu une nouvelle réclamation");
        newnotif.userId=0;
        newnotif.setReclamationId(id);
        ns.add(newnotif);

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/RecItem/ReclamationsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        utils.TrayNotificationAlert.notif("Reclamation", "Reclamation added successfully.",
                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        this.commentInput.setText("");
        this.titleInput.setText("");
        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
        addReviewsModel.setVisible(false);

    }
    // upload image symfony
    public void uploadImage(File imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpPost httpPost = new HttpPost("http://localhost:8000/upload-image");

        HttpEntity requestEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", imageFile, ContentType.APPLICATION_OCTET_STREAM, imageFile.getName())
                .build();

        httpPost.setEntity(requestEntity);
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);

        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            Header contentDispositionHeader = response.getFirstHeader("Content-Disposition");
            if (contentDispositionHeader != null) {
                String contentDisposition = contentDispositionHeader.getValue();
                String filename = extractFilenameFromContentDisposition(contentDisposition);
                System.out.println("Success upload. Filename: " + filename);
            } else {
                System.out.println("Success upload, but filename not found in the response");
            }
        } else {
            System.out.println("Failed upload");
        }
    }
    private String extractFilenameFromContentDisposition(String contentDisposition) {
        String filename = null;
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            String[] parts = contentDisposition.split(";");
            for (String part : parts) {
                if (part.trim().startsWith("filename=")) {
                    filename = part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
        }
        return filename;
    }
    public static Reclamation reclamation;


    // Update function reclamation
    public void update_reclamation(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {

        Reclamation reclamation = new Reclamation();
        this.nameInputErrorHbox.setVisible(false);
        this.DescriptionError.setText("*");
        this.TitleError.setText("*");
        this.TypeError.setText("*");
        if( titleInput.getText().isEmpty()){
            this.nameInputError.setText("Title cannot be empty");
            this.nameInputErrorHbox.setVisible(true);
            System.out.println("name");
            return;
        }
        if(commentInput.getText().isEmpty())
        {
            this.DescriptionError.setText("* Description cannot be empty.");
            return;
        }

        if(titleInput.getText().length() < 5){
            //      this.error.setText("Title must contain at least 5 characters.");
            //  this.error.setVisible(true);
            this.nameInputError.setText("Title must contain at least 5 characters");

            this.nameInputErrorHbox.setVisible(true);

            return;

        }
        if(TypeInput.getSelectionModel().getSelectedIndex()==-1)
        {
            //     this.error.setText("Please select a type for Reclamation");
            //   this.error.setVisible(true);
            this.TypeError.setText("* Please select a type for Reclamation");

            return;
        }

        if (commentInput.getText().length() < 10) {
            this.DescriptionError.setText("* Description must contain at least 10 characters.");
            return;
        }
        if (commentInput.getText().length() > 255) {
            this.DescriptionError.setText("* Description must contain at most 255 characters.");
            return;
        }
        if (titleInput.getText().length() > 15) {
            this.TitleError.setText("* Title must contain at most 15 characters.");
            return;
        }


        Reclamation rc=new Reclamation();

        if(selectedImageFile!=null) {
            System.out.println("Image is uploaded"+selectedImageFile.getName());
            uploadImage(selectedImageFile);
            ReclamationsListController_new.reclamation.setImage(selectedImageFile.getName());
        }else{
            System.out.println("Image is not uploaded");
            ReclamationsListController_new.reclamation.setImage(ReclamationsListController_new.reclamation.getImage());
        }
        System.out.println("Reclamation a modifier"+ReclamationsListController_new.reclamation);
        rc=ReclamationsListController_new.reclamation;
        //reclamation.setImage(selectedImageFile.getName());
        rc.setDescription_reclamation(commentInput.getText());
        rc.setTitre(titleInput.getText());
        rc.setType(TypeInput.getValue());
        rc.setDate(new Date(2020));
        rc.setId(ReclamationsListController_new.reclamation.getId());
        //rc.setDate(new Date());
        rc.setEtat(ReclamationsListController_new.reclamation.getEtat());
        System.out.println("Reclamation a modifier"+rc);
      /*  if (containsBadWords(reclamation.getDescription_reclamation())) {
            // Le commentaire contient des mots inappropriés, ne pas l'ajouter à la review
            // Afficher un message à l'utilisateur pour lui demander de modifier son
            // commentaire
            TrayNotificationAlert.notif("Bad Word Detected", "you should not use bad words.",
                    NotificationType.WARNING, AnimationType.POPUP, Duration.millis(2500));
            return;
        } else {
            review.setComment(comment);
        }*/

        ReclamationCrud reclamationCrud = new ReclamationCrud();
        //  if (value != 0) {
        reclamationCrud.modifier(rc);
        Parent fxml;
        System.out.println("########### comment input"+this.commentInput.getText());
        this.commentInput.setText("");
        this.titleInput.setText("");
        System.out.println("########### comment input after reset"+this.commentInput.getText());

        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
        addReviewsModel.setVisible(false);

        try {
            fxml = FXMLLoader.load(getClass().getResource("/RecItem/ReclamationsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }

        utils.TrayNotificationAlert.notif("Reclamation", "Reclamation modified successfully.",
                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

    }
    private File selectedImageFile;
    @FXML

    public void uploadImage(MouseEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);
        if (selectedImageFile != null) {
            try {
                javafx.scene.image.Image image = new Image(new FileInputStream(selectedImageFile));
                img.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void goToNavigate(ActionEvent event) {

            Controllers.RouterController router=new Controllers.RouterController();
            router.navigate("/fxml/AdminDashboard.fxml");

    }

    public void gotoAdmin(MouseEvent mouseEvent) {
        RouterController.navigate("/AdminDashboard/AdminDashboard.fxml");
    }



    public void close_viewReplyModel(MouseEvent mouseEvent) {
        System.out.println("Closing modal");
        this.replyInput.setText("");
        this.error.setVisible(false);
        this.viewReplyModel.setVisible(false);

    }

    public void close_viewReplyModal(MouseEvent mouseEvent) {
        System.out.println("Closing modal");
        this.error.setVisible(false);
        this.replyInput.setText("");
        this.viewReplyModel.setVisible(false);
    }

    public void OnclickTrier(ActionEvent actionEvent) {
    }

    //    public void searchquery(KeyEvent keyEvent) {
//        String selectedTitre = champRecherche.getText(); // Get the selected housing type
//            // Perform an action based on the selected type, such as displaying the housing of that type
//            // You can call a method from your LogementCrud service to retrieve and display the housing of that type
//            System.out.println(selectedTitre);
//            ObservableList<Reclamation> reclamations = ReclamationCrud.getAllByTitre(selectedTitre);
//            System.out.println(reclamations);
//            afficherType(reclamations); // Update the display with the selected type of housing
//
//    }
    public void searchquery(KeyEvent keyEvent) {
        String selectedTitre = champRecherche.getText(); // Get the selected housing type
        if (selectedTitre != null && !selectedTitre.isEmpty()) {
            System.out.println(selectedTitre);
            ObservableList<Reclamation> reclamations = ReclamationCrud.getAllByTitre(selectedTitre,GuiLoginController.user.getId());
            System.out.println(reclamations);
            afficherType(reclamations); // Update the display with the selected type of housing
        } else {
            System.out.println("Veuillez entrer un titre de réclamation.");
        }
        if (Objects.equals(champRecherche.getText(), ""))
        {
            ReclamationCrud ps = new ReclamationCrud();
            commentsListContainer.getChildren().clear();
            List<Reclamation> reclamationList = ps.afficher();

            // Set Reclamations List
            int ReclamationColumn = 0;
            int ReclamationRow = 1;
            try {
                for (int i = 0; i < reclamationList.size(); i++) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/RecItem/ReclamationItem.fxml"));
                    VBox commentItem = fxmlLoader.load();
                    ReclamationItem ReclamationItemController = fxmlLoader.getController();
                    ReclamationItemController.setReviewData(reclamationList.get(i));

                    if (ReclamationColumn == 1) {
                        ReclamationColumn = 0;
                        ++ReclamationRow;
                    }
                    commentsListContainer.add(commentItem, ReclamationColumn++, ReclamationRow);
                    GridPane.setMargin(commentItem, new Insets(0, 10, 15, 10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } // Update the display with the selected type of housing
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}