package com.example.mymangacollection.http;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import java.io.UnsupportedEncodingException;

public class HttpHelper {
    private RequestQueue requestQueue;
    private Context context;

    public HttpHelper(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(this.context);
    }

    public void post (String urlPath, String json) {
        final String data = json;

    }

    private void exibirAlerta (String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void tratarResposta (JSONArray resposta) {}
}
