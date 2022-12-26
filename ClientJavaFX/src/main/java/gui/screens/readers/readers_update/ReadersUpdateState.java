package gui.screens.readers.readers_update;

import modelo.Reader;

import java.util.List;

public record ReadersUpdateState(String error, String message, List<Reader> readers, Reader updatedReader,
                                 boolean isLoading,
                                 boolean isLoaded) {
}
