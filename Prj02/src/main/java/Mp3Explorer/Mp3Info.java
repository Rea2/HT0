package Mp3Explorer;




import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Created by Raik Yauheni on 02.11.2018.
 */
public class Mp3Info {
    private String title;
    private String artist;
    private String album;
    private Path path;
    private long duration;
    private static final String EMPTY_FIELD = "N/A";

    public Mp3Info() {
        title = EMPTY_FIELD;
        artist = EMPTY_FIELD;
        album = EMPTY_FIELD;
        long duration  =  -1;
        artist = EMPTY_FIELD;
    }

    public Mp3Info(String title, String artist, String album, Path path, long duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public static String getEmptyField() {
        return EMPTY_FIELD;
    }

    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Artist: ").append(artist).append("\n");
        sb.append("Album : ").append(album).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Duration: ").append(durationToString()).append("\n");
        sb.append("Destination: ").append(path.toString()).append("\n");

        byte[] b = sb.toString().getBytes(Charset.defaultCharset());
        return  new String(b,Charset.defaultCharset( ));
    }

    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + " ");
        sb.append(durationToString());
         return sb.toString();
    }

    public String durationToString() {
        if (duration == -1) return "N/A";

        return  String.format ( "%02d : %02d", (duration / 60) , (duration % 60) );
    }



}



