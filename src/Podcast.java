/**
 * Клас за представяне на podcast.
 */
public class Podcast extends AudioItem {
    private String host;

    public Podcast(String title, String genre, int duration, 
                   String category, String author, int publicationYear, String host) {
        super(title, genre, duration, category, author, publicationYear);
        this.host = host;
    }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    @Override
    public String getInformation() {
        return String.format("Podcast: %s | Водещ: %s | Жанр: %s | Продължителност: %d сек | " +
                           "Категория: %s | Автор: %s | Година: %d",
                           title, host, genre, duration, category, author, publicationYear);
    }

    @Override
    public String getType() {
        return "Podcast";
    }
}
