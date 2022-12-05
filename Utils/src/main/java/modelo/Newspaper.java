package modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Newspaper {
    private int id;
    private String nameNewspaper;
    private LocalDate releaseDate;

    public Newspaper(String name, LocalDate releaseDate) {
        this.nameNewspaper = name;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return this.nameNewspaper;
    }
}
