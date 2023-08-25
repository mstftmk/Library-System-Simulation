import java.util.ArrayList;
import java.util.List;


public class LibraryMember extends Person {
    private List<Media> borrowedItems;

    public LibraryMember(String name) {
        super(name);
        this.borrowedItems = new ArrayList<>();
    }

    public void borrowItem(Media media) {
        if (!media.isBorrowed()) {
            media.borrow();
            borrowedItems.add(media);
            System.out.println(getName() + " has borrowed: " + media.getTitle());
        } else {
            System.out.println("Sorry, " + media.getTitle() + " is already borrowed.");
        }
    }

    public void returnItem(Media media) {
        if (borrowedItems.contains(media)) {
            media.returnItem();
            borrowedItems.remove(media);
            System.out.println(getName() + " has returned: " + media.getTitle());
        } else {
            System.out.println("You have not borrowed: " + media.getTitle());
        }
    }
}
