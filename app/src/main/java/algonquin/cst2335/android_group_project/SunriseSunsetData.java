//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

/**
 * Represents sunrise and sunset data with time and the details returned from the website URL.
 * This class stores the sunrise and sunset times as Strings so they can be called into classes or added to the database.
 */
public class SunriseSunsetData {
    private final String sunriseTime;
    private final String sunsetTime;

    public SunriseSunsetData(String sunriseTime, String sunsetTime) {
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }
}