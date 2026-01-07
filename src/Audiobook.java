/**
 * Клас за представяне на аудиокнига.
 */
public class Audiobook extends AudioItem {
    private String narrator;

    public Audiobook(String title, String genre, int duration, 
                     String category, String author, int publicationYear, String narrator) {
        super(title, genre, duration, category, author, publicationYear);
        this.narrator = narrator;
    }

    public String getNarrator() { return narrator; }
    public void setNarrator(String narrator) { this.narrator = narrator; }

    @Override
    public String getInformation() {
        return String.format("Аудиокнига: %s | Разказвач: %s | Жанр: %s | Продължителност: %d сек | " +
                           "Категория: %s | Автор: %s | Година: %d",
                           title, narrator, genre, duration, category, author, publicationYear);
    }

    @Override
    public String getType() {
        return "Аудиокнига";
    }
}
