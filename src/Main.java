import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Главен клас на приложението с конзолно меню.
 */
public class Main {
    private static Catalog catalog = new Catalog();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Организатор на личен аудио каталог ===\n");
        
        // Зареждане на каталога от файл
        try {
            FileManager.loadCatalog(catalog);
            System.out.println("Каталогът е зареден успешно.\n");
        } catch (IOException e) {
            System.out.println("Нов каталог е създаден.\n");
        }
        
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readChoice();
            
            switch (choice) {
                case 1: addItem(); break;
                case 2: deleteItem(); break;
                case 3: search(); break;
                case 4: createPlaylist(); break;
                case 5: managePlaylist(); break;
                case 6: showInformation(); break;
                case 7: filter(); break;
                case 8: sort(); break;
                case 9: saveCatalog(); break;
                case 10: loadPlaylist(); break;
                case 0:
                    running = false;
                    System.out.println("Довиждане!");
                    break;
                default:
                    System.out.println("Невалиден избор!");
            }
        }
        
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== ГЛАВНО МЕНЮ ===");
        System.out.println("1. Добави нов обект");
        System.out.println("2. Изтрий обект");
        System.out.println("3. Търсене на обект");
        System.out.println("4. Създай playlist");
        System.out.println("5. Управление на playlist");
        System.out.println("6. Покажи информация");
        System.out.println("7. Филтрирай обекти");
        System.out.println("8. Сортирай обекти");
        System.out.println("9. Запиши каталог във файл");
        System.out.println("10. Зареди playlist от файл");
        System.out.println("0. Изход");
        System.out.print("\nИзберете опция: ");
    }

    private static int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addItem() {
        System.out.println("\n=== ДОБАВЯНЕ НА ОБЕКТ ===");
        System.out.println("1. Песен");
        System.out.println("2. Албум");
        System.out.println("3. Podcast");
        System.out.println("4. Аудиокнига");
        System.out.print("Изберете тип: ");
        
        int type = readChoice();
        if (type < 1 || type > 4) {
            System.out.println("Невалиден тип!");
            return;
        }
        
        System.out.print("Заглавие: ");
        String title = scanner.nextLine();
        System.out.print("Жанр: ");
        String genre = scanner.nextLine();
        System.out.print("Продължителност (секунди): ");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.print("Категория: ");
        String category = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        System.out.print("Година на публикуване: ");
        int year = Integer.parseInt(scanner.nextLine());
        
        AudioItem item = null;
        if (type == 1) {
            System.out.print("Изпълнител: ");
            String artist = scanner.nextLine();
            item = new Song(title, genre, duration, category, author, year, artist);
        } else if (type == 2) {
            item = new Album(title, genre, duration, category, author, year);
        } else if (type == 3) {
            System.out.print("Водещ: ");
            String host = scanner.nextLine();
            item = new Podcast(title, genre, duration, category, author, year, host);
        } else if (type == 4) {
            System.out.print("Разказвач: ");
            String narrator = scanner.nextLine();
            item = new Audiobook(title, genre, duration, category, author, year, narrator);
        }
        
        catalog.addItem(item);
        System.out.println("Обектът е добавен успешно!");
    }

    private static void deleteItem() {
        System.out.println("\n=== ИЗТРИВАНЕ НА ОБЕКТ ===");
        showAllItems();
        System.out.print("Въведете индекс на обекта за изтриване: ");
        int index = readChoice();
        
        if (catalog.deleteItemByIndex(index - 1)) {
            System.out.println("Обектът е изтрит успешно!");
        } else {
            System.out.println("Невалиден индекс!");
        }
    }

    private static void search() {
        System.out.println("\n=== ТЪРСЕНЕ ===");
        System.out.println("1. По заглавие");
        System.out.println("2. По автор");
        System.out.println("3. По жанр");
        System.out.println("4. По изпълнител (само за песни)");
        System.out.print("Изберете критерий: ");
        
        int criterion = readChoice();
        List<AudioItem> results = null;
        
        if (criterion == 1) {
            System.out.print("Въведете заглавие: ");
            String title = scanner.nextLine();
            results = catalog.searchByTitle(title);
        } else if (criterion == 2) {
            System.out.print("Въведете автор: ");
            String author = scanner.nextLine();
            results = catalog.searchByAuthor(author);
        } else if (criterion == 3) {
            System.out.print("Въведете жанр: ");
            String genre = scanner.nextLine();
            results = catalog.searchByGenre(genre);
        } else if (criterion == 4) {
            System.out.print("Въведете изпълнител: ");
            String artist = scanner.nextLine();
            results = catalog.searchByArtist(artist);
        } else {
            System.out.println("Невалиден критерий!");
            return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nРезултати:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).getInformation());
            }
        } else {
            System.out.println("Няма намерени резултати!");
        }
    }

    private static void createPlaylist() {
        System.out.println("\n=== СЪЗДАВАНЕ НА PLAYLIST ===");
        System.out.print("Въведете име на playlist: ");
        String name = scanner.nextLine();
        catalog.createPlaylist(name);
        System.out.println("Playlist е създаден успешно!");
    }

    private static void managePlaylist() {
        System.out.println("\n=== УПРАВЛЕНИЕ НА PLAYLIST ===");
        showAllPlaylists();
        System.out.print("Въведете име на playlist: ");
        String name = scanner.nextLine();
        Playlist playlist = catalog.findPlaylist(name);
        
        if (playlist == null) {
            System.out.println("Playlist не е намерен!");
            return;
        }
        
        System.out.println("1. Добави обект");
        System.out.println("2. Премахни обект");
        System.out.println("3. Запиши playlist във файл");
        System.out.print("Изберете опция: ");
        int choice = readChoice();
        
        if (choice == 1) {
            showAllItems();
            System.out.print("Въведете индекс на обекта: ");
            int index = readChoice();
            AudioItem item = catalog.getItemByIndex(index - 1);
            if (item != null) {
                playlist.addItem(item);
                System.out.println("Обектът е добавен към playlist!");
            } else {
                System.out.println("Невалиден индекс!");
            }
        } else if (choice == 2) {
            List<AudioItem> items = playlist.getItems();
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getInformation());
            }
            System.out.print("Въведете индекс на обекта: ");
            int index = readChoice();
            if (playlist.removeItemByIndex(index - 1)) {
                System.out.println("Обектът е премахнат от playlist!");
            } else {
                System.out.println("Невалиден индекс!");
            }
        } else if (choice == 3) {
            try {
                FileManager.savePlaylist(playlist, catalog);
                System.out.println("Playlist е записан успешно!");
            } catch (IOException e) {
                System.out.println("Грешка при запис: " + e.getMessage());
            }
        }
    }

    private static void showInformation() {
        System.out.println("\n=== ИНФОРМАЦИЯ ===");
        System.out.println("1. За обект");
        System.out.println("2. За playlist");
        System.out.print("Изберете опция: ");
        int choice = readChoice();
        
        if (choice == 1) {
            showAllItems();
            System.out.print("Въведете индекс на обекта: ");
            int index = readChoice();
            AudioItem item = catalog.getItemByIndex(index - 1);
            if (item != null) {
                System.out.println(item.getInformation());
            } else {
                System.out.println("Невалиден индекс!");
            }
        } else if (choice == 2) {
            showAllPlaylists();
            System.out.print("Въведете име на playlist: ");
            String name = scanner.nextLine();
            Playlist playlist = catalog.findPlaylist(name);
            if (playlist != null) {
                System.out.println(playlist.getInformation());
            } else {
                System.out.println("Playlist не е намерен!");
            }
        }
    }

    private static void filter() {
        System.out.println("\n=== ФИЛТРИРАНЕ ===");
        System.out.println("1. По жанр");
        System.out.println("2. По автор");
        System.out.println("3. По година");
        System.out.print("Изберете критерий: ");
        
        int criterion = readChoice();
        List<AudioItem> results = null;
        
        if (criterion == 1) {
            System.out.print("Въведете жанр: ");
            String genre = scanner.nextLine();
            results = catalog.filterByGenre(genre);
        } else if (criterion == 2) {
            System.out.print("Въведете автор: ");
            String author = scanner.nextLine();
            results = catalog.filterByAuthor(author);
        } else if (criterion == 3) {
            System.out.print("Въведете година: ");
            int year = Integer.parseInt(scanner.nextLine());
            results = catalog.filterByYear(year);
        } else {
            System.out.println("Невалиден критерий!");
            return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nРезултати:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).getInformation());
            }
        } else {
            System.out.println("Няма намерени резултати!");
        }
    }

    private static void sort() {
        System.out.println("\n=== СОРТИРАНЕ ===");
        System.out.println("1. По заглавие");
        System.out.println("2. По автор");
        System.out.println("3. По година");
        System.out.print("Изберете критерий: ");
        
        int criterion = readChoice();
        if (criterion == 1) {
            catalog.sortByTitle();
            System.out.println("Обектите са сортирани по заглавие!");
        } else if (criterion == 2) {
            catalog.sortByAuthor();
            System.out.println("Обектите са сортирани по автор!");
        } else if (criterion == 3) {
            catalog.sortByYear();
            System.out.println("Обектите са сортирани по година!");
        } else {
            System.out.println("Невалиден критерий!");
        }
    }

    private static void saveCatalog() {
        try {
            FileManager.saveCatalog(catalog);
            System.out.println("Каталогът е записан успешно!");
        } catch (IOException e) {
            System.out.println("Грешка при запис: " + e.getMessage());
        }
    }

    private static void loadPlaylist() {
        System.out.println("\n=== ЗАРЕЖДАНЕ НА PLAYLIST ===");
        System.out.print("Въведете име на playlist: ");
        String name = scanner.nextLine();
        try {
            Playlist playlist = FileManager.loadPlaylist(name, catalog);
            if (playlist != null) {
                catalog.getPlaylists().add(playlist);
                System.out.println("Playlist е зареден успешно!");
            } else {
                System.out.println("Playlist не е намерен!");
            }
        } catch (IOException e) {
            System.out.println("Грешка при зареждане: " + e.getMessage());
        }
    }

    private static void showAllItems() {
        List<AudioItem> items = catalog.getItems();
        if (items.isEmpty()) {
            System.out.println("Няма обекти в каталога!");
            return;
        }
        System.out.println("\nОбекти в каталога:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getInformation());
        }
    }

    private static void showAllPlaylists() {
        List<Playlist> playlists = catalog.getPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("Няма плейлисти!");
            return;
        }
        System.out.println("\nПлейлисти:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getName());
        }
    }
}
