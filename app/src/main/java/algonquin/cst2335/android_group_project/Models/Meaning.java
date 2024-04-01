package algonquin.cst2335.android_group_project.Models;
import java.util.List;

/**
 * Represents the meaning of a word, encapsulating the part of speech,
 * a list of definitions, and lists of synonyms and antonyms.
 * This class is typically used as part of a larger structure to represent
 * comprehensive dictionary entries.
 *
 * Purpose of the file: To define a model for storing detailed information about
 * the meaning of a word, including its part of speech, definitions, synonyms, and antonyms,
 * within the context of a dictionary application.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class Meaning {
    private String partOfSpeech;
    private List<Definition> definitions;
    private List<String> synonyms;
    private List<String> antonyms;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }
}
