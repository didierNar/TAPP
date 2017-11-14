package didiernarvaez.eam.tapp.Entidades;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import didiernarvaez.eam.tapp.R;
import didiernarvaez.eam.tapp.controlador.ctlGenerica;

/**
 * Created by luchobolivar on 11/13/17.
 */

public class ImageAdapter extends BaseAdapter implements AsyncResponse {

    private Context context;

    private ctlGenerica controller;

    public Integer[] imagesGranUno;


    public ImageAdapter(Context c, Integer [] image) {

        imagesGranUno = new Integer[image.length];
        for (int i = 0; i<image.length;i++){

            imagesGranUno [i] = image [i];

        }

        context = c;


    }

    @Override
    public int getCount() {
        return imagesGranUno.length;
    }

    @Override
    public Object getItem(int position) {
        return imagesGranUno[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imagesGranUno[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;
    }

    @Override
    public void processFinish(JSONObject output) throws JSONException {

    }

    @Override
    public void processFinishList(JSONArray output) throws JSONException {

    }
}
