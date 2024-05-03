package edu.esprit.controller;

import edu.esprit.entites.Notification;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.servies.NotificationService;
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
import javafx.scene.control.Label;
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


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
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
  public VBox notifModel;
  public GridPane notifContainer;
  public Text totalNotif;
  public Label error;
  @FXML
  private GridPane commentsListContainer;

  @FXML
  private VBox content_area;

  private int notifModel_isOpen = 0;

  private ImageView img;
  // private User user = null;



  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //    System.out.println("Time for fun?");
    updateBtnContainer.setVisible(false);
    addReviewsModel.setVisible(false);
    if(error !=null)
      error.setVisible(false);
    if (notifModel != null) {
      notifModel.setVisible(false);
    }

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

    NotificationService ns = new NotificationService();
    /// Intégration, changer user ID ici

    List<Notification> notifList = ns.getUserNotifications(0);
    int unreadNotifCount = 0;

    for (Notification notification : notifList) {
      if (!notification.getSeen()) {
        unreadNotifCount++;
      }
    }
    notifList.sort(new Comparator<Notification>() {
      @Override
      public int compare(Notification n1, Notification n2) {
        boolean seen1 = n1.getSeen();
        boolean seen2 = n2.getSeen();

        if (seen1 == seen2) {
          return 0;
        } else if (seen1) {
          return 1;
        } else {
          return -1;
        }
      }
    });

    if(totalNotif!=null)
      totalNotif.setText(String.valueOf(unreadNotifCount));
    if(notifContainer!=null) {
      int column = 0;
      int row = 1;
      try {
        for (int i = 0; i < notifList.size(); i++) {

          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("/AdminDashboard/notifItem.fxml"));
          HBox notifItem = fxmlLoader.load();
          NotifItemController notifController = fxmlLoader.getController();
          notifController.setNotifData(notifList.get(i));

          if (column == 1) {
            column = 0;
            ++row;
          }
          notifContainer.add(notifItem, column++, row);
          // GridPane.setMargin(notifItem, new Insets(10));
          GridPane.setMargin(notifItem, new Insets(0, 20, 20, 10));

        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Notifications are ready");
  }


  @FXML
  void open_addReviewModel(MouseEvent event) throws SQLException {
    try {
      ReponseCrud reponseCrud=new ReponseCrud();
      Reponse rep = reponseCrud.getById(reclamation.getReponseid());
      System.out.println("Reponse : "+rep);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    HBox addReviewsModel = (HBox) ((Node) event.getSource()).getScene().lookup("#addReviewsModel");
    addReviewsModel.setVisible(true);
  }

  public void close_addReviewsModel(MouseEvent mouseEvent) {
    this.error.setVisible(false);

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
  public void submitReponse(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, SQLException {

    error.setVisible(false);

    if (commentInput.getText().length() < 10) {
      // utils.TrayNotificationAlert.notif("Réponse", "Réponse must contain at least 10 characters.",
      //       NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
      error.setText("Réponse must contain at least 10 characters.");
      error.setVisible(true);
      return;
    }
    if (commentInput.getText().length() > 255) {
      // utils.TrayNotificationAlert.notif("Réponse", "Réponse must contain at most 255 characters.",
      //       NotificationType.ERROR, AnimationType.POPUP, Duration.millis(2500));
      error.setText("Réponse must contain at most 255 characters.");
      error.setVisible(true);
      return;

    }
    Reclamation rc=new Reclamation();

    ReclamationCrud reclamationCrud = new ReclamationCrud();
    ReponseCrud reponseCrud=new ReponseCrud();
    // If reclamation has a reply, it modifies it, if not, it creates a new reply and updates the reponse_id field
    if(RecListAdminController.reclamation.getReponseid()>0)
    {
      Reponse newReponse=new Reponse();
      newReponse.setDescription(this.commentInput.getText());
      newReponse.setDate(new Date(2024));
      int reponseid =reponseCrud.ajouterreturnsID(newReponse);
      rc = RecListAdminController.reclamation;
      rc.setReponseid(reponseid);
      reclamationCrud.modifier(rc);
    }else {
      rc = RecListAdminController.reclamation;
      Reponse newReponse=new Reponse();
      newReponse.setDescription(this.commentInput.getText());
      newReponse.setDate(new Date(2024));
      int reponseID = reponseCrud.ajouterreturnsID(newReponse);
      rc = RecListAdminController.reclamation;
      rc.setReponseid(reponseID);
      rc.setEtat(true);
      reclamationCrud.modifier(rc);
    }
    SmsController.Sms();
    System.out.println("Reclamation a répondre"+RecListAdminController.reclamation);
    //reclamation.setImage(selectedImageFile.getName());
    this.commentInput.setText("");
    this.updateBtn.setVisible(false);
    this.submitBtn.setVisible(true);
    addReviewsModel.setVisible(false);
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

  public void open_notifModel(MouseEvent mouseEvent) {
    if (notifModel_isOpen == 0) {
      notifModel.setVisible(true);
      notifModel_isOpen = 1;
      return;
    }

    if (notifModel_isOpen == 1) {
      notifModel.setVisible(false);
      notifModel_isOpen = 0;
    }
  }


  @FXML
  private void pdf(MouseEvent event) throws SQLException {
    // Afficher la boîte de dialogue de sélection de fichier
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Enregistrer le fichier PDF");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
    File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

    if (selectedFile != null) {
      // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
      // Récupérer la liste des visites médicales
      ReclamationCrud ps = new ReclamationCrud();
      List<Reclamation> reclamationList =  ps.afficher();;

      try {
        // Créer le document PDF
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
        document.open();

        // Titre du document
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("Liste des réclamations", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Créer une table pour afficher les visites médicales
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Ajouter les en-têtes de colonnes
        String[] headers = {"titre", "type", "description_reclamation", "Date","etat", "image"};
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        for (String header : headers) {
          PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          table.addCell(cell);
        }

        // Ajouter les données des visites médicales
        for (Reclamation reclamation : reclamationList) {
          table.addCell(String.valueOf(reclamation.getTitre()));
          table.addCell(String.valueOf(reclamation.getType()));
          table.addCell(reclamation.getDescription_reclamation());
          table.addCell(reclamation.getDate().toString());
          table.addCell(reclamation.getEtat() ? "Traitée" : "Non traitée");
          table.addCell(reclamation.getDescription_reclamation());

        }

        document.add(table);
        document.close();

        System.out.println("Le fichier PDF a été généré avec succès.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }



}
