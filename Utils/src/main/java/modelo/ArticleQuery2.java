package modelo;

import lombok.Data;

@Data
public class ArticleQuery2 {
    private int id;
    private String nameArticle;
    private int idType;
    private String description;
    private int idNewspaper;
    private String nameNewspaper;
}
