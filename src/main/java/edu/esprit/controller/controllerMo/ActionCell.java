package edu.esprit.controller.controllerMo;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.servies.Moyen_transportCrud;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

import javafx.scene.paint.Color;
import Controllers.GuiLoginController;


public class ActionCell extends TableCell<Moyen_transport, Void> {
    private final Button acceptButton;
    private final Button refuseButton;
    private final FontAwesomeIconView acceptIcon;
    private final FontAwesomeIconView refuseIcon;
    private final Moyen_transportCrud moyenCrud = new Moyen_transportCrud();


    public ActionCell() {
        acceptIcon = createIcon(FontAwesomeIcon.CHECK, Color.GREEN);
        refuseIcon = createIcon(FontAwesomeIcon.TIMES, Color.RED);


        acceptButton = new Button();
        acceptButton.setGraphic(acceptIcon);
        acceptButton.setOnAction(event -> {
            Moyen_transport moyen = getTableView().getItems().get(getIndex());
            moyen.setValide(true);
            moyen.setUserid(GuiLoginController.user.getId());

            getTableView().refresh();
            MailController mailer = new MailController();
            mailer.sendEmail("alaeddine.souidi@esprit.tn",moyen);

            try {
                moyenCrud.modifier(moyen);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        refuseButton = new Button();
        refuseButton.setGraphic(refuseIcon);
        refuseButton.setOnAction(event -> {
            Moyen_transport moyen = getTableView().getItems().get(getIndex());
            moyen.setValide(false);
            getTableView().refresh();
            try {
                moyenCrud.modifier(moyen);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            HBox hbox = new HBox(acceptButton, refuseButton);
            hbox.setAlignment(Pos.CENTER); // Center align the buttons
            setGraphic(hbox);
        }
    }

    private FontAwesomeIconView createIcon(FontAwesomeIcon icon, Color color) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setFill(color);
        iconView.setSize("16px");
        return iconView;
    }
}