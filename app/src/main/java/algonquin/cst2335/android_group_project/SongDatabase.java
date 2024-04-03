package algonquin.cst2335.android_group_project;
/**
 * Purpose:To set database of the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: April 1, 2024
 *
 */
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {SongReturn.class}, version = 1)

public abstract class SongDatabase extends RoomDatabase {
    /**
     *
     * @return An instance of SongReturnDAO, ready for use to perform data operations on
     *  SongReturn entities.
     *
     */
    public abstract SongReturnDAO cmDAO();
}