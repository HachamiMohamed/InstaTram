package inpt.sud.instatram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapFragment extends Fragment {

    private Context context;
    private RequestQueue mRequestQueue;
    boolean mdoubleBackToExitPressedOnce;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ;
        //Inisilize map fragment
        SupportMapFragment supportMapFragment =(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        context=getActivity();

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mRequestQueue = Volley.newRequestQueue(context);
                mRequestQueue.getCache().clear();
                mdoubleBackToExitPressedOnce = false;

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
                                Double longitude = stationItem.getDouble("lon");
                                Double latitude = stationItem.getDouble("lat");
                                Log.d("demo",name.toString());

                                LatLng position = new LatLng(latitude, longitude);
                                Log.d("demo",position.toString());
                                googleMap.addMarker(new MarkerOptions()
                                        .position(position)
                                        .title(name));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

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

                //////////////////////////////////////////
                //When we click two times on marker we move to another activity
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    private boolean mdoubleBackToExitPressedOnce;

                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if (mdoubleBackToExitPressedOnce) {
                        String makertitle = marker.getTitle();
                        Intent intent = new Intent(context,DetailsActivity.class);
                        intent.putExtra("title",makertitle);
                        startActivity(intent);
                        }else {
                            this.mdoubleBackToExitPressedOnce = true;

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mdoubleBackToExitPressedOnce = false;
                                }
                            }, 2000);
                        }
                        return false;
                    }
                });

            }
        });

        return view;
    }
}