package sg.edu.rp.c346.id21021436.p05_ndpsongsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //define variables

    EditText songTitle, singer, year;
    Button btnInsert, btnShowList;
    RadioGroup starsGroup;
    RadioButton oneStar, twoStar, threeStar, fourStar, fiveStar;
    ArrayList<Song> al;
    int stars;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songTitle = findViewById(R.id.etSongTitle);
        singer = findViewById(R.id.etSinger);
        year = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        oneStar = findViewById(R.id.oneStar);
        twoStar = findViewById(R.id.twoStar);
        threeStar = findViewById(R.id.threeStar);
        fourStar = findViewById(R.id.fourStar);
        fiveStar = findViewById(R.id.fiveStar);

        al = new ArrayList<Song>();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_SongTitle = songTitle.getText().toString();
                String str_Singer = singer.getText().toString();
                int int_Year = Integer.valueOf(year.getText().toString());

                //    Check each Radiobutton and set stars


                    if(oneStar.isChecked()) stars = 1;
                    if(twoStar.isChecked()) stars = 2;
                    if(threeStar.isChecked()) stars = 3;
                    if(fourStar.isChecked()) stars = 4;
                    if(fiveStar.isChecked()) stars = 5;




                DBHelper dbh = new DBHelper(MainActivity.this);

                //when making entry for new song, use id = 1 (arbitrary, as the true ID will be set by AUTOINCREMENT feature of database)
                Song songNew = new Song(1, str_SongTitle, str_Singer, int_Year, stars);

                long inserted_id = dbh.insertSong(songNew);

                if (inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Moving this to ShowSongs, but keeping code commented here just in case
                DBHelper dbh = new DBHelper(MainActivity.this);

                al.clear();
                al.addAll(dbh.getAllSongs());
                Intent intent = new Intent(MainActivity.this, ShowSongsActivity.class);
                intent.putExtra("songList", al);
                startActivity(intent);


            }
        });
    }
}