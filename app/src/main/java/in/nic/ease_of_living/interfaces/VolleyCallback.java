package in.nic.ease_of_living.interfaces;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallback {
        void onVolleySuccessResponse(JSONObject response);

        void onVolleyErrorResponse(VolleyError e);

        Response<JSONObject> onVolleyNetworkResponse(NetworkResponse response);
}



