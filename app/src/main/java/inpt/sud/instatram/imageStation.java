package inpt.sud.instatram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class imageStation extends Fragment {


    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private StorageReference mStorage;
    TextView textView;
    String stationName;


    private DatabaseReference mDatabaseRef;
    private List<Upload> uploadLists  ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image_station, container, false);
        super.onCreate(savedInstanceState);

        uploadLists = new ArrayList<>();

        //mStorage = FirebaseStorage.getInstance().getReference("Station");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Les Aigües");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadLists.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Map<String, Object> user = (Map<String, Object>) snapshot1.getValue();
                    String t = (String) user.get("title");
                    String imgUrl = (String) user.get("imageUrl");
                    String date = (String) user.get("date");
                    Upload upload = new Upload(t,imgUrl,date);
                    uploadLists.add(upload);
                }

                mAdapter = new ImageAdapter(getContext(),uploadLists);

                mAdapter.notifyDataSetChanged();

                mRecyclerView = v.findViewById(R.id.recyclerViewimage);
                mRecyclerView.setHasFixedSize(true);
                //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                //mAdapter = new ImageAdapter(getContext(), uploadLists);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}








