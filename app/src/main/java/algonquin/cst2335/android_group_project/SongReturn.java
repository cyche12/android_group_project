package algonquin.cst2335.android_group_project;
/**
 * Purpose:SongReturn class is returning music information for the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: March 31, 2024
 *
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SongReturn {
    /** display the album's image in the UI*/
    @ColumnInfo(name = "Album Image")
    String musicPicture;
    /**display the album's name in the UI*/
    @ColumnInfo(name = "Album Name")
    String musicAlbumName;
    /**display the Song Name in the UI*/
    @ColumnInfo(name = "Song Name")
    String musicSongName;

    /**display the Song duration in the UI*/
    @ColumnInfo(name = "Song Duration")
    String musicDuration;

    /**display the id in the UI*/
    @PrimaryKey
    @ColumnInfo(name = "id")
    int musicId;

    /**
     * default constructor
     * */
    public SongReturn() {

    }

    /**
     *
     * @param i The music ID, a unique identifier for the song
     * @param t The URL or path of the song's album image
     * @param b The name of the album to which the song belongs
     * @param f The name of the song
     * @param r The duration of the song
     */
    SongReturn(int i, String t, String b, String f, String r) {
        musicId = i;
        musicPicture = t;
        musicAlbumName = b;
        musicSongName = f;
        musicDuration = r;

    }
/**
 * @ return The unique identifier (ID) of the music item
 * */
    public int getMusicId() {
        return musicId;
    }

    /**
     *
     * @return The URL or file path of the music item's picture
     */
    public String getMusicPicture() {
        return musicPicture;
    }

    /**
     *
     * @return The name of the album as a String
     */
    public String getMusicAlbumName() {
        return musicAlbumName;
    }

    /**
     *
     * @return The name of the song as a String
     */
    public String getMusicSongName() {
        return musicSongName;
    }

    /**
     *
     * @return The duration of the music track as a String
     */
    public String getMusicDuration() {
        return musicDuration;
    }
}
