package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Petition {

    private int id;
    private String username;
    private LocalDateTime time;
    private String path;
    private String method;

}
