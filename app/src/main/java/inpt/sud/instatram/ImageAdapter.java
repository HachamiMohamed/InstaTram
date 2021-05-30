package inpt.sud.instatram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {



    private Context mContext;
    private List<Upload> mUploads ;
    private AdapterView.OnItemClickListener mListener;
    private FirebaseStorage firebaseStorage;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.images, parent, false);
        return new ImageAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload upload = mUploads.get(position);
        holder.title.setText(upload.getName());
        holder.date.setText((CharSequence) upload.getmDate());
        Glide.with(mContext)
                .load(upload.getImageUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount () {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public TextView date;

        public ImageViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name_image);
            imageView = itemView.findViewById(R.id.image);
            date=itemView.findViewById(R.id.image_date);

        }
    }
}
