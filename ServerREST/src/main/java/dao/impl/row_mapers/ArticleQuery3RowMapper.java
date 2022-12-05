package dao.impl.row_mapers;

import dao.common.Constantes;
import modelo.ArticleQuery3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleQuery3RowMapper implements RowMapper<ArticleQuery3> {

    @Override
    public ArticleQuery3 mapRow(ResultSet rs, int row) throws SQLException {
        ArticleQuery3 articlequery3 = new ArticleQuery3();
        articlequery3.setId(rs.getInt(Constantes.ID));
        articlequery3.setNameArticle(rs.getString(Constantes.NAME_ARTICLE));
        articlequery3.setIdType(rs.getInt(Constantes.ID_TYPE));
        articlequery3.setIdNewspaper(rs.getInt(Constantes.ID_NEWSPAPER));
        articlequery3.setIdReader(rs.getInt(Constantes.ID_READER));
        articlequery3.setRating(rs.getInt(Constantes.RATING));
        articlequery3.setBadRatings(rs.getInt(Constantes.BAD_RATINGS));
        articlequery3.setCritical(rs.getInt(Constantes.BAD_RATINGS) > Constantes.BAD_RATING_LIMIT_TO_BE_CRITICAL);
        return articlequery3;
    }
}
