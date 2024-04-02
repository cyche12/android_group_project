package algonquin.cst2335.android_group_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public SavedRecipeReturn() {

    }

    public SavedRecipeReturn(String recipeId, String title, String imageUrl, String summary, String sourceUrl) {
        this.recipeId = recipeId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.sourceUrl = sourceUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
