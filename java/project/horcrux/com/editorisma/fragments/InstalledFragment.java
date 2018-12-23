package project.horcrux.com.editorisma.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import project.horcrux.com.editorisma.R;
import project.horcrux.com.editorisma.utilities.TemplateAdapter;

public class InstalledFragment extends Fragment {

    RequestQueue requestQueue;
    ArrayList<String> list = new ArrayList<String>();
    String url = "";//your API
    RecyclerView recyclerView;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_installed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("PleaseWait");
        dialog.setCancelable(false);
        dialog.show();

        recyclerView = view.findViewById(R.id.installedrecycler);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                dialog.dismiss();

                for (int i = 0; i < response.length(); i++) {


                    try {
                        list.add(response.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                recyclerView.setAdapter(new TemplateAdapter(list,getContext()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }
}
