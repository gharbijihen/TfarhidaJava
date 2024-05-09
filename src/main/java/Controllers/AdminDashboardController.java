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
        RouterController router=new RouterController();
        router.navigate("/fxml/login.fxml");
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
        router.navigate("/ActiviteFxml/ActiviteAffB.fxml");
    }
    public void goToReclamations(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/AdminDashboard/AdminDashboard.fxml");
    }
    public void goToLogements(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/LogementFxml/LogementAffB.fxml");
    }



public void goToCategorie(MouseEvent mouseEvent){

    RouterController router=new RouterController();
    router.navigate("/ActiviteFxml/CategorieaffB.fxml");
}

    public void goToMoyen(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/MoyenFxml/afficherMoyenB.fxml");
    }

    public void goToTrajet(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/MoyenFxml/afficherTrajet.fxml");
    }

    public void goToResto(MouseEvent mouseEvent) {
        RouterController router=new RouterController();
        router.navigate("/FXMLRes/restaurantaffb.fxml");

    }
}