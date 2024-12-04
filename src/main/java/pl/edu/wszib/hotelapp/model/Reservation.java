package pl.edu.wszib.hotelapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private String guestName;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private boolean breakFast;

    private boolean dinner;

    private double mealCost;

    private double roomCost;

    private double stayPrice;


}
