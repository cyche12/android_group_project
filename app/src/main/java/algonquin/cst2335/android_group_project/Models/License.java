package algonquin.cst2335.android_group_project.Models;

/**
 * Represents licensing information for the dictionary data or API.
 * This class includes the name of the license and a URL where more information
 * about the license can be found. It's used to ensure that licensing information
 * is accessible and can be displayed in the application as required.
 *
 * Purpose of the file: To encapsulate licensing information related to dictionary data,
 * including the name of the license and a corresponding URL for more details.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class License {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
