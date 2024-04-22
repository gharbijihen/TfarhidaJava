package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AdminDashboardController {
    @FXML
    private Label adminNameLabel;

    public void initialize() {
        // Set the admin name in the label
    //    GuiLoginController guilogin = new GuiLoginController();
      //  String name="Bienvenue "+guilogin.user.getPrenom()+"!";
       // adminNameLabel.setText(name);
    }
    public void goToLogn(MouseEvent mouseEvent) {
    }

    public void goToNavigate(ActionEvent actionEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/AdminDashboard.fxml");
    }

    public void goToUsers(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/UsersCrud.fxml");
    }

    public void goToActivities(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/fxml/ActivitiesCRUD.fxml");
    }

    public void goToCommands(MouseEvent mouseEvent) {
    }

    public void goToReclamations(MouseEvent mouseEvent) {
    }

    public void goToEvent(MouseEvent mouseEvent) {
    }

    public void goToLivraisons(MouseEvent mouseEvent) {
    }
}
