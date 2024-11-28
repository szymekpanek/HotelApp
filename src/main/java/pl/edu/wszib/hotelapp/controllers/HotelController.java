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
        reservations.forEach(reservation ->
                reservation.setStayPrice(reservationService.calculateStayPrice(reservation))
        );
        model.addAttribute("reservations", reservations);
        model.addAttribute("rooms", hotelService.getAllRooms());
        return "welcome-page";
    }


    @GetMapping("/reservations/edit/{id}")
    public String editReservationForm(@PathVariable("id") Long reservationId, Model model) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        model.addAttribute("reservation", reservation);
        return "edit-reservation";
    }

    @PostMapping("/reservations/update")
    public String updateReservation(@RequestParam Long reservationId,
                                    @RequestParam String checkInDate,
                                    @RequestParam String checkOutDate,
                                    Model model) {
        try {
            reservationService.updateReservation(reservationId, checkInDate, checkOutDate);
            model.addAttribute("successMessage", "Reservation updated successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
}
