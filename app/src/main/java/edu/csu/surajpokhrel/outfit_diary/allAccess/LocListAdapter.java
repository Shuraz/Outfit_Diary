package edu.csu.surajpokhrel.outfit_diary.allAccess;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.csu.surajpokhrel.outfit_diary.R;

public class LocListAdapter extends ArrayAdapter {

    private Activity context;
    List<LocationInfo> place;
    public LocListAdapter(Activity context,List<LocationInfo>place){
        super(context,R.layout.activity_loc_lisc_adapter,place);
        this.context=context;
        this.place=place;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewPlace= inflater.inflate(R.layout.activity_loc_lisc_adapter,null,true);

        TextView tvName=listViewPlace.findViewById(R.id.pname);
        TextView tvLat = listViewPlace.findViewById(R.id.plat);
        TextView tvLon=listViewPlace.findViewById(R.id.plog);
        TextView tvInfo= listViewPlace.findViewById(R.id.pinfo);

        LocationInfo location=place.get(position);
        tvName.setText(location.getPlaceName());
        tvLat.setText(location.getLat());
        tvLon.setText(location.getLog());
        tvInfo.setText(location.getInfo());
        return listViewPlace;
    }
}