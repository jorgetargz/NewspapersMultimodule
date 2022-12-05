package gui.screens.readers.readers_list;

import modelo.Reader;

import java.util.List;


public record ReadersListState(String error, List<Reader> readers) {
}
