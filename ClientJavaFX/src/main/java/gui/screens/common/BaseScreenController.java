package gui.screens.common;

import gui.screens.main.MainController;


public class BaseScreenController {

    private MainController mainController;

    public MainController getPrincipalController() {
        return mainController;
    }

    public void setPrincipalController(MainController mainController) {
        this.mainController = mainController;
    }

    public void principalCargado() {
        //Se usa para lanzar metodos que requieren tener cargado el mainController
    }
}
