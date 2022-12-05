package gui.screens.newspapers.newspapers_add;

import modelo.Newspaper;

import java.util.List;

public record NewspapersAddState(String error, String message, List<Newspaper> newspapers,
                                 Newspaper newNewspaper) {
}
