package edu.esprit.controller;

import com.itextpdf.text.*;
import edu.esprit.entites.Notification;
import edu.esprit.entites.Reclamation;
import edu.esprit.entites.Reponse;
import edu.esprit.servies.EmailSenderApp;
import edu.esprit.servies.NotificationService;
import edu.esprit.servies.ReclamationCrud;
import edu.esprit.servies.ReponseCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
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


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
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
  @FXML
  private TextField searchField;
  private final ReclamationCrud reclamationCrud = new ReclamationCrud();
  private ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();



  public ObservableList<Reclamation> getAllReclamationCard() throws SQLException {
    reclamationList.addAll(reclamationCrud.afficher());
    System.out.println(reclamationList);
    return reclamationList ;
  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {

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

    afficherReclamations();
    NotificationService ns = new NotificationService();
    /// Intégration, changer user ID ici
//
//    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//      try {
//        updateSearchResults(newValue);
//      } catch (SQLException e) {
//        e.printStackTrace();
//        // Gérer l'exception
//      }
//    });
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


  public void afficherReclamations(){
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
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
      System.out.println("Reclamations are ready");
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
    Reponse newReponse=new Reponse();

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
    Reponse reponse=new Reponse();
    ReclamationCrud reclamationCrud = new ReclamationCrud();
    ReponseCrud reponseCrud=new ReponseCrud();
    // If reclamation has a reply, it modifies it, if not, it creates a new reply and updates the reponse_id field
    if(RecListAdminController.reclamation.getReponseid()>0)
    {

      newReponse.setDescription(this.commentInput.getText());
      newReponse.setDate(new Date(2024));
      int reponseid =reponseCrud.ajouterreturnsID(newReponse);
      rc = RecListAdminController.reclamation;
      rc.setReponseid(reponseid);
      reclamationCrud.modifier(rc);
    }else {
      rc = RecListAdminController.reclamation;

      newReponse.setDescription(this.commentInput.getText());
      newReponse.setDate(new Date(2024));
      int reponseID = reponseCrud.ajouterreturnsID(newReponse);
      rc = RecListAdminController.reclamation;
      rc.setReponseid(reponseID);
      rc.setEtat(true);
      reclamationCrud.modifier(rc);
    }
    SmsController.Sms();
    EmailSenderApp.sendEmail("tayssirsboui@gmail.com","Reponse Reclamation", "Vous avez reçu une réponse à votre réclamation: \n"+ newReponse.getDescription());
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
    router.navigate("/fxml/AdminDashboard.fxml");

  }

  public void goToClient(MouseEvent mouseEvent) {
    RouterController.navigate("/ClientDashboard/ClientDashboard.fxml");
  }

  public void open_notifModel(MouseEvent mouseEvent) {
    NotificationService ns=new NotificationService();
    List<Notification> notifList = ns.getUserNotifications(0);
    int unreadNotifCount = 0;

    for (Notification notification : notifList) {
      if (!notification.getSeen()) {
        unreadNotifCount++;
      }
    }
    totalNotif.setText(String.valueOf(unreadNotifCount));
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
  private void pdf(ActionEvent event) throws SQLException {
    // Afficher la boîte de dialogue de sélection de fichier
    FileChooser fileChooser = new FileChooser();
    String logoPath = "C:/Users/MSI/Downloads/tfarhida2/src/main/resources/assets/tfarhida.png";
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
        // Ajout du logo
        try {
          Image logo = Image.getInstance(logoPath);
          logo.scaleToFit(150, 150);
          document.add(logo);
        } catch (IOException e) {
          e.printStackTrace();
        }

        // Ajout de la date actuelle
        LocalDate currentDate = LocalDate.now();
        Font fontDate = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);
        Paragraph dateParagraph = new Paragraph("Date: " + currentDate.toString(), fontDate);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        // Titre du document
        Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, BaseColor.BLUE);
        Paragraph titleParagraph = new Paragraph("Liste des Reclamations", fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph("\n"));

        // Créer une table pour afficher les réclamations
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{20, 30, 40, 15, 15, 30});

        // Définir le style de bordure du tableau et des cellules

        table.getDefaultCell().setBorderWidth(0.5f);
        table.getDefaultCell().setBorderColor(BaseColor.GRAY);
        // Créer une table pour afficher les réclamations
//        PdfPTable table = new PdfPTable(6);
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10);

        // Ajouter les en-têtes de colonnes
        String[] headers = {"Titre", "Type", "Description", "Date", "État", "Image"};
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        for (String header : headers) {
          PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          table.addCell(cell);
        }

        // Ajouter les données des réclamations à la table
        for (Reclamation reclamation : reclamationList) {
          table.addCell(reclamation.getTitre());
          table.addCell(reclamation.getType());
          table.addCell(reclamation.getDescription_reclamation());
          table.addCell(reclamation.getDate().toString());
          table.addCell(reclamation.getEtat() ? "Traitée" : "Non traitée");

          // Ajouter l'image à la cellule
          Image img = loadImage(reclamation.getImage());
          if (img != null) {
            img.scaleToFit(100, 100); // Scale image to fit cell
            PdfPCell imageCell = new PdfPCell(img, true);
            table.addCell(imageCell);
          } else {
            table.addCell("Image non disponible");
          }
        }

        document.add(table);
        document.close();

        System.out.println("Le fichier PDF a été généré avec succès.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  // Méthode pour obtenir les données de l'image à partir du chemin du fichier
  private byte[] getImageData(String imagePath) {
    try {
      File imageFile = new File(imagePath);
      BufferedImage bufferedImage = ImageIO.read(imageFile);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
  @FXML
  void stat(ActionEvent event) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard/Statistiques.fxml"));
    Parent root = loader.load();

    // Créer une nouvelle fenêtre pour afficher le formulaire d'ajout
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
  }

  // Helper method to load an image from a file path, with error handling
  private Image loadImage(String imagePath) throws BadElementException {
    File imageFile = new File(imagePath);
    if (imageFile.exists() && imageFile.isFile()) {
      try {
        return Image.getInstance(imagePath);
      } catch (IOException e) {
        System.err.println("Error loading image: " + e.getMessage());
        return null; // Return null if there's an error loading the image
      }
    } else {
      return null; // Return null if image file doesn't exist or is not a file
    }
  }


}
