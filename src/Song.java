/**
 * Клас за представяне на песен.
 */
public class Song extends AudioItem {
    private String artist;

    public Song(String title, String genre, int duration, 
                String category, String author, int publicationYear, String artist) {
        super(title, genre, duration, category, author, publicationYear);
        this.artist = artist;
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    @Override
    public String getInformation() {
        return String.format("Песен: %s | Изпълнител: %s | Жанр: %s | Продължителност: %d сек | " +
                           "Категория: %s | Автор: %s | Година: %d",
                           title, artist, genre, duration, category, author, publicationYear);
    }

    @Override
    public String getType() {
        return "Песен";
    }
}
