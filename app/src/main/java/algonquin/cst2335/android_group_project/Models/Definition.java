package algonquin.cst2335.android_group_project.Models;
import java.util.List;

/**
 * Represents a definition of a word, including its synonyms, antonyms, and an example usage.
 * This model is used to store and manage the data associated with each definition retrieved
 * from a dictionary API or other data source.
 *
 * Purpose of the file: To define the model for storing information about a word's definition,
 * synonyms, antonyms, and an example usage within the dictionary application.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class Definition {
    private String definition;
    private List<String> synonyms;
    private List<String> antonyms;
    private String example;

    // Getter for definition
    public String getDefinition() {
        return definition;
    }

    // Setter for definition
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    // Getter for synonyms
    public List<String> getSynonyms() {
        return synonyms;
    }

    // Setter for synonyms
    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    // Getter for antonyms
    public List<String> getAntonyms() {
        return antonyms;
    }

    // Setter for antonyms
    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    // Getter for example
    public String getExample() {
        return example;
    }

    // Setter for example
    public void setExample(String example) {
        this.example = example;
    }

}
