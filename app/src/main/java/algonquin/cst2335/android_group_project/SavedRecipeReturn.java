package algonquin.cst2335.android_group_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Purpose: Constructors for saved recipes
 * Creation Date: 31/03/2024
 *@author Gabriel Hubert
 *@version 1.0
 */
@Entity(tableName = "saved_recipes")
public class SavedRecipeReturn {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Recipe_ID")
    private String recipeId;
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "Image_URL")
    private String imageUrl;
    @ColumnInfo(name = "Summary")
    private String summary;
    @ColumnInfo(name = "Source_URL")
    private String sourceUrl;

    /** Default Constructor. */
    public SavedRecipeReturn() {
    }

    /** Fetches the row id. */
    public int getId() {
        return id;
    }

    /** Sets the row id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Fetches the saved recipe id. */
    public String getRecipeId() {
        return recipeId;
    }

    /** Sets the saved recipe id. */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    /** Fetches the saved recipe title. */
    public String getTitle() {
        return title;
    }

    /** Sets the saved recipe title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Fetches the saved recipe image url. */
    public String getImageUrl() {
        return imageUrl;
    }

    /** Sets the saved recipe image url. */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** Fetches the saved recipe summary. */
    public String getSummary() {
        return summary;
    }

    /** Sets the saved recipe summary. */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /** Fetches the saved recipe source url. */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /** Sets the saved recipe source url. */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
