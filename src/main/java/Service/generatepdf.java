package Service;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import Entities.User;
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



    public class generatepdf {

        public static void generatePDF(List<User > users, FileOutputStream fileOutputStream, String logoPath)
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
            Paragraph titleParagraph = new Paragraph("Liste des Utilisateurs", fontTitle);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);
            document.add(new Paragraph("\n"));

            // Contenu des activités
            PdfPTable table = new PdfPTable(6); // 8 colonnes pour les détails des activités
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font fontHeader = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD);
            addCell(table, "Username", fontHeader);
            addCell(table, "First_name", fontHeader);
            addCell(table, "Last_name", fontHeader);
            addCell(table, "Role", fontHeader);
            addCell(table, "Email", fontHeader);
            addCell(table, "Numero", fontHeader);
        //    addCell(table, "Numero", fontHeader);
           // addCell(table, "Numero", fontHeader);


            for (User  user : users ) {
                addCell(table, user.getUsername() );
                addCell(table, user .getFirst_name() );
                addCell(table, user .getLast_name() );
                addCell(table, user .getRoles() );
                addCell(table, user .getEmail() );
                addCell(table, String.valueOf(user .getNumero() ));
            //    addCell(table, user .getPassword() );
             //   addCell(table, user .getReset_token() );
             //   addCell(table, String.valueOf(user .getIs_verified())  );
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


