package inpt.sud.instatram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView markertitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        markertitle = findViewById(R.id.marker_title);

        String title = getIntent().getStringExtra("title");
        markertitle.setText(title);
    }
}