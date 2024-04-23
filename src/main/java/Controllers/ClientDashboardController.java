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
        ModifyUserController.user=GuiLoginController.user;
        ModifyUserController.isUser=true;
        RouterController.navigate("/fxml/ModifyUser.fxml");

    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ActivitiesCRUD.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/Complaint.fxml");
    }


}

