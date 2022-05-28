package com.example.librarysystem;
import Entities.Borrowes;
import Entities.Requests;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RequestWidgetController {

    @FXML
    public Label title,ID, requestDate;

    private Requests requests;

    private ApplicationController controller = new ApplicationController();

    public void initialize() {
        requests = ApplicationController.getRequests();
        ID.setText(requests.getMember().getId() + "");
        title.setText(requests.getName());
        requestDate.setText(requests.getRequestDate());
    }
}
