package inpt.sud.instatram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    //private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private RequestQueue mRequestQueue;
    private ArrayList<ExampleItem> stations;
    private ArrayList<ExampleItem> stationModel;
    Button button_ecran2;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();



        stations = new ArrayList<>();
        stationModel=new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(context);

        mRequestQueue.getCache().clear();

        String url = "https://jsonkeeper.com/b/THK6";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray stations = data.getJSONArray("tram");

                    for (int i = 0; i < stations.length(); i++) {
                        JSONObject stationItem = stations.getJSONObject(i);

                        String name = stationItem.getString("name");
                        Log.d("demo",name.toString());

                        ExampleItem stat = new ExampleItem(name);
                        stationModel.add(stat);


                        ExampleAdapetr adapter = new ExampleAdapetr(stationModel);
                        RecyclerView mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerViewHome);

                        mRecyclerview = mRecyclerview.findViewById(R.id.recyclerViewHome);

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        mRecyclerview.setLayoutManager(mLayoutManager);

                        mRecyclerview.setAdapter(adapter);

                        //// on click of item recyclerview
                        adapter.setOnItemClickListner(new ExampleAdapetr.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                    Intent intent = new Intent(getActivity(),Ecran2.class);
                                    intent.putExtra("STATION_NAME",stationModel.get(position).getname());
                                    startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error a si mhamed " + error, Toast.LENGTH_SHORT).show();
            }

        });
        mRequestQueue.add(jsonObjectRequest);


        return view;
    }


}