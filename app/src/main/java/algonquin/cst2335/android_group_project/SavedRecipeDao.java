package algonquin.cst2335.android_group_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedRecipeDao {
    @Insert
    void insert(SavedRecipe savedRecipe);
    @Delete
    void delete(SavedRecipe savedRecipe);
    @Query("SELECT * FROM saved_recipes")
    LiveData<List<SavedRecipe>> getAllSavedRecipes();
}
