package sg.edu.rp.c346.id21021436.p05_ndpsongsenhanced;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowSongsActivity extends AppCompatActivity {
    ListView lv;
    Button btnFiveStars;

    ArrayList<Song> alSongList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_songs);

    lv = findViewById(R.id.listSongs);
    btnFiveStars = findViewById(R.id.buttonFiveStars);

    Intent intentReceived = getIntent();

    alSongList = (ArrayList<Song>) intentReceived.getSerializableExtra("songList");

    ArrayAdapter<Song> aaSongList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alSongList);

    lv.setAdapter(aaSongList);

    DBHelper dbh = new DBHelper(ShowSongsActivity.this);

    btnFiveStars.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    alSongList.clear();
    alSongList.addAll(dbh.getFiveStar());
    aaSongList.notifyDataSetChanged();

    //this is a way to make a button disappear programmatically, good to do because Show Five Stars becomes irrelevant
    btnFiveStars.setVisibility(View.GONE);


        }
    });

    lv.setClickable(true);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Song songPicked = (Song) lv.getItemAtPosition(position);
            Intent intentToModifySongs = new Intent(ShowSongsActivity.this, ModifySongsActivity.class);
            intentToModifySongs.putExtra("songPicked", songPicked);
            startActivity(intentToModifySongs);

        }
    });
    }

}
