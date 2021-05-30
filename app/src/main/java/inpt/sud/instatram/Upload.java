package inpt.sud.instatram;

import android.widget.EditText;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class Upload {
    private String mTitle;
    private String mImageUrl;
    private String mKey;
    private String mDate;

    public  Upload(){
    }


    public Upload(String name, String imageUrl,String mDate) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.mDate = mDate;
        mTitle = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mTitle;
    }

    public void setName(String name) {
        mTitle = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }



    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}






