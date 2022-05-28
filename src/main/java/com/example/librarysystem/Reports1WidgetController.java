package com.example.librarysystem;
import Entities.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class Reports1WidgetController {

    @FXML
    public Label name,ID;

    private Member member;

    private ApplicationController controller = new ApplicationController();

    public void initialize() {
        member = ApplicationController.getMember();
        ID.setText(member.getId() + "");
        name.setText(member.getFirstName()+ " " + member.getLastName());
    }
}
