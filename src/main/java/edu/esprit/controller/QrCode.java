package edu.esprit.controller;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import edu.esprit.entites.Activite;
import edu.esprit.entites.QRcode;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
public class QrCode {



        @FXML
        private ImageView qrCodeImageView;
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
            int qrCodeSize = 300;

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
    }
