import java.util.ArrayList;
import java.util.List;

/**
 * Клас за представяне на playlist.
 */
public class Playlist {
    private String name;
    private List<AudioItem> items;

    public Playlist(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<AudioItem> getItems() { return items; }

    public void addItem(AudioItem item) {
        items.add(item);
    }

    public boolean removeItem(AudioItem item) {
        return items.remove(item);
    }

    public boolean removeItemByIndex(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }

    public String getInformation() {
        StringBuilder info = new StringBuilder();
        info.append("Playlist: ").append(name).append("\n");
        info.append("Брой обекти: ").append(items.size()).append("\n");
        info.append("Обекти:\n");
        for (int i = 0; i < items.size(); i++) {
            info.append((i + 1)).append(". ").append(items.get(i).getInformation()).append("\n");
        }
        return info.toString();
    }

    public int getTotalDuration() {
        int total = 0;
        for (AudioItem item : items) {
            total += item.getDuration();
        }
        return total;
    }
}
