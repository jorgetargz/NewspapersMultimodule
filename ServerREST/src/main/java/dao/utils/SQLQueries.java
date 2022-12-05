package dao.utils;

public class SQLQueries {


    public static final String SELECT_SUBSCRIPTIONS_QUERY = "SELECT * FROM subscribe";
    public static final String SELECT_SUBSCRIPTIONS_BY_NEWSPAPER_QUERY = "select * from subscribe where id_newspaper = ?";
    public static final String SELECT_SUBSCRIPTIONS_BY_READER_QUERY = "SELECT * FROM subscribe WHERE id_reader = ?";
    public static final String SELECT_OLDEST_SUBSCRIPTIONS_BY_NEWSPAPER_QUERY = "select * from subscribe where id_newspaper = ? order by signing_date limit 10";
    public static final String SELECT_SUBSCRIPTION_BY_NEWSPAPER_AND_READER_QUERY = "SELECT * FROM subscribe WHERE id_newspaper = ? AND id_reader = ?";
    public static final String INSERT_SUBSCRIPTION = "INSERT INTO subscribe (id_reader, id_newspaper, signing_date) VALUES (?, ?, ?)";
    public static final String UPDATE_SUBSCRIPTION = "UPDATE subscribe SET cancellation_date = ? WHERE id_newspaper = ? AND id_reader = ?";
    public static final String DELETE_SUBSCRIPTION = "delete from subscribe where id_reader = ? and id_newspaper = ?";
    public static final String DELETE_SUBSCRIPTIONS_BY_NEWSPAPER = "DELETE FROM subscribe WHERE id_newspaper = ?";
    public static final String DELETE_SUBSCRIPTIONS_BY_READER_ID = "delete from subscribe where id_reader = ?";
    public static final String SELECT_READERS_QUERY = "select * from reader";
    public static final String SELECT_LOGIN_BY_USERNAME = "SELECT * FROM login WHERE username = ?";
    public static final String SELECT_READERS_BY_ARTICLE_TYPE_QUERY = "SELECT * FROM reader WHERE id IN (SELECT id_reader FROM readarticle WHERE id_article IN (SELECT id_article FROM article WHERE id_type = ?))";
    public static final String SELECT_READERS_BY_NEWSPAPER_QUERY = "select * from reader where id in (select id_reader from subscribe where id_newspaper = ?)";
    public static final String SELECT_READER_BY_USERNAME_QUERY = "select * from reader where id in (select id_reader from login where username = ?)";
    public static final String SELECT_READER_QUERY = "select * from reader where id = ?";
    public static final String INSERT_READER = "INSERT INTO reader (name_reader, birth_reader) VALUES (?,?)";
    public static final String UPDATE_READER = "update reader set name_reader = ?, birth_reader = ? where id = ?";
    public static final String DELETE_READER = "delete from reader where id = ?";
    public static final String SELECT_LOGIN_FROM_ID_QUERY = "SELECT * FROM login WHERE id_reader = ?";
    public static final String INSERT_LOGIN_QUERY = "INSERT INTO login (username, password, id_reader) VALUES (?, ?, ?)";
    public static final String UPDATE_LOGIN_QUERY = "UPDATE login SET password = ? WHERE username = ?";
    public static final String DELETE_LOGIN_QUERY = "DELETE FROM login WHERE username = ?";
    public static final String DELETE_LOGIN_BY_READER_ID = "DELETE FROM login WHERE id_reader = ?";
    public static final String SELECT_NEWSPAPERS_QUERY = "SELECT * FROM newspaper";
    public static final String SELECT_NEWSPAPER_BY_ID_QUERY = "SELECT * FROM newspaper WHERE id = ?";
    public static final String INSERT_NEWSPAPER_QUERY = "INSERT INTO newspaper (name_newspaper, release_date) VALUES (?, ?)";
    public static final String UPDATE_NEWSPAPER_QUERY = "UPDATE newspaper SET name_newspaper = ?, release_date = ? WHERE id = ?";
    public static final String DELETE_NEWSPAPER_QUERY = "DELETE FROM newspaper WHERE id = ?";
    public static final String SELECT_ARTICLES_WITH_TYPE_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description FROM article a INNER JOIN articletype t ON a.id_type = t.id";
    public static final String SELECT_ARTICLES_BY_NEWSPAPER_WITH_TYPE_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description FROM article a INNER JOIN articletype t ON a.id_type = t.id WHERE a.id_newspaper = ?";
    public static final String SELECT_ARTICLES_BY_READER_WITH_TYPE_FROM_SUSCRIBE_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description FROM article a INNER JOIN articletype t ON a.id_type = t.id INNER JOIN subscribe s ON a.id_newspaper = s.id_newspaper WHERE s.id_reader = ?";
    public static final String SELECT_ARTICLES_BY_TYPE_WITH_TYPE_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description FROM article a INNER JOIN articletype t ON a.id_type = t.id WHERE a.id_type = ?";
    public static final String SELECT_ARTICLES_BY_TYPE_WITH_NEWSPAPER_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description, n.name_newspaper FROM article a INNER JOIN articletype t ON a.id_type = t.id INNER JOIN newspaper n ON a.id_newspaper = n.id WHERE a.id_type = ?";
    public static final String SELECT_ARTICLES_BY_NEWSPAPER_WITH_BAD_RATING_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, r.id_reader, r.rating, (SELECT COUNT(*) FROM readarticle WHERE id_reader = r.id_reader AND rating < 5) AS bad_ratings FROM article a LEFT JOIN readarticle r ON a.id = r.id_article WHERE a.id_newspaper = ? AND r.rating < 5";
    public static final String SELECT_ARTICLE_QUERY = "SELECT article.id, article.name_article, articletype.description, COUNT(readarticle.id_reader) readers FROM article INNER JOIN articletype ON article.id_type = articletype.id LEFT JOIN readarticle ON article.id = readarticle.id_article WHERE article.id = ? GROUP BY article.id, article.name_article, articletype.description";
    public static final String SELECT_ARTICLE_BY_ID_WITH_TYPE_QUERY = "SELECT a.id, a.name_article, a.id_type, a.id_newspaper, t.description FROM article a INNER JOIN articletype t ON a.id_type = t.id WHERE a.id = ?";
    public static final String INSERT_ARTICLE_QUERY = "INSERT INTO article (name_article, id_type, id_newspaper) VALUES (?, ?, ?)";
    public static final String UPDATE_ARTICLE_QUERY = "UPDATE article SET name_article = ?, id_type = ?, id_newspaper = ? WHERE id = ?";
    public static final String DELETE_ARTICLE_QUERY = "DELETE FROM article WHERE id = ?";
    public static final String DELETE_ARTICLES_BY_NEWSPAPER_QUERY = "DELETE FROM article WHERE id_newspaper = ?";
    public static final String SELECT_ARTICLE_TYPES_QUERY = "SELECT * FROM articletype";
    public static final String SELECT_ARTICLE_TYPE_BY_ID_QUERY = "SELECT * FROM articletype WHERE id = ?";
    public static final String INSERT_ARTICLE_TYPE_QUERY = "INSERT INTO articletype (description) VALUES (?)";
    public static final String UPDATE_ARTICLE_TYPE_QUERY = "UPDATE articletype SET description = ? WHERE id = ?";
    public static final String DELETE_ARTICLE_TYPE_QUERY = "DELETE FROM articletype WHERE id = ?";
    public static final String SELECT_RATINGS_QUERY = "select * from readarticle";
    public static final String SELECT_RATINGS_BY_READER_QUERY = "SELECT * FROM readarticle WHERE id_reader = ?";
    public static final String SELECT_RATING_BY_ID_QUERY = "select * from readarticle where id = ?";
    public static final String INSERT_RATING_QUERY = "INSERT INTO readarticle (id_article, id_reader, rating) VALUES (?, ?, ?)";
    public static final String UPDATE_RATING_QUERY = "UPDATE readarticle SET rating = ? WHERE id_article = ? AND id_reader = ?";
    public static final String DELETE_RATING_QUERY = "delete from readarticle where id_article = ? and id_reader = ?";
    public static final String DELETE_RATINGS_BY_NEWSPAPER_QUERY = "DELETE FROM readarticle WHERE id_article IN (SELECT id FROM article WHERE id_newspaper = ?)";
    public static final String DELETE_RATINGS_BY_READER_ID_QUERY = "delete from readarticle where id_reader = ?";
    public static final String UPDATE_READER_MAIL_QUERY = "UPDATE login SET mail = ? WHERE username = ?";
    public static final String SELECT_LOGIN_BY_USERNAME_QUERY = "SELECT * FROM login WHERE username = ?";
    public static final String UPDATE_LOGIN_PASSWORD_BY_EMAIL_QUERY = "UPDATE login SET password = ? WHERE mail = ?";

    private SQLQueries() {
    }

}