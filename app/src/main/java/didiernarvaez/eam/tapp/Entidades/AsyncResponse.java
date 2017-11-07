package didiernarvaez.eam.tapp.Entidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Didier_Narváez on 2/11/2017.
 */

public interface AsyncResponse {
    void processFinish(JSONObject output) throws JSONException;

    void processFinishList(JSONArray output) throws JSONException;
}
