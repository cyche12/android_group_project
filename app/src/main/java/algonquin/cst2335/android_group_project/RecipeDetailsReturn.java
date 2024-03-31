package algonquin.cst2335.android_group_project;

public class RecipeDetailsReturn {
    String summary;
    String url;
    String image;

    RecipeDetailsReturn(String s, String u, String i){
        summary = s;
        url = u;
        image = i;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }
}
