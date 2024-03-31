package algonquin.cst2335.android_group_project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class RecipeDetailsViewModel extends ViewModel {
    public MutableLiveData<ArrayList<RecipeDetailsReturn>> details = new MutableLiveData<>(new ArrayList<>());
}
