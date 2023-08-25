public class Book extends Media {
	private Person author;

    public Book(String title, String genre, Person author) {
        super(title, genre);
        this.author = author;
    }

    public Person getAuthor() {
        return author;
    }
}