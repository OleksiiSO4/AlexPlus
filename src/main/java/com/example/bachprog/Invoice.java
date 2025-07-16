package com.example.bachprog;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.border.Border;

public class Invoice {

    public void openPdf(String path) {
        File file = new File(path);
        if (file.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot open PDF");
            }
        } else {
            System.out.println("Cannot find the file or Desktop doesn't support it");
        }
    }
    public static void generateCustomerInvoice(String dest, String[] repairDetailsArray, String CarInfo) {
        String imagePath = "D:\\Java Projects\\programmForBachelor\\BachProg\\src\\main\\resources\\Logo.png";

        try {
            File file = new File(dest);
            file.getParentFile().mkdirs();

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph header = new Paragraph("Invoice")
                    .setTextAlignment(TextAlignment.CENTER).setBold()
                    .setFontSize(20);
            document.add(header);

            PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
            canvas.setLineWidth(5);
            canvas.moveTo(36, 750);
            canvas.lineTo(559, 750);
            canvas.stroke();

            canvas.moveTo(36, 400);
            canvas.lineTo(559, 400);
            canvas.stroke();

            document.add(new Paragraph("\n\n Car service \"AlexPlus\"\nboul. Rène-Levesque 12, Montrèal\nMobile: +1(514) 111-2220").setBold());


            ImageData imageData = ImageDataFactory.create(imagePath);
            Image image = new Image(imageData);

            image.setWidth(150);
            image.setFixedPosition(400, 580);

            document.add(image);


            String dateOfOrder = null; // Order date
            String typeofRepairement = null; // Type of repair
            String mechanicname = null; // Mechanic name
            String mileAge = null; // Mileage
            String Cost = null; // Cost
            String Status = null; // StatusOfOrder

            for (String repairDetail : repairDetailsArray) {
                // Split each string in the array into parts
                String[] parts = repairDetail.split(" - ");

                // Extract individual fields from the parts
                dateOfOrder = parts[0]; // Order date
                typeofRepairement = parts[1]; // Type of repair
                mechanicname = parts[2]; // Mechanic name
                mileAge = parts[3]; // Mileage
                Cost = parts[4]; // Cost
                Status = parts [5]; // StatusOfOrder


                break;
            }

            Paragraph paragraph = new Paragraph();

            paragraph.add(new Text("Billing to:\n").setBold());
            paragraph.add(new Text("Client: ").setBold());
            paragraph.add(new Text(LogInClientWindow.clientName + "\n"));

            paragraph.add(new Text("Car: ").setBold());
            paragraph.add(new Text(CarInfo + "\n"));

            paragraph.add(new Text("Mileage: ").setBold());
            paragraph.add(new Text(mileAge + "\n"));

            paragraph.add(new Text("Mechanic name: ").setBold());
            paragraph.add(new Text(mechanicname + "\n"));

            paragraph.add(new Text("Date: ").setBold());
            paragraph.add(new Text(dateOfOrder));

            document.add(paragraph);


            float[] columnWidths = {300F, 100F, 100F};
            Table table = new Table(columnWidths);
            table.addHeaderCell("Type of service").setItalic().setBold();
            table.addHeaderCell("Price").setItalic().setBold();
            table.addHeaderCell("Quantity").setItalic().setBold();

            table.addCell(typeofRepairement);
            table.addCell(Cost);
            table.addCell("1");

            document.add(table);

            document.add(new Paragraph("\nSymmary: " + Cost).setBold());

            document.add(new Paragraph("\nThanks for choosing us!").setTextAlignment(TextAlignment.CENTER));

            //Terms and conditions
            document.add(new Paragraph("\nTERMS AND CONDITIONS").setTextAlignment(TextAlignment.LEFT).setBold());

            // Payment Terms
            document.add(new Paragraph("1. Payment is due upon completion of service unless otherwise agreed.")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("2. Accepted payment methods: cash, credit/debit card, or bank transfer.")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("3. Late payments may incur additional fees.")
                    .setTextAlignment(TextAlignment.LEFT));

            // Warranty & Liability
            document.add(new Paragraph("4. Services and parts may come with a limited warranty (e.g., 12 months or 10,000 kms).")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("5. The service provider is not liable for pre-existing issues or damages unrelated to the performed service.")
                    .setTextAlignment(TextAlignment.LEFT));

            // Estimates & Additional Work
            document.add(new Paragraph("6. The initial estimate is subject to change based on further inspection.")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("7. Any additional repairs will require customer approval before proceeding.")
                    .setTextAlignment(TextAlignment.LEFT));

            // Refunds & Returns
            document.add(new Paragraph("8. Refunds are only applicable for defective parts or unsatisfactory service.")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("9. Installed parts cannot be returned unless covered by warranty.")
                    .setTextAlignment(TextAlignment.LEFT));

            // Customer Responsibilities
            document.add(new Paragraph("10. Customers must provide accurate vehicle information.")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("11. The service provider is not responsible for personal belongings left in the vehicle.")
                    .setTextAlignment(TextAlignment.LEFT));

            // Closing
            document.add(new Paragraph("\nBy proceeding with the service, the customer agrees to these terms and conditions.")
                    .setTextAlignment(TextAlignment.CENTER));


            document.close();
            System.out.println("PDF created: " + dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
