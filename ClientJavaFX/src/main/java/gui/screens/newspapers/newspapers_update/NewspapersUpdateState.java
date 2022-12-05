package gui.screens.newspapers.newspapers_update;

import modelo.Newspaper;

import java.util.List;

public record NewspapersUpdateState(String error, String message, List<Newspaper> newspapers,
                                    Newspaper updatedNewspaper) {
}
