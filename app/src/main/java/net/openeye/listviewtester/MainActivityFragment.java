package net.openeye.listviewtester;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import javax.xml.validation.Validator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    private String company = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setAdapter(new ListViewAdapter(getActivity(), company));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String str = (String) l.getItemAtPosition(position);
        if (str.contains("Company")) {
            MainActivityFragment frag = new MainActivityFragment();
            frag.setCompany(str);
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_fragment_container, frag)
                    .addToBackStack("back")
                    .commit();
        } else {
            Toast.makeText(getActivity(), "Site Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
