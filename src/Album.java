import java.util.ArrayList;
import java.util.List;

/**
 * Клас за представяне на музикален албум.
 */
public class Album extends AudioItem {
    private List<Song> songs;

    public Album(String title, String genre, int duration, 
                String category, String author, int publicationYear) {
        super(title, genre, duration, category, author, publicationYear);
        this.songs = new ArrayList<>();
    }

    public List<Song> getSongs() { return songs; }

    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public String getInformation() {
        return String.format("Албум: %s | Жанр: %s | Продължителност: %d сек | " +
                           "Категория: %s | Автор: %s | Година: %d | Брой песни: %d",
                           title, genre, duration, category, author, publicationYear, songs.size());
    }

    @Override
    public String getType() {
        return "Албум";
    }
}
