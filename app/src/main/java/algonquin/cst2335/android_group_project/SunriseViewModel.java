//Student: Jake Elliott//
//Student #040732505//
//Class: CST2335//
//Creation Date: 25/3/24//
//Group Members: Jake Elliott, Gabriel Hubert, Shilpi Sarkar, Piyalee Mangaraj//
//Project: Final Group Project//
//App: Sunrise/Sunset App//

package algonquin.cst2335.android_group_project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel for the Sunrise search feature in the application.
 * This ViewModel stores and manages UI-related data concerning the latitude and longitude inputs
 * by the user. It survives changes such as screen rotations or changing applications, ensuring data is retained.
 */
public class SunriseViewModel extends ViewModel {
    /**
     * LiveData holding the latitude input text. It allows the UI to observe changes to the latitude input
     * so that the UI can react to data changes.
     */
    public final MutableLiveData<String> latitudeText = new MutableLiveData<>();

    /**
     * LiveData holding the longitude input text. It allows the UI to observe changes to the latitude input
     * so that the UI can react to data changes.
     */
    public final MutableLiveData<String> longitudeText = new MutableLiveData<>();
}
