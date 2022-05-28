package Entities;

import java.time.LocalDate;

public class PhysicalBook extends Book{
    private int barcode, copyNumber, rackNum;
    private String format;

    public PhysicalBook(){

    }

    public PhysicalBook(int ISBN, int volume, String title, String publisherName, String language, String releaseDate, int barcode, int copyNumber, int rackNum, String format, String authors) {
        super(ISBN, volume, title, publisherName, language, releaseDate, authors);
        this.barcode = barcode;
        this.copyNumber = copyNumber;
        this.format = format;
        this.rackNum = rackNum;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRackNum() {
        return rackNum;
    }

    public void setRackNum(int rackNum) {
        this.rackNum = rackNum;
    }
}
