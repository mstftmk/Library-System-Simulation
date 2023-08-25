public class MusicRecord extends Media {
    private Person producer;

    public MusicRecord(String title, String genre, Person producer) {
        super(title, genre);
        this.producer = producer;
    }

    public Person getProducer() {
        return producer;
    }
}
