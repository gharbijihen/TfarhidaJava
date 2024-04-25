package edu.esprit.controller;

import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.servies.ReclamationCrud;
import edu.esprit.servies.ReponseCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
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
import java.util.List;
import java.util.ResourceBundle;

public class RecListAdminController implements Initializable {

    public HBox addReviewsModel;
    public TextArea commentInput;
    public TextField titleInput;
    public ComboBox<String> TypeInput;
    public HBox updateBtnContainer;
    public HBox submitBtn;
    public HBox updateBtn;

    @FXML
    private GridPane commentsListContainer;

    @FXML
    private VBox content_area;
private ImageView img;
    // private User user = null;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //    System.out.println("Time for fun?");
        updateBtnContainer.setVisible(false);
        addReviewsModel.setVisible(false);

        //TypeInput.getItems().addAll("Activité", "Logement", "Restaurant", "Transport");
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
                fxmlLoader.setLocation(getClass().getResource("/RecItemAdmin/RecItemAdmin.fxml"));
                VBox commentItem = fxmlLoader.load();
                RecItemAdminController ReclamationItemController = fxmlLoader.getController();
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
        this.updateBtn.setVisible(false);
        this.submitBtn.setVisible(true);
    }

    public void uploadImage(ActionEvent imageFile) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

    }
    public void add_new_comment(ActionEvent event)
    {

    }
    public static Reclamation reclamation;
    public void update_reclamation(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {

        Reclamation reclamation = new Reclamation();


        if (commentInput.getText().length() < 10) {
            utils.TrayNotificationAlert.notif("Réponse", "Réponse must contain at least 10 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        if (commentInput.getText().length() > 255) {
            utils.TrayNotificationAlert.notif("Réponse", "Réponse must contain at most 255 characters.",
                    NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
            return;
        }
        Reclamation rc=new Reclamation();

        ReclamationCrud reclamationCrud = new ReclamationCrud();
        ReponseCrud reponseCrud=new ReponseCrud();

        if(RecListAdminController.reclamation.getReponseid()>0)
        {
            Reponse newReponse=new Reponse();
            newReponse.setDescription(this.commentInput.getText());
            newReponse.setDate(new Date(2020));
            int reponseid =reponseCrud.ajouterreturnsID(newReponse);
            rc = RecListAdminController.reclamation;
            rc.setReponseid(reponseid);
            reclamationCrud.modifier(rc);
        }else {
            rc = RecListAdminController.reclamation;
            Reponse newReponse=new Reponse();
            newReponse.setDescription(this.commentInput.getText());
            newReponse.setDate(new Date(2020));
            int reponseID = reponseCrud.ajouterreturnsID(newReponse);
            rc = RecListAdminController.reclamation;
            rc.setReponseid(reponseID);
            rc.setEtat(true);
            reclamationCrud.modifier(rc);
        }
        System.out.println("Reclamation a répondre"+RecListAdminController.reclamation);
        //reclamation.setImage(selectedImageFile.getName());

        Parent fxml;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/RecItemAdmin/RecListAdmin.fxml"));
            content_area.getChildren().removeAll();
            content_area.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        utils.TrayNotificationAlert.notif("Réponse", "Réponse added successfully.",
                NotificationType.SUCCESS, AnimationType.POPUP, Duration.millis(2500));

    }

    public void goToNavigate(ActionEvent event) {
        RouterController router=new RouterController();
        router.navigate("/back.fxml");

    }

    public void goToClient(MouseEvent mouseEvent) {
        RouterController.navigate("/ClientDashboard/ClientDashboard.fxml");
    }
}
