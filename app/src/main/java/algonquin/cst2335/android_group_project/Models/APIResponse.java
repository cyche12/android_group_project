package algonquin.cst2335.android_group_project.Models;
import java.util.List;

/**
 * Represents the structure of a response from the dictionary API.
 * This class includes the word being defined, its phonetic transcription, a list of phonetics,
 * meanings, licensing information, and source URLs.
 * Purpose of the file: To provide a model for parsing the JSON response from the dictionary API,
 * facilitating the extraction and use of various components of the API response within the application.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
public class APIResponse {
    String word="";
    String phonetic;
    List<Phonetic> phonetics;
    List<Meaning> meanings;
    License license;
    List<String> sourceUrls;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
    }
}
