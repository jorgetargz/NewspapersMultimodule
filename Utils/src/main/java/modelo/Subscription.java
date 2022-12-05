package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private int readerId;
    private int newspaperId;
    private LocalDate signingDate;
    private LocalDate cancellationDate;
}
