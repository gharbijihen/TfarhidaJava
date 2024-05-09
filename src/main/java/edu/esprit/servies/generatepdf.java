package edu.esprit.servies;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import edu.esprit.entites.Equipement;
import edu.esprit.entites.Logement;

public class generatepdf {

    public static void generatePDF(List<Logement> logements, FileOutputStream fileOutputStream, String logoPath)
            throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, fileOutputStream);
        document.open();

        // Ajout du logo
        try {
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(220, 150);
            document.add(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajout de la date actuelle
        LocalDate currentDate = LocalDate.now();
        Font fontDate = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);
        Paragraph dateParagraph = new Paragraph("Date: " + currentDate.toString(), fontDate);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        // Titre du document
        Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, BaseColor.BLUE);
        Paragraph titleParagraph = new Paragraph("Liste des Logements disponibles ", fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph("\n"));

        // Contenu des activités (cartes personnalisées)
        for (Logement logement : logements) {
            generateLogementCard(document, logement);
        }

        document.close();
    }

    private static void generateLogementCard(Document document, Logement logement) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);

        // Ajoutez ici la structure de votre carte personnalisée
        // Par exemple :
        Paragraph nom = new Paragraph("Nom: " + logement.getNom()+":Type de logement: " + logement.getType_log(), font);
        Paragraph num = new Paragraph("numéro: " + logement.getNum(), font);
        Paragraph note = new Paragraph("Note: " + logement.getNote_moyenne(), font);
        Paragraph localsation = new Paragraph("Localisation: " + logement.getLocalisation(), font);
        Paragraph prix = new Paragraph("Prix: " + String.valueOf(logement.getPrix()), font);
        // Ajoutez les autres détails du logement
        Equipement equipement = logement.equipement_id;

        Paragraph parking = new Paragraph("les equuipemeents disponible :\n"+"Parking: " + String.valueOf(equipement.isParking()), font);
        Paragraph internet = new Paragraph("Internet: " + String.valueOf(equipement.isParking()), font);
        Paragraph climatisation = new Paragraph("Climatisation: " + String.valueOf(equipement.isParking()), font);
        Paragraph nbrChamb = new Paragraph("Nombre de chambre disponible: " + String.valueOf(equipement.getNbr_chambre()), font);
        Paragraph typech= new Paragraph("Type des chambres: " + equipement.getTypes_de_chambre(), font);
        Paragraph desc = new Paragraph("Description: " + equipement.getDescription(), font);
        Paragraph SéparationParagraph = new Paragraph("--------------------------------------- ");
        SéparationParagraph.setAlignment(Element.ALIGN_CENTER);
        // Ajoutez les paragraphes à la carte
        document.add(nom);
        document.add(num);
        document.add(note);
        document.add(localsation);
        document.add(prix);
        document.add(parking);
        document.add(internet);
        document.add(climatisation);
        document.add(nbrChamb);
        document.add(typech);
        document.add(desc);

        document.add(SéparationParagraph);

        // Ajoutez les autres détails du logement

        // Ajoutez une séparation entre les cartes
        document.add(new Paragraph("\n"));
    }

}