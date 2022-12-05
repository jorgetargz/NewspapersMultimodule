package modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Article {
    private int id;
    private String name;
    private ArticleType articleType;
    private int newspaperId;
}
