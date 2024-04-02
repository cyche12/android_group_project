package algonquin.cst2335.android_group_project;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SongReturn {
    @ColumnInfo(name = "Album Image")
    String musicPicture;
    @ColumnInfo(name = "Album Name")
    String musicAlbumName;
    @ColumnInfo(name = "Song Name")
    String musicSongName;
    @ColumnInfo(name = "Song Duration")
    String musicDuration;
    @PrimaryKey
    @ColumnInfo(name = "id")
    int musicId;


    public SongReturn() {

    }

    SongReturn(int i, String t, String b, String f, String r) {
        musicId = i;
        musicPicture = t;
        musicAlbumName = b;
        musicSongName = f;
        musicDuration = r;

    }

    public int getMusicId() {
        return musicId;
    }

    public String getMusicPicture() {
        return musicPicture;
    }

    public String getMusicAlbumName() {
        return musicAlbumName;
    }

    public String getMusicSongName() {
        return musicSongName;
    }

    public String getMusicDuration() {
        return musicDuration;
    }
}
