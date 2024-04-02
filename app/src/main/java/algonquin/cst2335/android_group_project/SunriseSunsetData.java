// SunriseSunsetData.java
package algonquin.cst2335.android_group_project;

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
