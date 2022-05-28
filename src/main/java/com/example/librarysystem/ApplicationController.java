package com.example.librarysystem;

import Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationController {
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private static PhysicalBook book;
    private static Member member;
    private static Librarian librarian;
    private static Person currentAccount;
    private static Borrowes borrowes;
    public static String choice;
    private static Requests requests;
    private static String currid;

    @FXML
    public TextField catalogTF, reserveTF,username_field, pass_field, rDate;
    @FXML
    public ComboBox sCombo;

    /*
    ################################    Member and Librarian    ################################
     */

    public void auth(ActionEvent event) throws IOException, SQLException {
            String type = "librarian";
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM library.authenticator;");
        ResultSet rs = myStmt.executeQuery("select count(*) from authenticator");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        String email= username_field.getText();
        String pass=pass_field.getText();
        rs = myStmt.executeQuery("Select ID,authenticator.Email,Epassword, authenticator.Type from authenticator, person where person.Email=authenticator.Email");
        rs.next();
       for(int i=0; i<j; i++){
           if(email.equals(rs.getString("Email"))){
               if(pass.equals(rs.getString("Epassword"))){
                   type=rs.getString("Type");
                   if(type.equals("L")){
                       currid=rs.getString("ID");
                       switchToLibrarianHomeScreen(event);

                   }else if(type.equals("M")){
                       currid=rs.getString("ID");
                       switchToMemberHomeScreen(event);

                   }
               }
           }else{
               rs.next();
           }
           }
       }



           // if (type.equals("librarian")) {
          //      switchToLibrarianHomeScreen(event);
          //  } else if (type.equals("member")) {
          //      switchToMemberHomeScreen(event);
          //  }


    public void logOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("log-in.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        member = null;
        librarian = null;
        currentAccount = null;
    }

    public static Member getMember() {
        return member;
    }

    public static void setMember(Member member) {
        ApplicationController.member = member;
    }

    public static Librarian getLibrarian() {
        return librarian;
    }

    public static void setLibrarian(Librarian librarian) {
        ApplicationController.librarian = librarian;
    }

    public static Person getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Person currentAccount) {
        ApplicationController.currentAccount = currentAccount;
    }

    public static Requests getRequests() {
        return requests;
    }

    public static void setRequests(Requests requests) {
        ApplicationController.requests = requests;
    }

    /*
    ################################    Member    ################################
    */

    public void switchToMemberHomeScreen(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("member-home-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        sCombo = ((ComboBox) root.getChildren().get(2));
        ObservableList<String> list = FXCollections.observableArrayList("Title", "Author", "Category", "Release date");
        addHomeWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        sCombo.setItems(list);
        stage.setScene(scene);
        stage.show();
    }

    public void searchCatalog(ActionEvent event) throws IOException, SQLException {
        if (choice != null) {
            root = FXMLLoader.load(getClass().getResource("search-catalog-screen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            addSearchedBWidgets2((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
            stage.setScene(scene);
            stage.show();
            choice = null;
        }
    }

    public void switchToReserveScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("reserve-book-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBookListScreen(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("books-list-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        sCombo = ((ComboBox) root.getChildren().get(2));
        ObservableList<String> list = FXCollections.observableArrayList("Title", "Author", "Category", "Release date");
        sCombo.setItems(list);
        addSearchedBWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }


    public void switchToResConfirmScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("reserve-confirm-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void reserve(ActionEvent event) throws IOException, SQLException {//later
        if (!reserveTF.getText().isEmpty()) {
            String bookTitle = reserveTF.getText();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            //sql new record in request table.
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
            PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO `library`.`reserve` (`MID`, `Title`, `Rdate`) VALUES (?, ?, ?);");
            myStmt.setString(1, this.currid+"");
            myStmt.setString(2,bookTitle);
            myStmt.setString(3,dtf.format(now) );
            myStmt.execute();
            ResultSet rs = myStmt.executeQuery("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");

            switchToResConfirmScreen(event);

        }
    }

    public void borrowBook(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("borrow-book-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void brrBCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("brrBCScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void renewBook(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("renew-book-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void rnuBCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("rnuBCScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    private void addHomeWidgets(ScrollPane sp) throws IOException, SQLException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String type = "physical", format = "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        int ISBN = 999, volume = 999, barcode = 999, copyNumber = 999, rackNum = 999;
        double penalty=20;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        ResultSet rs = myStmt.executeQuery("Select count(*) from book, physicalbook, borrowes where book.ISBN=physicalbook.ISBN and book.ISBN=borrowes.ISBN and physicalbook.ISBN=borrowes.ISBN and MID="+currid);
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        //I put 20 for experiment change it to records number.
        rs = myStmt.executeQuery("Select book.ISBN, book.Title, Volume, Publisher_name,Release_date,languge, Format, Barcode, Rack_number, Copy_number, borrowes.MID, SDate, RDate, Penalty   from book, physicalbook, borrowes where book.ISBN=physicalbook.ISBN and book.ISBN=borrowes.ISBN and physicalbook.ISBN=borrowes.ISBN and MID="+currid);
        rs.next();
        //I put 5 for experiment change it to records number.
        for (int i = 0; i < j; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
           // if (type == "physical") {
            ISBN = Integer.parseInt(rs.getString("ISBN"));
            volume = Integer.parseInt(rs.getString("Volume"));
            title = rs.getString("Title");
            publisherName = rs.getString("Publisher_name");
            language = rs.getString("Languge");
            releaseDate = rs.getString("Release_date");
            barcode = Integer.parseInt(rs.getString("Barcode"));
            copyNumber = Integer.parseInt(rs.getString("Copy_number"));
            rackNum = Integer.parseInt(rs.getString("Rack_number"));
            format = rs.getString("Format");
            penalty= Double.parseDouble(rs.getString("Penalty"));
            startDate=rs.getString("SDate");
            returnDate=rs.getString("RDate");

           // authors = rs.getString("Fname") + rs.getString("LName");

            this.borrowes = new Borrowes(new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors), member, startDate, returnDate, penalty);
                pane = FXMLLoader.load(getClass().getResource("borrowed-book-widgit.fxml"));
                fp.getChildren().add(pane);
                rs.next();
            //} else {
                //this.borrowes = new Borrowes(new AudioBook(), this.memberAccount, startDate, returnDate, 0.0);
                //pane = FXMLLoader.load(getClass().getResource("borrowed-book-widgit.fxml"));
                //fp.getChildren().add(pane);
           // }
        }
    }

    private void addSearchedBWidgets(ScrollPane sp) throws IOException, SQLException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String type = "physical", format= "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        int ISBN = 999, volume = 999, barcode = 999, copyNumber = 999, rackNum = 999;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        ResultSet rs = myStmt.executeQuery("Select count(*) from book, physicalbook where book.ISBN=physicalbook.ISBN and book.Avalible='A'");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        rs = myStmt.executeQuery("Select book.ISBN, book.Title, book.Volume, book.Publisher_name, Release_date,languge, Format, physicalbook.Barcode, Rack_number, Copy_number, author.Fname, author.LName from book, physicalbook ,author,wrote where book.ISBN=physicalbook.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Avalible='A';");
        rs.next();
        //I put 5 for experiment change it to records number.
        for(int i = 0; i < j; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            ISBN= Integer.parseInt(rs.getString("ISBN"));
            volume= Integer.parseInt(rs.getString("Volume"));
            barcode= Integer.parseInt(rs.getString("Barcode"));
            copyNumber= Integer.parseInt(rs.getString("Copy_number"));
            publisherName= rs.getString("Publisher_name");
            language=rs.getString("languge");
            releaseDate=rs.getString("Release_date");
            rackNum= Integer.parseInt(rs.getString("Rack_number"));
            authors= rs.getString("Fname")+" "+rs.getString("LName");
            title=rs.getString("Title");
            book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum,format, authors);
            pane = FXMLLoader.load(getClass().getResource("searched-book-widgit.fxml"));
            fp.getChildren().add(pane);
            rs.next();
        }
    }

    private void addSearchedBWidgets2(ScrollPane sp) throws IOException, SQLException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String searchStatement = catalogTF.getText();
        String type = "physical", format= "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        int ISBN = 999, volume = 999, barcode = 999, copyNumber = 999, rackNum = 999;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select * FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Title like "+searchStatement);
        if(choice.equals("Title")){
            ResultSet rs = myStmt.executeQuery("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Title like '%"+searchStatement+"%'");
            rs.next();
            int j = Integer.parseInt(rs.getString("count(*)"));
            rs = myStmt.executeQuery("Select * FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Title like '%"+searchStatement+"%'");
            rs.next();
            for (int i = 0; i < j; i++) {
                ISBN = Integer.parseInt(rs.getString("ISBN"));
                volume = Integer.parseInt(rs.getString("Volume"));
                title = rs.getString("Title");
                publisherName = rs.getString("Publisher_name");
                language = rs.getString("Languge");
                releaseDate = rs.getString("Release_date");
                barcode = Integer.parseInt(rs.getString("Barcode"));
                copyNumber = Integer.parseInt(rs.getString("Copy_number"));
                rackNum = Integer.parseInt(rs.getString("Rack_number"));
                format = rs.getString("Format");
                book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors);
                pane = FXMLLoader.load(getClass().getResource("searched-book-widgit.fxml"));
                fp.getChildren().add(pane);
                rs.next();

            }
        } else if(choice.equals("Author")){
            ResultSet rs = myStmt.executeQuery("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Author like '%"+searchStatement+"%'");
            rs.next();
            int j = Integer.parseInt(rs.getString("count(*)"));
            rs = myStmt.executeQuery("Select * FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Author like '%"+searchStatement+"%'");
            rs.next();
            for (int i = 0; i < j; i++) {
                //Retrieve sql data here and assign them to decide which type of book should be constructed.
                ISBN = Integer.parseInt(rs.getString("ISBN"));
                volume = Integer.parseInt(rs.getString("Volume"));
                title = rs.getString("Title");
                publisherName = rs.getString("Publisher_name");
                language = rs.getString("Languge");
                releaseDate = rs.getString("Release_date");
                barcode = Integer.parseInt(rs.getString("Barcode"));
                copyNumber = Integer.parseInt(rs.getString("Copy_number"));
                rackNum = Integer.parseInt(rs.getString("Rack_number"));
                format = rs.getString("Format");
                book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors);
                pane = FXMLLoader.load(getClass().getResource("searched-book-widgit.fxml"));
                fp.getChildren().add(pane);
                rs.next();

            }
        } else if(choice.equals("Category")){
            ResultSet rs = myStmt.executeQuery("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Title like '%"+searchStatement+"%'");
            rs.next();
            int j = Integer.parseInt(rs.getString("count(*)"));
            rs = myStmt.executeQuery("Select * FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Title like '%"+searchStatement+"%'");
            rs.next();
            for (int i = 0; i < j; i++) {
                //Retrieve sql data here and assigCategorym to decide which type of book should be constructed.
                book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors);
                pane = FXMLLoader.load(getClass().getResource("searched-book-widgit.fxml"));
                fp.getChildren().add(pane);

            }
        } else{
            ResultSet rs = myStmt.executeQuery("Select count(*) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Release_date like '%"+searchStatement+"%'");
            rs.next();
            int j = Integer.parseInt(rs.getString("count(*)"));
            rs = myStmt.executeQuery("Select * FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN and book.Release_Date like '%"+searchStatement+"%'");
            rs.next();
            //I put 5 for experiment change it to records number.
            for (int i = 0; i < j; i++) {
                ISBN = Integer.parseInt(rs.getString("ISBN"));
                volume = Integer.parseInt(rs.getString("Volume"));
                title = rs.getString("Title");
                publisherName = rs.getString("Publisher_name");
                language = rs.getString("Languge");
                releaseDate = rs.getString("Release_date");
                barcode = Integer.parseInt(rs.getString("Barcode"));
                copyNumber = Integer.parseInt(rs.getString("Copy_number"));
                rackNum = Integer.parseInt(rs.getString("Rack_number"));
                format = rs.getString("Format");
                book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors);
                pane = FXMLLoader.load(getClass().getResource("searched-book-widgit.fxml"));
                fp.getChildren().add(pane);
                rs.next();

            }
        }
    }

    public void selectCB(ActionEvent event) {
        choice = sCombo.getSelectionModel().getSelectedItem().toString();
        System.out.println(choice);
    }

    public static Borrowes getBorrowInfo() {
        return borrowes;
    }

    public static void setBorrowInfo(Borrowes borrowes) {
        ApplicationController.borrowes = borrowes;
    }

    public static void setBook(PhysicalBook book) {
        ApplicationController.book = book;
    }

    public static PhysicalBook getBookData() {
        return book;
    }

    /*
    ################################    Librarian    ################################
    */

    public void switchToLibrarianHomeScreen(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("librarian-home-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        addRQWidget((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBLScreen(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("librarian-bl-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        addBLWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    public void editBook(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("edit-bbok-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void eBCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("eBCScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addBLWidgets(ScrollPane sp) throws IOException, SQLException {//Done
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String type = "physical", format = "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        int ISBN = 999, volume = 999, barcode = 999, copyNumber = 999, rackNum = 999;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN;");
        ResultSet rs = myStmt.executeQuery("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        rs.next();
        int j = Integer.parseInt(rs.getString("count( book.ISBN)"));
        //I put 20 for experiment change it to records number.
        rs = myStmt.executeQuery("SELECT book.ISBN, book.Volume, book.Title, book.Publisher_name, book.Languge, book.Release_date, physicalbook.Barcode, physicalbook.Copy_number, physicalbook.Rack_number, physicalbook.Format, wrote.AuthorID, author.Fname, author.LName FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        rs.next();
        for (int i = 0; i < j; i++) {
            ISBN = Integer.parseInt(rs.getString("ISBN"));
            volume = Integer.parseInt(rs.getString("Volume"));
            title = rs.getString("Title");
            publisherName = rs.getString("Publisher_name");
            language = rs.getString("Languge");
            releaseDate = rs.getString("Release_date");
            barcode = Integer.parseInt(rs.getString("Barcode"));
            copyNumber = Integer.parseInt(rs.getString("Copy_number"));
            rackNum = Integer.parseInt(rs.getString("Rack_number"));
            format = rs.getString("Format");
            authors = rs.getString("Fname") + rs.getString("LName");

            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            // if (type == "physical"){
            book = new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors);
            pane = FXMLLoader.load(getClass().getResource("librarian-book-widgit.fxml"));
            fp.getChildren().add(pane);
            rs.next();
            //  }
            //else {
            //book = new AudioBook();
            //pane = FXMLLoader.load(getClass().getResource("borrowed-book-widgit.fxml"));
            //fp.getChildren().add(pane);
            // }
        }
    }

    private void addRQWidget(ScrollPane sp) throws IOException, SQLException {//Done
        FlowPane fp;
        Pane pane;
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String title = "Test title", requestDate = "Test request date";
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone phone number", joinDate = "Test join date", birthDate = "Test birth date";
        int id = 999, barcode = 999, borrowedBooks = 999;
        boolean active = true, status = true;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN;");
        ResultSet rs = myStmt.executeQuery("Select count(*) From requests;");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        rs = myStmt.executeQuery("Select * from person, lmember, requests where lmember.MID=requests.MID and person.id=lmember.MID");
        rs.next();
        //I put 20 for experiment change it to records number.
        for (int i = 0; i < j; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            title=rs.getString("Bookname");
            requestDate=rs.getString("RequestDate");
            firstName=rs.getString("Fname");
            lastName=rs.getString("LName");
            userName=rs.getString("Fname");
            email=rs.getString("Email");
            phoneNum=rs.getString("Phone_number");
            joinDate=rs.getString("JDate");
            birthDate=rs.getString("BDate");
            id= Integer.parseInt(rs.getString("MID"));
            barcode= Integer.parseInt(rs.getString("Barcode"));
            borrowedBooks= Integer.parseInt(rs.getString("Borrowed_bks"));
            if(rs.getString("act").equals("yes")){
                active=true;
            }else{
                active=false;
            }
            if(rs.getString("MStatus").equals("nob")){
                status=true;
            }else{
                status=false;
            }
            requests = new Requests(new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active), title, requestDate);
            pane = FXMLLoader.load(getClass().getResource("book-request-widget.fxml"));
            fp.getChildren().add(pane);
            rs.next();
        }
    }

    public void switchToMLScreen(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("librarian-ml-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        memberListWidget((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    private void memberListWidget(ScrollPane sp) throws IOException, SQLException {//Done
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone number", joinDate = "Test join date", birthDate = "Test birth date";
        int id = 999, barcode = 999, borrowedBooks = 999;
        boolean active = true, status = true;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN;");
        ResultSet rs = myStmt.executeQuery("Select count(*) From lmember");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        rs = myStmt.executeQuery("Select * from person, lmember where  person.id=lmember.MID");
        rs.next();
        //I put 20 for experiment change it to records number.
        for (int i = 0; i < j; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            firstName=rs.getString("Fname");
            lastName=rs.getString("LName");
            userName=rs.getString("Fname");
            email=rs.getString("Email");
            phoneNum=rs.getString("Phone_number");
            joinDate=rs.getString("JDate");
            birthDate=rs.getString("BDate");
            id= Integer.parseInt(rs.getString("ID"));
            barcode= Integer.parseInt(rs.getString("Barcode"));
            borrowedBooks= Integer.parseInt(rs.getString("Borrowed_bks"));
            if(rs.getString("act").equals("yes")){
                active=true;
            }else{
                active=false;
            }
            if(rs.getString("MStatus").equals("nob")){
                status=true;
            }else{
                status=false;
            }
            member = new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active);
            pane = FXMLLoader.load(getClass().getResource("edit-member-widgit.fxml"));
            fp.getChildren().add(pane);
            rs.next();
        }
    }

    public void addMemberScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-member-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void aMCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("aMCSreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addBookScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("add-book-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void aBCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("aBCScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void requestLoanScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("request-loan-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void rLCScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("rLCScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToReportsScreen1(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("report-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        reports1SWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    private void reports1SWidgets(ScrollPane sp) throws IOException, SQLException {//Done
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone number", joinDate = "Test join date", birthDate = "Test birth date";
        int id = 999, barcode = 999, borrowedBooks = 999;
        boolean active = true, status = true;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("SELECT * FROM library.lmember where act='no'");
        ResultSet rs = myStmt.executeQuery("select count(*) from lmember where act='no'");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(*)"));
        rs = myStmt.executeQuery("SELECT * FROM library.lmember, person where act='no' and ID=MID");
        rs.next();
        //I put 20 for experiment change it to records number.
        for (int i = 0; i <j ; i++) {
            //Retrieve sql data here and assign them to decide which type of member should be constructed.
            firstName=rs.getString("Fname");
            lastName=rs.getString("LName");
            userName=rs.getString("Fname");
            email=rs.getString("Email");
            phoneNum=rs.getString("Phone_number");
            joinDate=rs.getString("JDate");
            birthDate=rs.getString("BDate");
            id= Integer.parseInt(rs.getString("ID"));
            barcode= Integer.parseInt(rs.getString("Barcode"));
            borrowedBooks= Integer.parseInt(rs.getString("Borrowed_bks"));
            if(rs.getString("act").equals("yes")){
                active=true;
            }else{
                active=false;
            }
            if(rs.getString("MStatus").equals("nob")){
                status=true;
            }else{
                status=false;
            }
            member = new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active);
            pane = FXMLLoader.load(getClass().getResource("reports1-widget.fxml"));
            fp.getChildren().add(pane);
            rs.next();
        }
    }

    //List all members and their penalty amounts.
    public void switchToReportsScreen2(ActionEvent event) throws IOException, SQLException {
        root = FXMLLoader.load(getClass().getResource("all-members-screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        reports2SWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    private void reports2SWidgets(ScrollPane sp) throws IOException, SQLException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone number", joinDate = "Test join date", birthDate = "Test birth date", type = "physical", format= "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        boolean active = true, status = true;
        int id = 999, barcode = 999, borrowedBooks = 999, ISBN = 999, volume = 999, bBbarcode = 999, copyNumber = 999, rackNum = 999;;
        double penalty=999;
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count(distinct person.ID) From lmember, person, borrowes where person.ID=lmember.MID and borrowes.MID=lmember.MID; ");
        ResultSet rs = myStmt.executeQuery("Select count(distinct person.ID) From lmember, person, borrowes where person.ID=lmember.MID and borrowes.MID=lmember.MID; ");
        rs.next();
        int j = Integer.parseInt(rs.getString("count(distinct person.ID)"));
        //I put 20 for experiment change it to records number.
        rs = myStmt.executeQuery("Select lmember.MID, person.Fname, person.LName, sum(borrowes.Penalty) From lmember, person, borrowes where person.ID=lmember.MID and borrowes.MID=lmember.MID group by borrowes.MID; ");
        rs.next();
        for(int i = 0; i <= j; i++) {
            firstName=rs.getString("Fname");
            lastName=rs.getString("LName");
            id= Integer.parseInt(rs.getString("MID"));
            penalty= Double.parseDouble(rs.getString("Penalty"));

            //Retrieve sql data here and assign them to decide which type of borrow should be constructed.
            this.borrowes = new Borrowes(new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum,format, authors), new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active), startDate, returnDate, 20.0);
            pane = FXMLLoader.load(getClass().getResource("reports2-widget.fxml"));
            fp.getChildren().add(pane);
            rs.next();
        }
    }

    //List members who more than 3 books and who have exceeded 120 days for at least one book.
    public void switchToReportsScreen3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("before-due-date.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        reports3SWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    private void reports3SWidgets(ScrollPane sp) throws IOException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone number", joinDate = "Test join date", birthDate = "Test birth date", type = "physical", format = "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        boolean active = true, status = true;
        int id = 999, barcode = 999, borrowedBooks = 999, ISBN = 999, volume = 999, bBbarcode = 999, copyNumber = 999, rackNum = 999;

        //I put 20 for experiment change it to records number.
        for (int i = 0; i <= 20; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            borrowes = new Borrowes(new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors), new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active), startDate, returnDate, 20.0);
            pane = FXMLLoader.load(getClass().getResource("reports3-widget.fxml"));
            fp.getChildren().add(pane);
        }
    }

    //List members who check out books but return them at least one day before due date.
    public void switchToReportsScreen4(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("before-due-date.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        reports4SWidgets((ScrollPane) root.getChildren().get(root.getChildren().size() - 1));
        stage.setScene(scene);
        stage.show();
    }

    private void reports4SWidgets(ScrollPane sp) throws IOException {
        FlowPane fp;
        Pane pane;
        fp = (FlowPane) sp.getContent();
        fp.getChildren().clear();
        String firstName = "Test first name", lastName = "Test last name", userName = "Username", email = "Test email", phoneNum = "Phone number", joinDate = "Test join date", birthDate = "Test birth date", type = "physical", format = "Test format", title = "Test title", publisherName = "Test publisher name", language = "Test language", releaseDate = "Test release date", audioLength = "Test audio length", startDate = "Test start date", returnDate = "Test return date", authors = "Test authors";
        boolean active = true, status = true;
        int id = 999, barcode = 999, borrowedBooks = 999, ISBN = 999, volume = 999, bBbarcode = 999, copyNumber = 999, rackNum = 999;
        ;
        //I put 20 for experiment change it to records number.
        for (int i = 0; i <= 20; i++) {
            //Retrieve sql data here and assign them to decide which type of book should be constructed.
            this.borrowes = new Borrowes(new PhysicalBook(ISBN, volume, title, publisherName, language, releaseDate, barcode, copyNumber, rackNum, format, authors), new Member(firstName, lastName, userName, email, phoneNum, id, joinDate, birthDate, barcode, borrowedBooks, status, active), startDate, returnDate, 20.0);
            pane = FXMLLoader.load(getClass().getResource("reports4-widget.fxml"));
            fp.getChildren().add(pane);
        }
    }
    public String getCurrid() {
        return currid;
    }


}