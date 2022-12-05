package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleType {
    private int id;
    private String description;

    @Override
    public String toString() {
        return this.description;
    }
}
