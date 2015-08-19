package ir.irancell.course.rss;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Amir7 on 12/08/2015.
 */
public class FragmentTest extends Fragment {



    TextView textViewTest;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragmenttest, container, false);

/*        ArrayList<CustomerInformation> arrayOfUsers = new ArrayList<CustomerInformation>();

        arrayOfUsers.add(new CustomerInformation("Maryam","02122851474","09352000433","Heravi"));
        arrayOfUsers.add(new CustomerInformation("Mona","02178686878","09352000787","Asghar Tape"));
        arrayOfUsers.add(new CustomerInformation("Roshanak","021879379799","09352000877","Shariyati"));

        CustomerAdapter adapter = new CustomerAdapter(getActivity(),arrayOfUsers);
        ListView listViewCustomer = (ListView) view.findViewById(R.id.listViewCustomer);
        listViewCustomer.setAdapter(adapter);

        */

        textViewTest = (TextView) view.findViewById(R.id.textViewTest);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        //fav = menu.add("add");
        // fav.setIcon(R.drawable.disk);

        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
       inflater.inflate(R.menu.testmenu, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            // case R.id.action_settings:
            //  Log.i("6666666", "Search Bottom Clicked");
            // Intent intent = new Intent(this, CustomerSearch.class);
            // startActivity(intent);
            //  break;

            case R.id.action_test:

                Log.i("", "Menu Test is Clicked");
                textViewTest.setText("Menu Test Is Clicked");

                break;
/*
            case R.id.action_refresh:
                Log.i("Refresh is clicked","11111111111");

                break;*/


        }

        return super.onOptionsItemSelected(item);
    }


}
