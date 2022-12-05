package dao.impl.row_mapers;

import dao.common.Constantes;
import modelo.Article;
import modelo.ArticleType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int row) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt(Constantes.ID));
        article.setName(rs.getString(Constantes.NAME_ARTICLE));
        ArticleType articleType = new ArticleType(rs.getInt(Constantes.ID_TYPE), rs.getString(Constantes.DESCRIPTION));
        article.setArticleType(articleType);
        article.setNewspaperId(rs.getInt(Constantes.ID_NEWSPAPER));
        return article;
    }
}
