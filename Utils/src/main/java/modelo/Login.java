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
    private String role;

    public Login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Login(String username, String inputPassword, String email, int id) {
        this.username = username;
        this.password = inputPassword;
        this.email = email;
        this.idReader = id;
    }
}
