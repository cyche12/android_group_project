package algonquin.cst2335.android_group_project.Dictionary;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.android_group_project.Models.APIResponse;
import algonquin.cst2335.android_group_project.Models.Definition;
import algonquin.cst2335.android_group_project.Models.Meaning;

public class DictionaryApiRequest {
    private final Context context;

    public DictionaryApiRequest(Context context) {
        this.context = context;
    }

    public void getDefinitions(String word, final ResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response using Gson
                        Gson gson = new Gson();
                        Type wordEntryType = new TypeToken<List<APIResponse>>(){}.getType();
                        List<APIResponse> wordEntries = gson.fromJson(response, wordEntryType);

                        // Extract definitions
                        List<String> definitions = new ArrayList<>();
                        for (APIResponse entry : wordEntries) {
                            for (Meaning meaning : entry.getMeanings()) {
                                for (Definition definition : meaning.getDefinitions()) {
                                    definitions.add(definition.getDefinition());
                                }
                            }
                        }

                        // Pass definitions to the listener
                        listener.onResponse(definitions);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    public interface ResponseListener {
        void onResponse(List<String> definitions);
        void onError(String message);
    }
}
