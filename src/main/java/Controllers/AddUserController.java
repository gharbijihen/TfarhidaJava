package Controllers;



import Entities.Role;
import Entities.User;
import Service.ServiceUser;
import javafx.collections.FXCollections;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML
    private DatePicker Date;

    @FXML
    private TextField Email;

    @FXML
    private TextField Nom;

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
    private ImageView imageView;

    @FXML
    private Label nomLabel;

    @FXML
    private TextField num_tel;




    private File selectedImageFile;

    private ServiceUser serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
        Type.getItems().addAll(roles);
        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Returning to userCrud");
                RouterController.navigate("/fxml/UsersCRUD.fxml");
            }
        });
    }
    public boolean validateInputsAndProceed() {

        String numero = this.numero.getText();
        if (!numero.matches("\\d{8}")) {
            showAlert("le numéro est invalide");
            return false;
        }
        if (Username.getText().isEmpty()) {
            showAlert("Le username est requis");
            return false;
        }
        AddUserController.user.setUsername(Username.getText());


        if (email.getText().isEmpty()) {
            showAlert("Le email est requis");
            return false;
        }
        AddUserController.user.setEmail(email.getText());

        if (prenom.getText().isEmpty()) {
            showAlert("prenom est requis");
            return false;
        }
        AddUserController.user.setFirst_name(prenom.getText());

        if (this.numero.getText().isEmpty()) {
            showAlert("Numéro de téléphone est requis");
            return false;
        }

        if (nom.getText().isEmpty()) {
            showAlert("Nom est requis");
            return false;
        }
        AddUserController.user.setLast_name(nom.getText());
        AddUserController.user.setNumero(Integer.parseInt(this.numero.getText()));




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

        AddUserController.user.setPassword(encryptedPassword);

        // Vérifier si l'adresse email est valide et afficher une erreur si elle ne l'est pas
        String email = this.email.getText();
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("L'adresse email est invalide");
            return false;
        }
        String selectedtype = Type.getValue(); // Get the selected item from the ComboBox
        if (selectedtype == null || selectedtype.isEmpty()) {
            showAlert("Selectionnez un role svp");
            return false;
        }
        AddUserController.user.setIs_verified(isverified.isSelected());
        String type = Type.getValue();

        user.setRoles("[\""+type+"\"]");
// Vérifier si le mot de passe et sa confirmation correspondent et afficher une erreur si ce n'est pas le cas
        /*      if (this.password.equals(confirmpass)){
            showAlert("Les mots de passe ne correspondent pas");
            return false;
        }*/


        return true;

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        String type = Type.getValue(); // Get the selected item from ComboBox
        System.out.println(user);
        serviceUser.add(user);
        showSuccessMessage("Utilisateur ajoutée avec success");
        RouterController.navigate("/fxml/UsersCRUD.fxml");
    }


    @FXML
    public void returnTo(ActionEvent actionEvent) {
        System.out.println("Returning to users CRUD");
        RouterController.navigate("/fxml/UsersCRUD.fxml");
    }


    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Message");
        alert.setContentText(message);
        alert.showAndWait();
    }

}