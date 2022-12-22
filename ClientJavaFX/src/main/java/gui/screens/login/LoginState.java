package gui.screens.login;

import modelo.Reader;

public record LoginState(Reader reader, String error, boolean readerRegistered, boolean isLoading,
                         boolean isLoaded) {
}
