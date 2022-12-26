package gui.screens.newspapers.newspapers_list;

import modelo.Newspaper;

import java.util.List;

public record NewspapersListState(String error, List<Newspaper> newspapers, boolean isLoading,
                                  boolean isLoaded) {
}
