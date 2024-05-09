package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ClientDashboardController {
    @FXML
    private Label ClientNameLabel;

    public void initialize() {

       // GuiLoginController guilogin = new GuiLoginController();
       // String name="Bienvenue "+guilogin.user.getPrenom()+"!";
       // ClientNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
        RouterController.navigate("/fxml/login.fxml");
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ClientDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {
        ModifierProfile.user=GuiLoginController.user;
        ModifierProfile.isUser=true;
        ProfileDetailsControllers.user=GuiLoginController.user;
        RouterController.navigate("/fxml/ProfileDetails.fxml");

    }


    public void goToReclamations(MouseEvent mouseEvent) {
        System.out.println("GOING TO RECLAMATIONS");

        RouterController.navigate("/ClientDashboard/ClientDashboard.fxml");
    }

    public void goToTrans(MouseEvent event) {
        System.out.println("GOING TO TRANS");
        RouterController.navigate("/MoyenFxml/afficherMoyenF.fxml");
    }

    public void gotoLogement(MouseEvent event) {
        System.out.println("GOING TO TRANS");
        RouterController.navigate("/LogementFxml/indexLogement.fxml");
    }

    public void goToLogement(MouseEvent event) {
    }
}

