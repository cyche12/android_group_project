package algonquin.cst2335.android_group_project;

import android.media.Image;

public class RecipeReturn {
    String title;
    String id;
    String image;

    RecipeReturn(String t, String c, String i){
        title = t;
        id = c;
        image = i;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
