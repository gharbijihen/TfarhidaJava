package Controllers;

import Entities.User;
import Service.ServiceUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileDetailsControllers implements Initializable {
    public static User user = new User();
    public static boolean isUser = false;
    private int userId;
    private byte[] imageData;
    @FXML
    private ImageView imageView;
    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    private DatePicker Date;

    @FXML
    private TextField Email;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Password;

    @FXML
    private TextField Prenom;

    @FXML
    private ComboBox<String> Type;

    @FXML
    private TextField address;

    @FXML
    private Label adresseLabel;

    @FXML
    private AnchorPane bord;

    @FXML
    private ImageView btnReturn;

    @FXML
    private Button btnSignup;

    @FXML
    private Button btnUploadImage;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private TextField confirmpass;



    @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;

    @FXML
    private Label prenomLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Type.getItems().addAll(Role.values());
        //Type.setDisable(false);
        loadData();
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to users CRUD");
                RouterController.navigate("/fxml/UsersCRUD.fxml");
            }
        });
    }




    public void init(int userId) {
        this.userId = userId;
        loadData();
    }
    private File selectedImageFile;
    public boolean validateInputsAndProceed() {
        return true;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void ModifiyUser(ActionEvent event) {
       /* if(!validateInputsAndProceed()) return;
        // String nom = Nom.getText();
        //String prenom = Prenom.getText();
        //String addres = address.getText();
        //String Num_tel = num_tel.getText();
        //String email = Email.getText();
        //String password  = Password.getText();
        //String confirmPass=confirmpass.getText();
        String type = Type.getValue();

        user.setRoles("[\""+type+"\"]");
        System.out.println(user);
        // Role type = Type.getValue(); // Get the selected item from ComboBox
        // java.util.Date dateJoined = java.sql.Date.valueOf(Date.getValue());
        //   System.out.println(nom+prenom);
        try {
            // Update the user

            serviceUser.update(user);
            showSuccessMessage("Votre utilisateur a été modifiée avec succées");
            MouseEvent e=null;
            if(ModifyUserController.isUser)
            {
                ModifyUserController.isUser = false;
                RouterController.navigate("/fxml/ClientDashboard.fxml");
            }
            this.returnTo(e);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        RouterController.navigate("ModifierProfile.fxml");
    }
    @FXML
    void returnTo(MouseEvent event) {


    }
    @FXML
    TextField Username;
    @FXML
    TextField email;
    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    TextField isverified;
    @FXML
    TextField numero;

    private void loadData() {

        // if (user != null) {
        System.out.println(user);
        Username.setText(user.getUsername());
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        //Type.getItems().addAll(roles);
        // Type.setValue(Role.valueOf(user.getRoles()));
       /// String displayRole = user.getRoles().substring(2, user.getRoles().length() - 2); // Remove "[ROLE_" and "]"
       // Type.setValue(displayRole);
        email.setText(user.getEmail());
       // isverified.setText(user.getIs_verified().toString());
        numero.setText(String.valueOf(user.getNumero()));
        nom.setText(user.getFirst_name());
        prenom.setText(user.getLast_name());
        //     Password.setText(user.getPassword());

        //   imageData = user.getImageData(); // Load image data
        // if (imageData != null && imageData.length > 0) {
        //   imageView.setImage(new javafx.scene.image.Image(new ByteArrayInputStream(user.getImageData())));
        //}
        //} else {
        //  System.out.println("user not found.");
        //}
        // } catch (SQLException e) {
        //   e.printStackTrace();
        // System.out.println("Error");
        //}

    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ChangePassword(ActionEvent actionEvent) {
        RouterController.navigate("/fxml/ChangerMotDePasse.fxml");
    }
}
