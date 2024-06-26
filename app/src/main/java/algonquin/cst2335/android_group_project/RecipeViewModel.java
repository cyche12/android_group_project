package algonquin.cst2335.android_group_project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 * Purpose: Recipe View Model
 * Creation Date: 30/03/2024
 *@author Gabriel Hubert
 *@version 1.0
 */
public class RecipeViewModel extends ViewModel {
    /** Used to hold and manage list of RecipeReturn. */
    public MutableLiveData<ArrayList<RecipeReturn>> recipes = new MutableLiveData<>(new ArrayList<>());
}
