package gui.screens.newspapers.newspapers_delete;

import modelo.Newspaper;

import java.util.List;

public record NewspapersDeleteState(String error, String message, List<Newspaper> newspapers,
                                    Newspaper newspaperDeleted, boolean isLoading,
                                    boolean isLoaded) {
}
