import java.util.ArrayList;
import java.util.List;

/**
 * Клас за управление на каталога с всички аудио обекти и playlist-и.
 */
public class Catalog {
    private List<AudioItem> items;
    private List<Playlist> playlists;

    public Catalog() {
        this.items = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    // Управление на обекти
    public void addItem(AudioItem item) {
        items.add(item);
    }

    public boolean deleteItem(AudioItem item) {
        // Премахваме обекта от всички плейлисти
        for (Playlist playlist : playlists) {
            playlist.removeItem(item);
        }
        return items.remove(item);
    }

    public boolean deleteItemByIndex(int index) {
        if (index >= 0 && index < items.size()) {
            AudioItem item = items.get(index);
            return deleteItem(item);
        }
        return false;
    }

    public List<AudioItem> searchByTitle(String title) {
        List<AudioItem> results = new ArrayList<>();
        String searchTitle = title.toLowerCase();
        for (AudioItem item : items) {
            if (item.getTitle().toLowerCase().contains(searchTitle)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<AudioItem> searchByAuthor(String author) {
        List<AudioItem> results = new ArrayList<>();
        String searchAuthor = author.toLowerCase();
        for (AudioItem item : items) {
            if (item.getAuthor().toLowerCase().contains(searchAuthor)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<AudioItem> searchByGenre(String genre) {
        List<AudioItem> results = new ArrayList<>();
        String searchGenre = genre.toLowerCase();
        for (AudioItem item : items) {
            if (item.getGenre().toLowerCase().contains(searchGenre)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<AudioItem> searchByArtist(String artist) {
        List<AudioItem> results = new ArrayList<>();
        String searchArtist = artist.toLowerCase();
        for (AudioItem item : items) {
            if (item instanceof Song) {
                Song song = (Song) item;
                if (song.getArtist().toLowerCase().contains(searchArtist)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    // Филтрация
    public List<AudioItem> filterByGenre(String genre) {
        List<AudioItem> results = new ArrayList<>();
        for (AudioItem item : items) {
            if (item.getGenre().equalsIgnoreCase(genre)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<AudioItem> filterByAuthor(String author) {
        List<AudioItem> results = new ArrayList<>();
        for (AudioItem item : items) {
            if (item.getAuthor().equalsIgnoreCase(author)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<AudioItem> filterByYear(int year) {
        List<AudioItem> results = new ArrayList<>();
        for (AudioItem item : items) {
            if (item.getPublicationYear() == year) {
                results.add(item);
            }
        }
        return results;
    }

    // Сортиране
    public void sortByTitle() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - 1 - i; j++) {
                String title1 = items.get(j).getTitle().toLowerCase();
                String title2 = items.get(j + 1).getTitle().toLowerCase();
                if (title1.compareTo(title2) > 0) {
                    AudioItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    }

    public void sortByAuthor() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - 1 - i; j++) {
                String author1 = items.get(j).getAuthor().toLowerCase();
                String author2 = items.get(j + 1).getAuthor().toLowerCase();
                if (author1.compareTo(author2) > 0) {
                    AudioItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    }

    public void sortByYear() {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = 0; j < items.size() - 1 - i; j++) {
                int year1 = items.get(j).getPublicationYear();
                int year2 = items.get(j + 1).getPublicationYear();
                if (year1 > year2) {
                    AudioItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                }
            }
        }
    }

    // Управление на плейлисти
    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public Playlist findPlaylist(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(name)) {
                return playlist;
            }
        }
        return null;
    }

    public boolean deletePlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            if (playlists.get(i).getName().equalsIgnoreCase(name)) {
                playlists.remove(i);
                return true;
            }
        }
        return false;
    }

    // Гетъри
    public List<AudioItem> getItems() { return items; }
    public List<Playlist> getPlaylists() { return playlists; }

    public AudioItem getItemByIndex(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
}
