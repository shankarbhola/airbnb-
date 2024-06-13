package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import com.airbnb.exception.ResourceNotFound;
import com.airbnb.payload.BookingDto;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {

    private ModelMapper modelMapper;
    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private BucketService bucketService;
    private EmailService emailService;
    private PDFService pdfService;
    private MultipartFileConvert multipartFileConvert;
    private BitlyService bitlyService;
    private TwilioSmsService smsService;
    private WhatsappService whatsappService;

    public BookingServiceImpl(ModelMapper modelMapper, PropertyRepository propertyRepository, BookingRepository bookingRepository, BucketService bucketService, EmailService emailService, PDFService pdfService, MultipartFileConvert multipartFileConvert, BitlyService bitlyService, TwilioSmsService smsService, WhatsappService whatsappService) {
        this.modelMapper = modelMapper;
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.bucketService = bucketService;
        this.emailService = emailService;
        this.pdfService = pdfService;
        this.multipartFileConvert = multipartFileConvert;
        this.bitlyService = bitlyService;
        this.smsService = smsService;
        this.whatsappService = whatsappService;
    }

    @Override
    public BookingDto createBooking(long propertyId, User user, BookingDto bookingDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFound("Property Not Found with id " + propertyId
                ));
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        booking.setProperty(property);
        booking.setUser(user);
        booking.setTotalPrice(property.getNightlyPrice()*booking.getTotalNights());
        booking.setBookingDate(LocalDate.now());
        Booking savedBooking = bookingRepository.save(booking);
        BookingDto dto = modelMapper.map(savedBooking, BookingDto.class);
        String pdfPath = pdfService.generateBookingDetailsPDF(booking, property);
        try {
            MultipartFile file = multipartFileConvert.convert(pdfPath);
            String awsUrl = bucketService.uploadFile(file);
            String shortLink = bitlyService.shortLink(awsUrl);
            smsService.sendSms("+919078359646","Your Booking is confirmed. Click here "+shortLink);
            emailService.sendEmailWithAttachment(booking.getUser().getEmailId(),"Booking Confirmation", "Dear "+user.getName()+"\n Your Booking is confirmed",pdfPath);
            whatsappService.sendWhatsappMessage("+919078359646","Dear "+user.getName()+" \n Your Booking is confirmed.",shortLink);
            File pdfFile = new File(pdfPath);
            pdfFile.delete();
            return modelMapper.map(savedBooking, BookingDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookingDto> getBookings(User user) {
        List<Booking> bookings = bookingRepository.findByUser_Id(user.getId());
        List<BookingDto> bookingDtos = bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
        return bookingDtos;
    }

    @Override
    public void deleteBooking(long id) {
        bookingRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Booking not found for id: " + id)
        );
        bookingRepository.deleteById(id);
    }
}
