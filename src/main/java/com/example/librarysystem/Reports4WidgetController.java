package com.example.librarysystem;
import Entities.Borrowes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Reports4WidgetController {

    @FXML
    public Label name,ID;

    private Borrowes borrowes;

    private ApplicationController controller = new ApplicationController();

    public void initialize() {
        borrowes = ApplicationController.getBorrowInfo();
        ID.setText(borrowes.getMemberAccount().getId() + "");
        name.setText(borrowes.getMemberAccount().getFirstName() + " " + borrowes.getMemberAccount().getLastName());
    }
}
