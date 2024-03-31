package algonquin.cst2335.android_group_project.Dictionary;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.android_group_project.DAO.DictionaryDao;
import algonquin.cst2335.android_group_project.Dictionary.DictionaryViewModel;

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

