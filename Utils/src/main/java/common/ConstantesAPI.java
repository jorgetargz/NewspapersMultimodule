package common;

public class ConstantesAPI {

    public static final String API_PATH = "api/";
    //Main Paths for REST /api
    public static final String PATH_LOGIN = "login/";
    public static final String PATH_NEWSPAPERS = "newspapers/";
    public static final String PATH_RATINGS = "ratings/";
    public static final String PATH_ARTICLES = "articles/";
    public static final String PATH_READERS = "readers/";
    public static final String PATH_SUBSCRIPTIONS = "subscriptions/";
    //Auxiliar Paths for REST /api/mainPath/auxPath
    public static final String ID_PATH_PARAM = "{id}/";
    public static final String READER_PATH = "reader/";
    public static final String LOGIN_PATH = "login/";
    public static final String NEWSPAPER_PATH = "newspaper/";
    public static final String BAD_RATINGS_PATH = "badRatings/";
    public static final String TYPE_PATH = "type/";
    public static final String ARTICLE_TYPES_PATH = "articleTypes/";
    public static final String ARTICLE_TYPE_PATH = "articleType/";
    public static final String OLDEST_PATH = "oldest/";
    public static final String ID_PATH = "id/";
    public static final String LOGOUT_PATH = "logout/";
    //ENDPOINTS LOGIN
    public static final String ENDPOINT_LOGIN = PATH_LOGIN;
    public static final String ENDPOINT_LOGOUT = PATH_LOGIN + LOGOUT_PATH;
    //ENDPOINTS READERS
    public static final String ENDPOINT_READERS = PATH_READERS;
    public static final String ENDPOINT_READER_BY_ID = PATH_READERS + ID_PATH_PARAM;
    public static final String ENDPOINT_READERS_BY_NEWSPAPER_ID = PATH_READERS + NEWSPAPER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_READERS_BY_ARTICLE_TYPE_ID = PATH_READERS + ARTICLE_TYPE_PATH + ID_PATH_PARAM;
    //ENDPOINTS NEWSPAPERS
    public static final String ENDPOINT_NEWSPAPERS = PATH_NEWSPAPERS;
    public static final String ENDPOINT_NEWSPAPER_BY_ID = PATH_NEWSPAPERS + ID_PATH_PARAM;
    //ENDPOINTS RATINGS
    public static final String ENDPOINT_RATINGS = PATH_RATINGS;
    public static final String ENDPOINT_RATING_BY_ID = PATH_RATINGS + ID_PATH_PARAM;
    public static final String ENDPOINT_RATINGS_BY_READER_ID = PATH_RATINGS + READER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_RATINGS_BY_NEWSPAPER_ID = PATH_RATINGS + NEWSPAPER_PATH + ID_PATH_PARAM;
    //ENDPOINTS SUBSCRIPTIONS
    public static final String ENDPOINT_SUBSCRIPTIONS = PATH_SUBSCRIPTIONS;
    public static final String ENDPOINT_SUBSCRIPTION_BY_ID_READER_AND_NEWSPAPER = PATH_SUBSCRIPTIONS + ID_PATH; //IDs of reader and newspaper query param
    public static final String ENDPOINT_SUBSCRIPTIONS_BY_READER_ID = PATH_SUBSCRIPTIONS + READER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_SUBSCRIPTIONS_BY_NEWSPAPER_ID = PATH_SUBSCRIPTIONS + NEWSPAPER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_10_OLDEST_SUBSCRIPTIONS_BY_NEWSPAPER_ID = PATH_SUBSCRIPTIONS + NEWSPAPER_PATH + ID_PATH_PARAM + OLDEST_PATH;
    //ENDPOINTS ARTICLES
    public static final String ENDPOINT_ARTICLES = PATH_ARTICLES;
    public static final String ENDPOINT_ARTICLE_BY_ID = PATH_ARTICLES + ID_PATH_PARAM;
    public static final String ENDPOINT_ARTICLES_BY_NEWSPAPER = PATH_ARTICLES + NEWSPAPER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_ARTICLES_BY_NEWSPAPER_WITH_BAD_RATINGS = PATH_ARTICLES + NEWSPAPER_PATH + ID_PATH_PARAM + BAD_RATINGS_PATH;
    public static final String ENDPOINT_ARTICLES_BY_TYPE_ID_WITH_NEWSPAPER_NAME = PATH_ARTICLES + TYPE_PATH + ID_PATH_PARAM + NEWSPAPER_PATH;
    public static final String ENDPOINT_ARTICLES_AVAILABLE_BY_READER_ID = PATH_ARTICLES + READER_PATH + ID_PATH_PARAM;
    public static final String ENDPOINT_ARTICLE_TYPES = PATH_ARTICLES + ARTICLE_TYPES_PATH;
    public static final String ENDPOINT_ARTICLE_TYPE_BY_ID = PATH_ARTICLES + ARTICLE_TYPES_PATH + ID_PATH_PARAM;
    //Parameters
    public static final String ID = "id";
    public static final String ID_READER = "idReader";
    public static final String ID_NEWSPAPER = "idNewspaper";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private ConstantesAPI() {
    }
}
