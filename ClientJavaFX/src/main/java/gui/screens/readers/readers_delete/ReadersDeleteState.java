package gui.screens.readers.readers_delete;

import modelo.Reader;

import java.util.List;

public record ReadersDeleteState(String error, String message, List<Reader> readers, Reader readerDeleted) {
}
