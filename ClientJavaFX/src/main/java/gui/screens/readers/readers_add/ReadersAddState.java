package gui.screens.readers.readers_add;

import modelo.Reader;

import java.util.List;


public record ReadersAddState(String error, String message, List<Reader> readers, Reader newReader) {
}
