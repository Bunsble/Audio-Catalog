/**
 * Базов клас за всички аудио обекти в каталога.
 * Всеки обект трябва да има жанр, продължителност, категория, автор и година на публикуване.
 */
public abstract class AudioItem {
    protected String title;
    protected String genre;
    protected int duration; // в секунди
    protected String category;
    protected String author;
    protected int publicationYear;

    public AudioItem(String title, String genre, int duration, 
                     String category, String author, int publicationYear) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.category = category;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    // Гетъри
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }
    public String getCategory() { return category; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }

    // Сетъри
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setCategory(String category) { this.category = category; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    /**
     * Връща информация за обекта като низ.
     */
    public abstract String getInformation();

    /**
     * Връща типа на обекта (песен, албум, podcast, аудиокнига).
     */
    public abstract String getType();
}
