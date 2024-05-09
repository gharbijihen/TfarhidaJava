package edu.esprit.controller.Activite;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import edu.esprit.entites.Activite;
import edu.esprit.entites.QRcode;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
public class QrCodeActivite {



        @FXML
        private ImageView qrCodeImageView;
    @FXML
    private Button closeButton;
        private String log;

        public void initialize() {
            // Initialisation, par exemple, si nécessaire
        }

        public void setAct(Activite activite) {
            if (activite != null) {
                log = "nom:" + activite.getNom() +
                        " localisation:" + activite.getLocalisation() +
                        " description_act:" + activite.getDescription_act();
                generateQRCode();  // Générer le code QR lorsque les informations du logement sont définies
            }
        }

        private void generateQRCode() {
            if (log == null || log.isEmpty()) {
                System.out.println("Les informations sur l'activitée sont pas définies.");
                return;
            }

            String contentToEncode = log;
            int qrCodeSize = 900;

            try {
                QRcode qrGenerator = new QRcode(
                        contentToEncode,
                        BarcodeFormat.QR_CODE,
                        qrCodeSize,
                        qrCodeSize,
                        BufferedImage.TYPE_INT_ARGB
                );

                BufferedImage qrImage = qrGenerator.qrImage();

                // Convertir BufferedImage en Image JavaFX
                Image image = SwingFXUtils.toFXImage(qrImage, null);

                // Définir l'image générée sur l'ImageView
                qrCodeImageView.setImage(image);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    @FXML
    void onClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    }
