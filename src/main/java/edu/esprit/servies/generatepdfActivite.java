package edu.esprit.servies;

        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.time.LocalDate;
        import java.util.List;

        import com.itextpdf.text.*;
        import com.itextpdf.text.pdf.PdfPCell;
        import com.itextpdf.text.pdf.PdfPTable;
        import com.itextpdf.text.pdf.PdfWriter;

        import edu.esprit.entites.Activite;

public class generatepdfActivite {

    public static void generatepdfActivite(List<Activite> activites, FileOutputStream fileOutputStream, String logoPath)
            throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, fileOutputStream);
        document.open();

        // Ajout du logo
        try {
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(150, 150);
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
        Paragraph titleParagraph = new Paragraph("Liste des Activités", fontTitle);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph("\n"));

        // Contenu des activités
        PdfPTable table = new PdfPTable(8); // 8 colonnes pour les détails des activités
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font fontHeader = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD);
        addCell(table, "Nom", fontHeader);
        addCell(table, "Prix", fontHeader);
        addCell(table, "Localisation", fontHeader);
        addCell(table, "Nombre de participants", fontHeader);
        addCell(table, "Etat", fontHeader);
        addCell(table, "Image", fontHeader);
        addCell(table, "Description", fontHeader);

        addCell(table, "ID de catégorie", fontHeader);

        for (Activite activite : activites) {
            addCell(table, activite.getNom());
            addCell(table, String.valueOf(activite.getPrix()));
            addCell(table, activite.getLocalisation());
            addCell(table, String.valueOf(activite.getNb_P()));
            addCell(table, activite.getEtat());
            try {
                Image img = Image.getInstance(activite.getImage()); // Ensure this path is correct and accessible
                img.scaleToFit(100, 100); // Scale image to fit cell
                PdfPCell imageCell = new PdfPCell(img, true);
                table.addCell(imageCell);
            } catch (BadElementException | IOException e) {
                // In case of error, fall back to adding a cell with text "No image"
                addCell(table, "No image");
            }            addCell(table, activite.getDescription_act());
            addCell(table, String.valueOf(activite.getCategorie_id()));
        }

        document.add(table);

        document.close();
    }

    // Méthode utilitaire pour ajouter une cellule à une table PDF
    private static void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        table.addCell(cell);
    }

    private static void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        table.addCell(cell);
    }
}

