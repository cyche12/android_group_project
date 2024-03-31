package algonquin.cst2335.android_group_project;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SavedRecipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract SavedRecipeDao savedRecipeDao();

}
