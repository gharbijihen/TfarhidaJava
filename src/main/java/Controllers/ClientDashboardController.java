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



}

