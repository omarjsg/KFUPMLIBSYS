package com.example.librarysystem;
import Entities.Borrowes;
import Entities.PhysicalBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class renewBookController {

    @FXML
    public TextField rDate;

    private ApplicationController controller = new ApplicationController();

    private Borrowes borrowes;

    public void renewB(ActionEvent event) throws IOException, SQLException {
        if(rDate.getText().length() != 0) {
            String returnDate = rDate.getText();
            //take data from text fields above then assign it to queries.
            //update book sql query.
            //add borrow sql query.
           Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
            PreparedStatement myStmt = myConn.prepareStatement("UPDATE `library`.`borrowes` SET `RDate` = ? WHERE (`MID` = ?) and (`ISBN` = ?);");
            myStmt.setString(1, returnDate);
            myStmt.setString(2,controller.getCurrid()+"" );
            myStmt.setString(3,borrowes.getBook().getISBN()+"" );
            myStmt.execute();
            System.out.println(borrowes.getBook().getISBN()+""+controller.getCurrid()+returnDate);
            ResultSet rs = myStmt.executeQuery("select * from borrowes");
            controller.rnuBCScreen(event);
        }
    }

    public void switchToBookListScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToBookListScreen(event);
    }

    public void switchToReserveScreen(ActionEvent event) throws IOException {
        controller.switchToReserveScreen(event);
    }

    public void switchToMemberHomeScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToMemberHomeScreen(event);
    }

    public void logOut(ActionEvent event) throws IOException {
        controller.logOut(event);
    }
    public void initialize() {
        borrowes = ApplicationController.getBorrowInfo();
        rDate.setText(borrowes.getReturnDate());
    }
}
