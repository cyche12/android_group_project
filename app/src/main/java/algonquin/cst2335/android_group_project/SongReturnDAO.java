package algonquin.cst2335.android_group_project;
/**
 * Purpose:this is the Interface for the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: March 31, 2024
 *
 */
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongReturnDAO {

    /**
     *
     * @param m The SongReturn entity to be inserted into the database.
     */
    @Insert
    void insertSong(SongReturn m);

    /**
     *
     * @return song from SongReturn
     */
    @Query("Select * from SongReturn")
    public List<SongReturn> getAllSongs();

    /**
     *
     * @return id from SongReturn
     */
    @Query("Select id from SongReturn")
    public List<Integer> getAllIds();

    /**
     *
     * @param m The ongReturn entity to be deleted from the database.
     */
    @Delete
    void deleteSong(SongReturn m);


}
