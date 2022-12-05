package dao.impl.row_mapers;

import dao.common.Constantes;
import modelo.ArticleQuery2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleQuery2RowMapper implements RowMapper<ArticleQuery2> {

    @Override
    public ArticleQuery2 mapRow(ResultSet rs, int row) throws SQLException {
        ArticleQuery2 articlequery2 = new ArticleQuery2();
        articlequery2.setId(rs.getInt(Constantes.ID));
        articlequery2.setNameArticle(rs.getString(Constantes.NAME_ARTICLE));
        articlequery2.setIdType(rs.getInt(Constantes.ID_TYPE));
        articlequery2.setIdNewspaper(rs.getInt(Constantes.ID_NEWSPAPER));
        articlequery2.setDescription(rs.getString(Constantes.DESCRIPTION));
        articlequery2.setNameNewspaper(rs.getString(Constantes.NAME_NEWSPAPER));
        return articlequery2;
    }
}
