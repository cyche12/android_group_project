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
     * @param i
     * @param t
     * @param b
     * @param f
     * @param r
     */
    SongReturn(int i, String t, String b, String f, String r) {
        musicId = i;
        musicPicture = t;
        musicAlbumName = b;
        musicSongName = f;
        musicDuration = r;

    }
/**
 * @ return music
 * */
    public int getMusicId() {
        return musicId;
    }

    /**
     *
     * @return
     */
    public String getMusicPicture() {
        return musicPicture;
    }

    /**
     *
     * @return
     */
    public String getMusicAlbumName() {
        return musicAlbumName;
    }

    /**
     *
     * @return
     */
    public String getMusicSongName() {
        return musicSongName;
    }

    /**
     *
     * @return
     */
    public String getMusicDuration() {
        return musicDuration;
    }
}
