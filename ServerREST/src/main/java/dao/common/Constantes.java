package dao.common;

public class Constantes {

    //Configuracion de la base de datos
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static final String CONNECTION_TO_DATABASE_ESTABLISHED = "Connection to database established";
    public static final String CLOSING_CONNECTION_POOL = "Closing connection pool";
    public static final int BAD_RATING_LIMIT_TO_BE_CRITICAL = 4;
    public static final int MAX_POOL_SIZE = 4;
    public static final int TIMEOUT_MS = 5000;
    public static final int CACHE_SIZE = 250;
    public static final int CACHE_SQL_LIMIT = 2048;

    //Readers con id menor a este son administradores
    public static final int MIN_ID_READER = 0;

    //Column names for mapping tables
    public static final String ID = "id";
    public static final String ID_READER = "id_reader";
    public static final String ID_NEWSPAPER = "id_newspaper";
    public static final String ID_ARTICLE = "id_article";
    public static final String ID_TYPE = "id_type";
    public static final String NAME_READER = "name_reader";
    public static final String BIRTH_READER = "birth_reader";
    public static final String SIGNING_DATE = "signing_date";
    public static final String CANCELLATION_DATE = "cancellation_date";
    public static final String NAME_ARTICLE = "name_article";
    public static final String RATING = "rating";
    public static final String BAD_RATINGS = "bad_ratings";
    public static final String DESCRIPTION = "description";
    public static final String NAME_NEWSPAPER = "name_newspaper";
    public static final String RELEASE_DATE = "release_date";
    public static final String READERS = "readers";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String MAIL = "mail";
    public static final String CODE = "code";
    public static final String CODE_EXPIRATION_DATE = "expiration_date";
    public static final String EMAIL = "mail";
    public static final String ROLE = "role";

    //Exceptions messages
    public static final String THERE_ARE_NO_ARTICLES = "There are no articles in the database";
    public static final String NO_ARTICLES_OF_THIS_NEWSPAPER = "There are no articles of this newspaper in the database";
    public static final String NO_ARTICLES_OF_THIS_TYPE = "There are no articles of this type in the database";
    public static final String NO_ARTICLES_OF_THIS_READER = "There are no articles of this reader in the database";
    public static final String ARTICLE_COULD_NOT_BE_SAVED = "The article could not be saved";
    public static final String NO_ARTICLE_WITH_THIS_ID = "There is no article with this id in the database";
    public static final String NO_ARTICLE_TYPES_FOUND = "No article types found";
    public static final String ARTICLE_TYPE_NOT_FOUND = "Article type not found";
    public static final String ARTICLE_TYPE_NOT_SAVED = "Article type not saved";
    public static final String ARTICLE_TYPE_NOT_UPDATED = "Article type not updated";
    public static final String ARTICLE_TYPE_NOT_DELETED = "Article type not deleted";
    public static final String NO_NEWSPAPERS_FOUND = "No newspapers found";
    public static final String NEWSPAPER_NOT_FOUND = "Newspaper not found";
    public static final String NEWSPAPER_NOT_SAVED = "Newspaper not saved";
    public static final String NEWSPAPER_NOT_UPDATED = "Newspaper not updated";
    public static final String THERE_ARE_NO_RATINGS_IN_THE_DATABASE = "There are no ratings in the database";
    public static final String NO_RATINGS_MADE_BY_THIS_READER = "There are no ratings made by this reader in the database";
    public static final String NO_RATING_WITH_THIS_ID_IN_THE_DATABASE = "There is no rating with this id in the database";
    public static final String NO_READERS = "There are no readers in the database";
    public static final String NO_READERS_OF_THIS_NEWSPAPER = "There are no readers of this newspaper in the database";
    public static final String NO_READERS_OF_THIS_ARTICLE_TYPE = "There are no readers of this article type in the database";
    public static final String READER_NOT_FOUND = "Reader not found";
    public static final String NO_SUBSCRIPTIONS = "There are no subscriptions in the database";
    public static final String NO_SUBSCRIPTIONS_OF_THIS_NEWSPAPER = "There are no subscriptions of this newspaper in the database";
    public static final String NO_SUBSCRIPTIONS_OF_THIS_READER = "There are no subscriptions of this reader in the database";
    public static final String SUBSCRIPTION_NOT_FOUND = "There is no subscription of this newspaper and reader in the database";
    public static final String VERIFY_YOUR_EMAIL_FIRST = "You must verify your email first";
    public static final String WRONG_PASSWORD = "Wrong password";
    public static final String SECRET_NOT_FOUND = "Secret not found";

    private Constantes() {
    }

}