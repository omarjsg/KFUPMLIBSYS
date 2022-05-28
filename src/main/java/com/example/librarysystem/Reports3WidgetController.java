package com.example.librarysystem;
import Entities.Borrowes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Reports3WidgetController {

    @FXML
    public Label name,ID;

    private Borrowes borrowes;

    private ApplicationController controller = new ApplicationController();

    public void initialize() {
        borrowes = ApplicationController.getBorrowInfo();
        System.out.println(borrowes);
        ID.setText(borrowes.getMemberAccount().getId() + "");
        name.setText(borrowes.getMemberAccount().getFirstName() + " " + borrowes.getMemberAccount().getLastName());
    }
}
