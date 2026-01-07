import java.io.*;

/**
 * Клас за запис и четене на каталога и плейлистите във файлове (текстов формат).
 * Формат: TYPE|заглавие|жанр|продължителност|категория|автор|година|допълнително
 */
public class FileManager {
    private static final String CATALOG_FILE = "каталог.txt";
    private static final String PLAYLISTS_DIR = "плейлисти/";
    private static final String DELIMITER = "|";

    /**
     * Записва всички обекти от каталога във файл.
     */
    public static void saveCatalog(Catalog catalog) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CATALOG_FILE))) {
            for (AudioItem item : catalog.getItems()) {
                StringBuilder line = new StringBuilder();
                line.append(item.getType()).append(DELIMITER);
                line.append(item.getTitle()).append(DELIMITER);
                line.append(item.getGenre()).append(DELIMITER);
                line.append(item.getDuration()).append(DELIMITER);
                line.append(item.getCategory()).append(DELIMITER);
                line.append(item.getAuthor()).append(DELIMITER);
                line.append(item.getPublicationYear()).append(DELIMITER);
                
                if (item instanceof Song) {
                    line.append(((Song) item).getArtist());
                } else if (item instanceof Podcast) {
                    line.append(((Podcast) item).getHost());
                } else if (item instanceof Audiobook) {
                    line.append(((Audiobook) item).getNarrator());
                } else if (item instanceof Album) {
                    line.append("-"); // Албумът няма допълнително поле
                }
                
                writer.println(line.toString());
            }
        }
    }

    /**
     * Чете всички обекти от каталога от файл.
     */
    public static void loadCatalog(Catalog catalog) throws IOException {
        File file = new File(CATALOG_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CATALOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\" + DELIMITER);
                if (parts.length < 7) continue;
                
                String type = parts[0];
                String title = parts[1];
                String genre = parts[2];
                int duration = Integer.parseInt(parts[3]);
                String category = parts[4];
                String author = parts[5];
                int publicationYear = Integer.parseInt(parts[6]);
                String additional = parts.length > 7 ? parts[7] : "-";
                
                AudioItem item = null;
                if ("Песен".equals(type)) {
                    item = new Song(title, genre, duration, category, author, publicationYear, additional);
                } else if ("Podcast".equals(type)) {
                    item = new Podcast(title, genre, duration, category, author, publicationYear, additional);
                } else if ("Аудиокнига".equals(type)) {
                    item = new Audiobook(title, genre, duration, category, author, publicationYear, additional);
                } else if ("Албум".equals(type)) {
                    item = new Album(title, genre, duration, category, author, publicationYear);
                }
                
                if (item != null) {
                    catalog.addItem(item);
                }
            }
        }
    }

    /**
     * Записва плейлист във файл.
     */
    public static void savePlaylist(Playlist playlist, Catalog catalog) throws IOException {
        File dir = new File(PLAYLISTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String filename = PLAYLISTS_DIR + playlist.getName() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(playlist.getName());
            for (AudioItem item : playlist.getItems()) {
                // Записваме индекса на обекта в каталога
                int index = catalog.getItems().indexOf(item);
                if (index >= 0) {
                    writer.println(index);
                }
            }
        }
    }

    /**
     * Чете плейлист от файл.
     */
    public static Playlist loadPlaylist(String name, Catalog catalog) throws IOException {
        String filename = PLAYLISTS_DIR + name + ".txt";
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String playlistName = reader.readLine();
            if (playlistName == null) return null;
            
            Playlist playlist = new Playlist(playlistName.trim());
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int index = Integer.parseInt(line.trim());
                    AudioItem item = catalog.getItemByIndex(index);
                    if (item != null) {
                        playlist.addItem(item);
                    }
                } catch (NumberFormatException e) {
                    // Пропускаме невалидни редове
                }
            }
            return playlist;
        }
    }
}
