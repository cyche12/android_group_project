public class Artist {
    private int id;
    private String name;
    private String pictureMedium;
    private String tracklist;

    // Constructor
    public Artist(int id, String name, String pictureMedium, String tracklist) {
        this.id = id;
        this.name = name;
        this.pictureMedium = pictureMedium;
        this.tracklist = tracklist;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPictureMedium() { return pictureMedium; }
    public String getTracklist() { return tracklist; }
}
