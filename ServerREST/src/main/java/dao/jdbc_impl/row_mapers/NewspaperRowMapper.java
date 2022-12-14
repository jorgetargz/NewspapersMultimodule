package dao.jdbc_impl.row_mapers;

import dao.common.Constantes;
import modelo.Newspaper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewspaperRowMapper implements RowMapper<Newspaper> {

    @Override
    public Newspaper mapRow(ResultSet resultSet, int i) throws SQLException {
        Newspaper newspaper = new Newspaper();
        newspaper.setId(resultSet.getInt(Constantes.ID));
        newspaper.setNameNewspaper(resultSet.getString(Constantes.NAME_NEWSPAPER));
        newspaper.setReleaseDate(resultSet.getDate(Constantes.RELEASE_DATE).toLocalDate());
        return newspaper;
    }
}
