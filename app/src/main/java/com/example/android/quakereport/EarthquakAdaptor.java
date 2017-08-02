package com.example.android.quakereport;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aditya on 04-07-2017.
 */

public class EarthquakAdaptor extends ArrayAdapter<Earthquake> {

    public EarthquakAdaptor(EarthquakeActivity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        final Earthquake currentword=getItem(position);

        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitude.setText(Magnitude_formater(currentword.getMagnitude()));

        TextView location=(TextView)listItemView.findViewById(R.id.location);
        location.setText(currentword.getPlace().substring(12,currentword.getPlace().length()));

        TextView date=(TextView)listItemView.findViewById(R.id.date);

        Date date1=new Date(currentword.getDate());
        date.setText(formate_Date(date1));

        TextView split=(TextView)listItemView.findViewById(R.id.split_text);
        split.setText(currentword.getPlace().substring(0,11));

        TextView time=(TextView)listItemView.findViewById(R.id.time);
        time.setText(format_time(date1));



        GradientDrawable magnitudeCircle = (GradientDrawable)magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentword.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        LinearLayout linearLayout=(LinearLayout) listItemView.findViewById(R.id.main_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String url=currentword.getUrl();
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                view.getContext().startActivity(i);
            }
        });

        return listItemView;
    }
    private String formate_Date(Date dateobject)
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateobject);
    }
    private String format_time(Date dateobject)
    {
        SimpleDateFormat timeFormate=new SimpleDateFormat("h:mm a");
         return  timeFormate.format(dateobject);
    }
    private String Magnitude_formater(double magnitude)
    {
        DecimalFormat magnitude_formate=new DecimalFormat("0.0");
        return magnitude_formate.format(magnitude);
    }
    // Fetch the background from the TextView, which is a GradientDrawable.
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;


                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
