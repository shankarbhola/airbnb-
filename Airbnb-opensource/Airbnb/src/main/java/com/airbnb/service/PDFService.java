package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PDFService {

    public String generateBookingDetailsPDF(Booking booking, Property property) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D://booking-confirmation-" + booking.getId() + ".pdf"));

            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
            Paragraph title = new Paragraph("Booking Confirmation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add empty line
            document.add(new Paragraph("\n"));

            // Add booking ID
            Font idFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
            Paragraph idParagraph = new Paragraph("Booking ID: " + booking.getId(), idFont);
            idParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(idParagraph);

            // Add empty line
            document.add(new Paragraph("\n"));

            // Add booking details
            PdfPTable bookingDetailsTable = new PdfPTable(2);
            bookingDetailsTable.setWidthPercentage(100);
            addBookingDetailRow(document, "Guest Name", booking.getGuestName());
            addBookingDetailRow(document, "Booking Date", booking.getBookingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            addBookingDetailRow(document, "Total Nights", booking.getTotalNights().toString());
            addBookingDetailRow(document, "Check-in Date", booking.getCheckInDate().toString());
            addBookingDetailRow(document, "Check-in Time", booking.getCheckInTime().toString());

            // Add property details
            addBookingDetailRow(document, "Location", property.getLocation().getLocationName());
            addBookingDetailRow(document, "Resort Name", property.getPropertyName());

            // Calculate total price
            Double totalPrice = property.getNightlyPrice() * booking.getTotalNights();
            addBookingDetailRow(document, "Total Price", "Rs." + totalPrice.toString());

            // Add footer
            Font footerFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 16, BaseColor.BLACK);

            // Add empty line
            document.add(new Paragraph("\n"));

            Paragraph footer = new Paragraph("Thank you for choosing our service!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return "D://booking-confirmation-" + booking.getId() + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addBookingDetailRow(Document document, String attribute, String value) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase(attribute));
        PdfPCell cell2 = new PdfPCell(new Phrase(value));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell2.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);
        table.addCell(cell2);
        document.add(table);
    }
}

