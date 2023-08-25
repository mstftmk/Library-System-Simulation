import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private List<Media> mediaItems;
    private List<LibraryMember> libraryMembers;
    private Scanner scanner;

    private LibrarySystem() {
        this.mediaItems = new ArrayList<>();
        this.libraryMembers = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    private void run() {
        System.out.println("Welcome to the Library System!");

        while (true) {
            displayMenu();
            int option = getValidOption();

            switch (option) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addMusicRecording();
                    break;
                case 3:
                    addMember();
                    break;
                case 4:
                    borrowItem();
                    break;
                case 5:
                    returnItem();
                    break;
                case 6:
                	displayMediaItemsByGenre();
                	break;
                case 7:
                	viewMostBorrowedItems();
                	break;
                case 8:
                	checkPersonRole();
                	break;
                case 9:
                	saveToTextFile();
                	break;
                case 0:
                    System.out.println("Exiting the Library System. Goodbye!");
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add a new book to the library system");
        System.out.println("2. Add a new music recording to the library system");
        System.out.println("3. Add a new member to the library system");
        
        System.out.println("4. Borrow an item from the library system");
        System.out.println("5. Return a item to the library system");
        
        System.out.println("6. Display media items by genre");
        System.out.println("7. View most borrowed books and music recordings");
        System.out.println("8. Check whether a person is an author or producer");
        System.out.println("9. Save all new media items and members to a text file");
        System.out.println("0. Quit");
    }

    private int getValidOption() {
        int option;
        while (true) {
            try {
                System.out.print("Enter option number: ");
                option = scanner.nextInt();
                scanner.nextLine();
                if (option >= 0 && option <= 9) {
                    break;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 8.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return option;
    }

    private void addBook() {
        System.out.println("\nAdding a new book to the library system:");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter author's name: ");
        String authorName = scanner.nextLine();

        Person author = new Person(authorName);
        Book book = new Book(title, genre, author);
        mediaItems.add(book);

        System.out.println("Book added successfully!");
    }

    private void addMusicRecording() {
        System.out.println("\nAdding a new music recording to the library system:");

        System.out.print("Enter music recording title: ");
        String title = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter producer's name: ");
        String producerName = scanner.nextLine();

        Person producer = new Person(producerName);
        MusicRecord musicRecording = new MusicRecord(title, genre, producer);
        mediaItems.add(musicRecording);

        System.out.println("Music recording added successfully!");
    }

    private void addMember() {
        System.out.println("\nAdding a new member to the library system:");

        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        LibraryMember member = new LibraryMember(name);
        libraryMembers.add(member);

        System.out.println("Member added successfully!");
    }
    
    
    private void borrowItem() {
    	System.out.println("\nBorrowing an item from the library system:");
    	
    	System.out.print("Enter your name: ");
        String personName = scanner.nextLine();
        boolean memberFound = false;
        
        for(LibraryMember member: libraryMembers) {
        	if(member.getName().equalsIgnoreCase(personName)) {
        		
        		memberFound = true;
 
                System.out.print("Enter item's name: ");
                String name = scanner.nextLine();
                
                boolean recordFound = false;

                for (Media item : mediaItems) {
                    if (item.getTitle().equalsIgnoreCase(name)) {
                    	member.borrowItem(item);
                        recordFound = true;
                    }
                }
                if (!recordFound) {
                    System.out.println("\nNo item found in the specified name.");
                } 
        	}
        }
        
        if (!memberFound) {
    		System.out.println("\nNo member found in the database.");
    	}
        
    }

    
    private void returnItem() {
    	System.out.println("\nReturning an item to the library system:");

    	System.out.print("Enter your name: ");
        String personName = scanner.nextLine();
        boolean memberFound = false;
        
        for(LibraryMember member: libraryMembers) {
        	if(member.getName().equalsIgnoreCase(personName)) {
        		
        		memberFound = true;
 
                System.out.print("Enter item's name: ");
                String name = scanner.nextLine();
                
                boolean recordFound = false;
                for (Media item : mediaItems) {
                    if (item.getTitle().equalsIgnoreCase(name)) {
                    	member.returnItem(item);
                        recordFound = true;
                    }
                }
                if (!recordFound) {
                    System.out.println("\nNo item found in the specified name.");
                }  
        	}
        }      
    	if (!memberFound) {
    		System.out.println("\nNo member found in the database.");
    	}
        
    }
    

    private void displayMediaItemsByGenre() {
        System.out.println("\nDisplaying media items by genre:");

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        boolean found = false;
        for (Media media : mediaItems) {
            if (media.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(media.getTitle() + " (" + media.getClass().getSimpleName() + ")");
                found = true;
            }
        }

        if (!found) {
            System.out.println("\nNo media items found in the specified genre.");
        }
    }

    private void viewMostBorrowedItems() {
        System.out.println("\nViewing most borrowed books and music recordings:");

        System.out.println("Most borrowed books:");
        displayMostBorrowedItems(Book.class);

        System.out.println("Most borrowed music recordings:");
        displayMostBorrowedItems(MusicRecord.class);
    }

    private void displayMostBorrowedItems(Class<?> mediaClass) {
        List<Media> filteredItems = new ArrayList<>();
        for (Media media : mediaItems) {
            if (media.getClass().equals(mediaClass)) {
                filteredItems.add(media);
            }
        }

        if (!filteredItems.isEmpty()) {
            filteredItems.sort(Comparator.comparingInt(Media::getBorrowCount).reversed());
            int maxBorrowCount = filteredItems.get(0).getBorrowCount();

            for (Media media : filteredItems) {
                if (media.getBorrowCount() == maxBorrowCount) {
                    System.out.println(media.getTitle() + " (" + media.getClass().getSimpleName() + ")");
                    System.out.println("Borrow count: " + media.getBorrowCount());
                } else {
                    break;
                }
            }
        } else {
            System.out.println("No " + mediaClass.getSimpleName() + " found.");
        }
    }

    private void checkPersonRole() {
        System.out.println("\nChecking person's role (author or producer):");

        System.out.print("Enter person's name: ");
        String personName = scanner.nextLine();

        boolean isAuthor = false;
        boolean isProducer = false;

        for (Media media : mediaItems) {
            if (media instanceof Book) {
                Book book = (Book) media;
                if (book.getAuthor().getName().equalsIgnoreCase(personName)) {
                    isAuthor = true;
                    break;
                }
            } else if (media instanceof MusicRecord) {
                MusicRecord musicRecord = (MusicRecord) media;
                if (musicRecord.getProducer().getName().equalsIgnoreCase(personName)) {
                    isProducer = true;
                    break;
                }
            }
        }

        if (isAuthor) {
            System.out.println(personName + " is an author.");
        } else if (isProducer) {
            System.out.println(personName + " is a producer.");
        } else {
            System.out.println("No media found for " + personName + ".");
        }
    }

    private void saveToTextFile() {
        System.out.println("\nSaving all new media items and members to a text file...");

        try (PrintWriter writer = new PrintWriter(new FileWriter("library_data.txt"))) {
            writer.println("Media Items:");
            writer.println("------------");

            for (Media media : mediaItems) {
                if (media instanceof Book) {
                    Book book = (Book) media;
                    writer.println("--Book--");
                    writer.println("-Name:"+book.getTitle());
                    writer.println("-Genre:"+book.getGenre());
                    writer.println("-Author:"+book.getAuthor().getName());
                } else if (media instanceof MusicRecord) {
                    MusicRecord musicRecord = (MusicRecord) media;
                    writer.println("--MusicRecord--");
                    writer.println("-Name:"+musicRecord.getTitle());
                    writer.println("-Genre:"+musicRecord.getGenre());
                    writer.println("-Producer:"+musicRecord.getProducer().getName());
                }

                writer.println();
            }

            // Writing library members
            writer.println("Library Members:");
            writer.println("---------------");

            for (LibraryMember member : libraryMembers) {
                writer.println("-"+member.getName());
            }

            System.out.println("Data saved to library_data.txt file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the data to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.run();
    }
}