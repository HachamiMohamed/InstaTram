package inpt.sud.instatram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapetr extends RecyclerView.Adapter<ExampleAdapetr.ExampleViewHolder> {

    private ArrayList<ExampleItem> mExampleList;   // contain names of stations
    private OnItemClickListener mListner;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListener listner){
        mListner = listner;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mNameStation;

        public ExampleViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            mNameStation = itemView.findViewById(R.id.textview_nameStation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ExampleAdapetr(ArrayList<ExampleItem> examplelist) {
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_ltem,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListner);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mNameStation.setText(currentItem.getname());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
