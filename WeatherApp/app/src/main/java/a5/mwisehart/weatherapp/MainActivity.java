package a5.mwisehart.weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements UIBind{
    private Spinner spinnerLocations;
    private APIBridge apiBridge;
    private TextView txtLatitude, txtLongitude, txtHigh, txtLow, txtCurrent, txtFeelsLike,
        txtPressure, txtHumidity, txtWeatherDescription, txtWindSpeed;
    NetworkImageView imageWeatherIcon;
    ImageView imgWindDir;
    RequestQueue requestQueue;
    ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.apiBridge = new APIBridge(this, getApplicationContext());
        this.txtLatitude = findViewById(R.id.txtLatitude);
        this.txtLongitude = findViewById(R.id.txtLongitude);
        this.txtHigh = findViewById(R.id.txtHigh);
        this.txtLow = findViewById(R.id.txtLow);
        this.txtCurrent = findViewById(R.id.txtCurrent);
        this.txtFeelsLike = findViewById(R.id.txtFeelsLike);
        this.txtPressure = findViewById(R.id.txtPressure);
        this.txtHumidity = findViewById(R.id.txtHumidity);
        this.txtWeatherDescription = findViewById(R.id.txtWeatherDescription);
        this.txtWindSpeed = findViewById(R.id.txtWindSpeed);
        this.imgWindDir = findViewById(R.id.imgWindDir);
        this.imageWeatherIcon = findViewById(R.id.imgWeatherIcon);
        this.requestQueue = Volley.newRequestQueue(this);
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });


        this.spinnerLocations = findViewById(R.id.spinLocations);

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LOCATION", spinnerLocations.getSelectedItem().toString());
                apiBridge.GenerateWeatherModel(spinnerLocations.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void mapUI(WeatherModel weatherModel) {
        txtLongitude.setText(String.valueOf(weatherModel.getLon()));
        txtLatitude.setText(String.valueOf(weatherModel.getLat()));
        txtHigh.setText(String.format("%sF", weatherModel.getTempMax()));
        txtLow.setText(String.format("%sF", weatherModel.getTempMin()));
        txtCurrent.setText(String.format("%sF", weatherModel.getTemp()));
        txtFeelsLike.setText(String.format("%sF", weatherModel.getFeelsLike()));
        txtPressure.setText(String.format("%shPa", weatherModel.getPressure()));
        txtHumidity.setText(String.format("%d%%s", weatherModel.getHumidity()));
        imageWeatherIcon.setImageUrl(weatherModel.getWeatherIcon(), this.imageLoader);
        txtWeatherDescription.setText(weatherModel.getWeatherDescription());
        txtWindSpeed.setText(String.format("%sMPH", weatherModel.getWindSpeed()));
        imgWindDir.setRotation(weatherModel.getWindDirection());


    }
}