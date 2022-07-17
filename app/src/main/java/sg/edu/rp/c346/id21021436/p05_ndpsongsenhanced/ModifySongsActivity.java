package sg.edu.rp.c346.id21021436.p05_ndpsongsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ModifySongsActivity extends AppCompatActivity {

    //define variables

    EditText songId, songTitle, singer, year;
    Button btnUpdate, btnDelete, btnDeleteAll, btnCancel;
    RadioGroup starsGroup;
    RadioButton oneStar, twoStar, threeStar, fourStar, fiveStar;
    ArrayList<Song> al;
    int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_songs);

        songId = findViewById(R.id.etSongID);
        songTitle = findViewById(R.id.etSongTitle);
        singer = findViewById(R.id.etSinger);
        year = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnCancel = findViewById(R.id.btnCancel);
        oneStar = findViewById(R.id.oneStar);
        twoStar = findViewById(R.id.twoStar);
        threeStar = findViewById(R.id.threeStar);
        fourStar = findViewById(R.id.fourStar);
        fiveStar = findViewById(R.id.fiveStar);

        al = new ArrayList<Song>();

        Intent intentReceivedFromSecond = getIntent();
        Song songPicked = (Song) intentReceivedFromSecond.getSerializableExtra("songPicked");

        songId.setText(songPicked.get_id() + "");
        //to prevent editing of the songId, set focusable and clickable to false
        songId.setFocusable(false);
        songId.setClickable(false);
        songTitle.setText(songPicked.getTitle() + "");
        singer.setText(songPicked.getSingers() + "");
        year.setText(songPicked.getYear() + "");
        stars = songPicked.getStars();

        //logic to set the rating stars

        switch(stars){
            case 1: oneStar.setChecked(true);
            break;
            case 2: twoStar.setChecked(true); stars = 2;
            break;
            case 3: threeStar.setChecked(true); stars = 3;
            break;
            case 4: fourStar.setChecked(true); stars = 4;
            break;
            case 5: fiveStar.setChecked(true); stars = 5;
            break;
        }


        DBHelper dbh = new DBHelper(ModifySongsActivity.this);
        btnUpdate.setOnClickListener(v -> {

            //read and set new stars value first
            if(oneStar.isChecked()) stars = 1;
            if(twoStar.isChecked()) stars = 2;
            if(threeStar.isChecked()) stars = 3;
            if(fourStar.isChecked()) stars = 4;
            if(fiveStar.isChecked()) stars = 5;

            int resultUpdate = dbh.updateSong(songPicked, songTitle.getText().toString(), singer.getText().toString(), Integer.parseInt(year.getText().toString()), stars);
            if (resultUpdate != -1) Toast.makeText(ModifySongsActivity.this, "Update successful", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllSongs());
            Intent intentGoingBackToShowSongs = new Intent(ModifySongsActivity.this, ShowSongsActivity.class);
            intentGoingBackToShowSongs.putExtra("songList", al);
            startActivity(intentGoingBackToShowSongs);
            finish();

        });

        btnDelete.setOnClickListener(v -> {
            int resultDelete = dbh.deleteSong(songPicked);
            if (resultDelete != -1) Toast.makeText(ModifySongsActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllSongs());
            Intent intentGoingBackToShowSongs = new Intent(ModifySongsActivity.this, ShowSongsActivity.class);
            intentGoingBackToShowSongs.putExtra("songList", al);
            startActivity(intentGoingBackToShowSongs);
            finish();
        });

        btnDeleteAll.setOnClickListener(v -> {
            dbh.deleteAllSongs();
            Toast.makeText(ModifySongsActivity.this, "All Songs Deleted!", Toast.LENGTH_SHORT).show();

            al.clear();
            al.addAll(dbh.getAllSongs());
            Intent intentGoingBackToShowSongs = new Intent(ModifySongsActivity.this, ShowSongsActivity.class);
            intentGoingBackToShowSongs.putExtra("songList", al);
            startActivity(intentGoingBackToShowSongs);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            al.clear();
            al.addAll(dbh.getAllSongs());
            Intent intentGoingBackToShowSongs = new Intent(ModifySongsActivity.this, ShowSongsActivity.class);
            intentGoingBackToShowSongs.putExtra("songList", al);
            startActivity(intentGoingBackToShowSongs);
            finish();

        });

    }
}