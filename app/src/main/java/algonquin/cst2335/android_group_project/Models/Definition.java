package algonquin.cst2335.android_group_project.Models;
import java.util.List;
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
