package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import edu.esprit.servies.ReclamationCrud;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.List;
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

    @FXML
        private GridPane commentsListContainer;

        @FXML
        private VBox content_area;
        @FXML
        private ImageView img;



       // private User user = null;



        @Override
        public void initialize(URL url, ResourceBundle rb) {
            updateBtnContainer.setVisible(false);
            addReviewsModel.setVisible(false);
            TypeInput.getItems().addAll("Activité", "Logement", "Restaurant", "Transport");
            // recuperer user connecté
         //   user = new User();
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
            }
            System.out.println("Reclamations are ready");
        }


        @FXML
        void open_addReviewModel(MouseEvent event) throws SQLException {
            HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
            addReviewsModel.setVisible(true);
        }

    public void close_addReviewsModel(MouseEvent mouseEvent) {
        addReviewsModel.setVisible(false);
        this.commentInput.setText("");
        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
    }
    @FXML
    DatePicker dateAjout;
    public void add_new_comment(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        Reclamation reclamation = new Reclamation();

       /* review.setUser_id(user.getId());
        review.setProduct_id(Collecte.getIdProduit());
        review.setTitle(titleInput.getText());
        review.setComment(commentInput.getText());

        review.setValue(value);

        String comment = commentInput.getText();*/
        if(TypeInput.getSelectionModel().getSelectedIndex()==-1)
        {
            utils.TrayNotificationAlert.notif("Reclamation", "Please select a type.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if( titleInput.getText().isEmpty() || commentInput.getText().isEmpty()){
            utils.TrayNotificationAlert.notif("Reclamation", "Please fill all the fields.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if(titleInput.getText().isEmpty() || commentInput.getText().isEmpty()){
            utils.TrayNotificationAlert.notif("Reclamation", "Please fill all the fields.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if(titleInput.getText().length() < 5){
            utils.TrayNotificationAlert.notif("Reclamation", "Title must contain at least 5 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (commentInput.getText().length() < 10) {
            utils.TrayNotificationAlert.notif("Reclamation", "Description must contain at least 10 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (commentInput.getText().length() > 255) {
            utils.TrayNotificationAlert.notif("Reclamation", "Description must contain at most 255 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (titleInput.getText().length() > 15) {
            utils.TrayNotificationAlert.notif("Reclamation", "Title must contain at most 15 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (selectedImageFile == null)
        {
            utils.TrayNotificationAlert.notif("Reclamation", "Please select an image.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        uploadImage(selectedImageFile);
        reclamation.setImage(selectedImageFile.getName());
        reclamation.setDescription_reclamation(commentInput.getText());
        reclamation.setTitre(titleInput.getText());
        reclamation.setType(TypeInput.getValue());

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
      //  if (value != 0) {
            rc.ajouter(reclamation);
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
        addReviewsModel.setVisible(false);
        this.commentInput.setText("");
        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
    }
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
    public void update_reclamation(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {

        Reclamation reclamation = new Reclamation();

       /* review.setUser_id(user.getId());
        review.setProduct_id(Collecte.getIdProduit());
        review.setTitle(titleInput.getText());
        review.setComment(commentInput.getText());

        review.setValue(value);

        String comment = commentInput.getText();*/
        if(TypeInput.getValue().isEmpty() || titleInput.getText().isEmpty() || commentInput.getText().isEmpty()){
            utils.TrayNotificationAlert.notif("Reclamation", "Please fill all the fields.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if(titleInput.getText().isEmpty() || commentInput.getText().isEmpty() || TypeInput.getValue().isEmpty()){
            utils.TrayNotificationAlert.notif("Reclamation", "Please fill all the fields.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if(titleInput.getText().length() < 5){
            utils.TrayNotificationAlert.notif("Reclamation", "Title must contain at least 5 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (commentInput.getText().length() < 10) {
            utils.TrayNotificationAlert.notif("Reclamation", "Comment must contain at least 10 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (commentInput.getText().length() > 255) {
            utils.TrayNotificationAlert.notif("Reclamation", "Comment must contain at most 255 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (titleInput.getText().length() > 15) {
            utils.TrayNotificationAlert.notif("Reclamation", "Title must contain at most 15 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
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
        try {
            fxml = FXMLLoader.load(getClass().getResource("/RecItem/ReclamationsList.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        utils.TrayNotificationAlert.notif("Reclamation", "Reclamation modified successfully.",
                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));
        addReviewsModel.setVisible(false);
        this.commentInput.setText("");
        this.TypeInput.getSelectionModel().select(0);
        this.selectedImageFile=null;
        this.img.setImage(null);
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
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
    }

    public void gotoAdmin(MouseEvent mouseEvent) {
            RouterController.navigate("/AdminDashboard/AdminDashboard.fxml");
    }
}

