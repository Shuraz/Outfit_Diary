package edu.csu.surajpokhrel.outfit_diary.utility;
import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import edu.csu.surajpokhrel.outfit_diary.R;

import static edu.csu.surajpokhrel.outfit_diary.R.id.tvImageName;

/**
 * Created by surajpokhrel on 24/9/17.
 */

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        TextView tvName = (TextView) v.findViewById(tvImageName);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getName());
        Picasso.with(context).load(listImage.get(position).getUrl()).fit().centerCrop().into(img);
        return v;

    }
}
