package algonquin.cst2335.android_group_project;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongReturnDAO {


    @Insert
    void insertSong(SongReturn m);

    @Query("Select * from SongReturn")
    public List<SongReturn> getAllSongs();

    @Query("Select id from SongReturn")
    public List<Integer> getAllIds();

    @Delete
    void deleteSong(SongReturn m);


}
