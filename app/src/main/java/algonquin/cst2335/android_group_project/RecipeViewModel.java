package algonquin.cst2335.android_group_project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class RecipeViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> messages = new MutableLiveData< >();
}
