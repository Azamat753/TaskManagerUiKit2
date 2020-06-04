package com.lawlett.taskmanageruikit.map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.QuickActivity;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.lawlett.taskmanageruikit.BuildConfig.MAPBOX_KEY;

public class MapBoxActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapLongClickListener {

    protected MapView mapView;
    protected MapboxMap map;
    protected AlertDialog.Builder adBuilder;
    protected final String DIRECTION_LAYER = "DIRECTION_LAYER";
    protected final String DIRECTION_SOURCE = "DIRECTION_SOURCE";
    ImageView dropPinView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Mapbox.getInstance(this,MAPBOX_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_box);

        mapView = findViewById(R.id.map_view);
        mapView.getMapAsync(this);

    getLocation();
    }

    public void getLocation() {
         dropPinView = new ImageView(this);
        dropPinView.setImageResource(R.drawable.ic_pin_drop_black_24dp);
        FrameLayout.LayoutParams params = new
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);
        mapView.addView(dropPinView);


    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        adBuilder = new AlertDialog.Builder(MapBoxActivity.this);
        adBuilder.setTitle("MapBox title");
        adBuilder.setMessage("MapBox message");
        adBuilder.setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(MapBoxActivity.this, QuickActivity.class);
                    intent.putExtra("lat", point.getLatitude());
                    intent.putExtra("lon", point.getLongitude());
                    startActivity(intent);
                });
        return true;
    }
    public void getPosition(){
        LatLng position = map.getProjection().fromScreenLocation(new
                PointF(dropPinView.getLeft() + (dropPinView.getWidth() / 2), dropPinView.getBottom()));
        Toast.makeText(this, "pos"+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,QuickActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            map = mapboxMap;
            initSources();
        });
mapboxMap.addOnMapLongClickListener(this);
    }
    private void initSources() {
        map.getStyle().addSource(new GeoJsonSource(DIRECTION_SOURCE));
        map.getStyle().addLayer(new LineLayer(DIRECTION_LAYER, DIRECTION_SOURCE).withProperties(
                PropertyFactory.lineWidth(12f),
                PropertyFactory.lineColor(Color.parseColor("#596CD9"))
        ));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void click(View view) {
        getPosition();
    }
}
