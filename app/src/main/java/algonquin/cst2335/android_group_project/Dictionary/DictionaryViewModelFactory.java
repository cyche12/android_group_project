package algonquin.cst2335.android_group_project.Dictionary;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Dictionary.DictionaryViewModel;

/**
 * Factory class for creating instances of the DictionaryViewModel.
 * This factory is necessary because the DictionaryViewModel requires a constructor parameter (DictionaryDao),
 * which is not supported by the default ViewModelProvider.
 * Purpose of the file: To provide a custom factory for instantiating DictionaryViewModel with the necessary DictionaryDao dependency.
 * Author: Piyalee Mangaraj
 * Lab Section: CST2335 012
 * Creation Date: 1st April 2024
 */

public class DictionaryViewModelFactory implements ViewModelProvider.Factory {
    private final DictionaryDao dictionaryDao;

    public DictionaryViewModelFactory(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DictionaryViewModel.class)) {
            return (T) new DictionaryViewModel(dictionaryDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

