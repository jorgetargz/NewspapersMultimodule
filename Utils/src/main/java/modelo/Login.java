package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String username;
    private String password;
    private String email;
    private int idReader;

    public Login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
