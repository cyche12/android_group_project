package algonquin.cst2335.android_group_project;
/**
 * Purpose:MySingleton class using to make a single instance for the application
 * Author: Shilpi Sarkar
 * Lab section:012
 * Date created: April 1, 2024
 *
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;
    /**
     *  @param context The application context used to ensure a single instance across the app.
     * */

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    /**
                     *  @param url The URL of the image to retrieve from the cache. This URL acts as the key
                     *  *            for the cached bitmap entries.
                     *  * @return The cached bitmap image if found; {@code null} otherwise. The absence of an
                     *  *         image in the cache typically triggers a new load operation from the network or
                     *  *         storage.
                     * */


                    @Override
                    public Bitmap getBitmap(String url) {

                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }
    /**
     * @param context The application context used for creating or retrieving the singleton instance.
     * @return The single instance of the  class.
     *
     */

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }
/**
 * @return The initialized  instance for networking operations.
 * This instance is globally available within the app through the singleton pattern.
 * */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);
    }
 /**
  * @return The singleton  instance for use throughout the app.
  * */
    public ImageLoader getImageLoader() {

        return imageLoader;
    }
}
