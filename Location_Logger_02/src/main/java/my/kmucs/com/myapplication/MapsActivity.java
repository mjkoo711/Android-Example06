package my.kmucs.com.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MyDB mydb;
    SQLiteDatabase sqlite;
    Cursor cursor;
    Intent i;
    PolylineOptions polylineOptions;
    MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mydb = new MyDB(this);
        polylineOptions = new PolylineOptions();
        markerOptions = new MarkerOptions();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        sqlite = mydb.getReadableDatabase();
        String sql = "SELECT * FROM location";
        cursor = sqlite.rawQuery(sql, null);

        while(cursor.moveToNext()){
            LatLng temp = new LatLng(Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
            mMap.addMarker((markerOptions.position(temp).title(cursor.getString(4))));
            mMap.setMinZoomPreference(13);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));

            //선연결하는 문장
            Polyline line = mMap.addPolyline(polylineOptions.add(temp).width(5).color(Color.RED));
        }

    }
}
