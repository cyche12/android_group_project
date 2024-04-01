package algonquin.cst2335.android_group_project.Dictionary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Executors;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Entity.Definition;
import algonquin.cst2335.android_group_project.Entity.SearchTerm;
/**
 * ViewModel for managing UI-related data in the Dictionary application.
 * This ViewModel handles the retrieval of search terms and their associated definitions
 * from the database asynchronously and exposes LiveData objects for observation by the UI.
 * Purpose of the file: To provide a clean API for UI components to interact with the database
 * for fetching search terms and definitions, and managing definition deletion.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */
public class DictionaryViewModel extends ViewModel {

    private final DictionaryDao dictionaryDao;
    private final MutableLiveData<List<SearchTerm>> allSearchTerms = new MutableLiveData<>();
    private final MutableLiveData<List<Definition>> definitions = new MutableLiveData<>();


    public DictionaryViewModel(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    public LiveData<List<SearchTerm>> getAllSearchTerms() {
        fetchAllSearchTerms();
        return allSearchTerms;
    }

    public LiveData<List<Definition>> getDefinitionsForSearchTerm(int searchTermId) {
        fetchDefinitionsForSearchTerm(searchTermId);
        return definitions;
    }

    private void fetchAllSearchTerms() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SearchTerm> searchTerms = dictionaryDao.getAllSearchTerms();
            allSearchTerms.postValue(searchTerms);
        });
    }

    private void fetchDefinitionsForSearchTerm(int searchTermId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Definition> definitionsList = dictionaryDao.getDefinitionsForSearchTerm(searchTermId);
            definitions.postValue(definitionsList);
        });
    }

    public void deleteDefinition(Definition definition) {
        Executors.newSingleThreadExecutor().execute(() -> {
            dictionaryDao.deleteDefinition(definition);
            int searchTermId = definition.searchTermId;
            List<Definition> updatedDefinitions = dictionaryDao.getDefinitionsForSearchTerm(searchTermId);
            definitions.postValue(updatedDefinitions);

        });
    }
}

