package modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleQuery3 {
    private int id;
    private String nameArticle;
    private int idType;
    private int idNewspaper;
    private int idReader;
    private int rating;
    private int badRatings;
    private boolean isCritical;

    public String getCritical() {
        return isCritical ? "Yes" : "No";
    }

}
