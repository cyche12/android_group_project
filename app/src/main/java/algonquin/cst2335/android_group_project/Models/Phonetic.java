package algonquin.cst2335.android_group_project.Models;

/**
 * Represents the phonetic information of a word, including the phonetic text,
 * an audio URL for pronunciation, a source URL for the phonetic information, and
 * licensing information for the phonetic data.
 *
 * Purpose of the file: To encapsulate phonetic information related to words within
 * the dictionary application, allowing for detailed representation of pronunciation
 * and associated metadata.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class Phonetic {
    private String text;
    private String audio;
    private String sourceUrl;
    private License license;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }
}
