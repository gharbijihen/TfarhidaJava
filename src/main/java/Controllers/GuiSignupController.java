package Controllers;




import java.util.regex.Pattern;
import Entities.Role;
import Entities.User;
import Service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GuiSignupController implements Initializable {

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
    private ComboBox<Role> Type;

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
    private ImageView imageView;

    @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;



    private File selectedImageFile;

    private ServiceUser serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField password;
    @FXML
    TextField Username;
    @FXML
    TextField email;
    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    CheckBox isverified;
    @FXML
    TextField numero;
    public boolean validateInputsAndProceed() {
      /*  if (
                selectedImageFile == null) {
            showAlert("Selectionner une image stp.");
            return false; // Stop the process
        }*/
        if (Username.getText().isEmpty()) {
            showAlert("Le username est requis");
            return false;
        }
        GuiSignupController.user.setUsername(Username.getText());


        if (email.getText().isEmpty()) {
            showAlert("Le email est requis");
            return false;
        }
        GuiSignupController.user.setEmail(email.getText());

        if (prenom.getText().isEmpty()) {
            showAlert("prenom est requis");
            return false;
        }
        GuiSignupController.user.setFirst_name(prenom.getText());

        if (numero.getText().isEmpty()) {
            showAlert("Numéro de téléphone est requis");
            return false;
        }

        String numeroValue = numero.getText();

// Validate that numero contains only numeric characters
        if (!Pattern.matches("\\d+", numeroValue)) {
            showAlert("Numéro de téléphone ne doit contenir que des chiffres");
            return false;
        }

// Validate that numero has a length of exactly 8 digits
        if (numeroValue.length() != 8) {
            showAlert("Numéro de téléphone doit avoir une longueur de 8 chiffres");
            return false;
        }
        if (nom.getText().isEmpty()) {
            showAlert("Nom est requis");
            return false;
        }
        GuiSignupController.user.setLast_name(nom.getText());
        GuiSignupController.user.setNumero(Integer.parseInt(numero.getText()));



        if (password.getText().isEmpty()) {
            showAlert("password est requiq");
            return false;
        }

        String password  = this.password.getText();
        if (password.length() < 8) {
            showAlert("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        GuiSignupController.user.setPassword(encryptedPassword);

        // Vérifier si l'adresse email est valide et afficher une erreur si elle ne l'est pas
        String email = this.email.getText();
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("L'adresse email est invalide");
            return false;
        }

// Check if email already exists
        ServiceUser userService = new ServiceUser();
        try {
            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                showAlert("Cette adresse email est déjà utilisée");
                return false;
            }
        } catch (SQLException e) {
            // Handle any exceptions
            e.printStackTrace();
            return false;
        }

        GuiSignupController.user.setIs_verified(false);

        GuiSignupController.user.setRoles("[\"ROLE_USER\"]");
// Vérifier si le mot de passe et sa confirmation correspondent et afficher une erreur si ce n'est pas le cas
        /*      if (this.password.equals(confirmpass)){
            showAlert("Les mots de passe ne correspondent pas");
            return false;
        }*/

        System.out.println("DATA IS SET");
        System.out.println("NOM"+nom.getText());
        System.out.println(ModifyUserController.user);

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
    public void returnTo(ActionEvent actionEvent) {
        System.out.println("Returning to ");
        RouterController.navigate("/fxml/Home.fxml");
    }


    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    System.out.println("OK button clicked");
                    RouterController.navigate("/fxml/login.fxml");
                });

    }

    public static User user = new User();
    @FXML
    void AddUser(ActionEvent event) throws SQLException {
        if(!validateInputsAndProceed()) return;
      /*  String nom = Nom.getText();
        String prenom = Prenom.getText();
        String addres = address.getText();
        String Num_tel = num_tel.getText();
        String email = Email.getText();
        String password  = this.password.getText();
      //  String confirmPass=confirmpass.getText();*/
        //java.util.Date date = java.sql.Date.valueOf(Date.getValue());
        serviceUser.add(GuiSignupController.user);
        showSuccessMessage("Votre inscription a été enregistrée avec succès");
    }

}



