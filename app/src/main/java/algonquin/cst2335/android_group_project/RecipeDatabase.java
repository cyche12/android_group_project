package algonquin.cst2335.android_group_project;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SavedRecipeReturn.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract SavedRecipeDao savedRecipeDao();
    private static RecipeDatabase instance;
    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
