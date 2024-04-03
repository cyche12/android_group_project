package algonquin.cst2335.android_group_project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Purpose: DAO for the recipe database
 * Creation Date: 31/03/2024
 *@author Gabriel Hubert
 *@version 1.0
 */
@Dao
public interface SavedRecipeDao {
    @Insert
    void insert(SavedRecipeReturn savedRecipeReturn);
    @Delete
    void delete(SavedRecipeReturn savedRecipeReturn);
    @Query("SELECT * FROM saved_recipes")
    LiveData<List<SavedRecipeReturn>> getAllSavedRecipes();

    @Query("SELECT * FROM saved_recipes WHERE Recipe_ID = :recipeId")
    SavedRecipeReturn getSavedRecipeById(String recipeId);
    @Query("DELETE FROM saved_recipes WHERE Recipe_ID = :recipeId")
    void deleteById(String recipeId);
}
