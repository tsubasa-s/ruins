package ac.ruins.asuka.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import com.github.gfx.android.orma.annotation.Database;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import br.com.mauker.materialsearchview.MaterialSearchView;

import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback{

    RuinDao dao;
    MaterialSearchView searchView;
    GoogleMap sGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Ruin> ruins = new Select().from(Ruin.class).orderBy("Name ASC").limit("5").where("name like ?", "%"+newText+"%").execute();
                List<String> sug = new ArrayList<String>();

                for(Ruin ruin: ruins){
                    sug.add(ruin.name);
                    Log.d("suggest", ruin.name);
                }

                searchView.addSuggestions(sug);
                return false;
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ruin ruin = (Ruin)new Select().from(Ruin.class).where("name = ?", searchView.getSuggestionAtPosition(i)).execute().get(0);
                sGoogleMap.clear();
                sGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ruin.longtitude, ruin.latitude), 20));
                sGoogleMap.addMarker(new MarkerOptions().position(new LatLng(ruin.longtitude, ruin.latitude)).title(ruin.name));
                searchView.closeSearch();
            }
        });

        searchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                // Do something once the view is open.
            }

            @Override
            public void onSearchViewClosed() {
                // Do something once the view is closed.
            }
        });
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_search:

                searchView.openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        sGoogleMap = googleMap;

        final List<Ruin> list = new Select().from(Ruin.class).execute();

        for(Ruin ruin : list){
            Log.d("ruins", ruin.name);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(ruin.longtitude, ruin.latitude)).title(ruin.name));
            Log.d("long", String.valueOf( ruin.longtitude));
        }


        googleMap.setOnMarkerClickListener(this);
//               googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.681382, 139.766084), 15));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
