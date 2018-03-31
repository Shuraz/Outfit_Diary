package edu.csu.surajpokhrel.outfit_diary.allAccess;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.csu.surajpokhrel.outfit_diary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWeather extends Fragment {
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    Typeface weatherFont;
    String lat,lon;


    public FragmentWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        View  v = inflater.inflate(R.layout.fragment_fragment_weather2, container, false);
        lat = (intent.getStringExtra(LocationActivity.PLACE_LAT)) ;
        lon=(intent.getStringExtra(LocationActivity.PLACE_LON)) ;

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");

        cityField = (TextView)v.findViewById(R.id.city_field);
        updatedField = (TextView)v.findViewById(R.id.updated_field);
        detailsField = (TextView)v.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)v.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)v.findViewById(R.id.humidity_field);
        pressure_field = (TextView)v.findViewById(R.id.pressure_field);
        weatherIcon = (TextView)v.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: "+weather_humidity);
                pressure_field.setText("Pressure: "+weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));

            }
        });
        asyncTask.execute(lat, lon); //  asyncTask.execute("Latitude", "Longitude")



        return v;
    }

}
