package algonquin.cst2335.android_group_project;

import android.media.Image;
/**
 *@author Gabriel Hubert
 *@version 1.0
 */
public class RecipeReturn {
    /** String to hold the recipe title. */
    String title;
    /** String to hold the recipe id. */
    String id;
    /** String to hold the image URL. */
    String image;

    /** Constructor to hold the title, id, and image Strings. */
    RecipeReturn(String t, String c, String i){
        title = t;
        id = c;
        image = i;
    }
    /** Fetches the saved recipe title. */
    public String getTitle() {
        return title;
    }

    /** Fetches the saved recipe id. */
    public String getId() {
        return id;
    }

    /** Fetches the saved recipe image url. */
    public String getImage() {
        return image;
    }
}
