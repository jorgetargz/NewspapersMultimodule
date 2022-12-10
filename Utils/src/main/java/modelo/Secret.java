package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secret {

    private String code;
    private LocalDateTime codeExpirationDate;
    private String username;
    private String email;
}
