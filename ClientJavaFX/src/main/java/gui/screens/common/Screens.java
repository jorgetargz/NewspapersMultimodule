package gui.screens.common;

public enum Screens {

    WELCOME(ScreenConstants.FXML_WELCOME_SCREEN_FXML),
    LOGIN(ScreenConstants.FXML_LOGIN_SCREEN_FXML),
    READERS_LIST(ScreenConstants.FXML_READERS_LIST_SCREEN),
    READERS_ADD(ScreenConstants.FXML_READERS_ADD_SCREEN),
    READERS_UPDATE(ScreenConstants.FXML_READERS_UPDATE_SCREEN),
    READERS_DELETE(ScreenConstants.FXML_READERS_DELETE_SCREEN),
    NEWSPAPERS_LIST(ScreenConstants.FXML_NEWSPAPERS_LIST_SCREEN),
    NEWSPAPERS_ADD(ScreenConstants.FXML_NEWSPAPERS_ADD_SCREEN),
    NEWSPAPERS_UPDATE(ScreenConstants.FXML_NEWSPAPERS_UPDATE_SCREEN),
    NEWSPAPERS_DELETE(ScreenConstants.FXML_NEWSPAPERS_DELETE_SCREEN);

    private final String path;

    Screens(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


}
