package pl.edu.wszib.hotelapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.hotelapp.Services.IHotelService;
import pl.edu.wszib.hotelapp.Services.implementations.HotelService;
import pl.edu.wszib.hotelapp.Services.implementations.ReservationService;
import pl.edu.wszib.hotelapp.model.Reservation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor

@Controller
public class HotelController {
    private final HotelService hotelService;
    private final ReservationService reservationService;

    @GetMapping("/")
    public String getHotelData(Model model) {
        List<Reservation> reservations = hotelService.getAllReservations();

        reservations.forEach(reservation -> {
            reservationService.calculateMealCost(reservation);
            reservationService.calculateRoomCost(reservation);
            reservationService.calculateStayPrice(reservation);
        });

        model.addAttribute("reservations", reservations);
        model.addAttribute("rooms", hotelService.getAllRooms());

        return "welcome-page";
    }


    @GetMapping("/reservations/edit/{id}")
    public String showEditForm(@PathVariable("id") Long reservationId, Model model) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        if (reservation == null) {
            model.addAttribute("errorMessage", "Reservation not found");
            return "error";
        }
        model.addAttribute("reservation", reservation);
        return "edit-reservation";
    }


    @PostMapping("/reservations/update")
    public String updateReservation(
            @RequestParam Long reservationId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate,
            @RequestParam(required = false, defaultValue = "false") boolean hasBreakfast,
            @RequestParam(required = false, defaultValue = "false") boolean hasDinner,
            Model model) {
        try {
            reservationService.updateReservation(reservationId, checkInDate, checkOutDate, hasBreakfast, hasDinner);
            model.addAttribute("successMessage", "Reservation updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "edit-reservation";
        }


        return "redirect:/";
    }


    @GetMapping("/reservations/create")
    public String showCreateReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "create-reservation";
    }

    @PostMapping("/reservations/create")
    public String createReservation(
            @RequestParam Long roomId,
            @RequestParam String guestName,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate,
            @RequestParam(required = false, defaultValue = "false") boolean hasBreakfast,
            @RequestParam(required = false, defaultValue = "false") boolean hasDinner,
            Model model) {
        try {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);

            Reservation reservation = reservationService.createReservation(roomId, guestName, checkIn, checkOut, hasBreakfast, hasDinner);

            model.addAttribute("successMessage", "Reservation created successfully!");
            model.addAttribute("reservation", reservation);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "create-reservation";
        }

        return "redirect:/";
    }


}
