package pl.edu.wszib.hotelapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
public class Room {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    String roomType;
    int capacity;
    double pricePerNight;

    public enum RoomType {
        STANDARD(100.0), // 100 PLN za noc
        APARTMENT(200.0), // 200 PLN za noc
        STUDIO(300.0); // 300 PLN za noc

        private final double pricePerNight;

        RoomType(double pricePerNight) {
            this.pricePerNight = pricePerNight;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }
    }
}


