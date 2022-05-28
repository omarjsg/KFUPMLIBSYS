package Entities;

public class AudioBook extends Book{
    private String audioLength;

    public AudioBook(int ISBN, int volume, String title, String publisherName, String language, String releaseDate, String audioLength, String authors) {
        super(ISBN, volume, title, publisherName, language, releaseDate, authors);
        this.audioLength = audioLength;
    }

    public String getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(String audioLength) {
        this.audioLength = audioLength;
    }
}
