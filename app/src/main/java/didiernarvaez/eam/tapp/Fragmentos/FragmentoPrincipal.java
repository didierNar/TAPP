package didiernarvaez.eam.tapp.Fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import didiernarvaez.eam.tapp.R;

/**
 * Created by luchobolivar on 11/10/17.
 */

public class FragmentoPrincipal extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.primer_fragment, container, false);

        return view;

    }
}
