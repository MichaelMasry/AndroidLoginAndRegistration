package info.androidhive.loginandregistration.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.Landmark;

public class Main3Activity extends AppCompatActivity {


    private final String url3 = "http://192.168.1.5/userDB/loadDB.php";
    private ListView listView;
    private ArrayList<Landmark> landmarkList;
    private LandMarkAdapter landMarkAdapter;
    private Location currentLocation;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = findViewById(R.id.listView);
        landmarkList = new ArrayList<>();
        landMarkAdapter = new LandMarkAdapter(this, landmarkList);
        //gpsLoc(this);
        //to get current location values
        client = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddd");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation.setLatitude(location.getLatitude());
                    currentLocation.setLongitude(location.getLongitude());
                    System.out.println("X axiiiiiiiiiiiiis " + location.getLatitude());
                    System.out.println("Y axiiiiiiiiiiiiiiiis " + location.getLongitude());
                }
            }
        });
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        loadData();
    }

    public void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject lmObj = jsonArray.getJSONObject(i);
                        double latitude = Double.parseDouble(lmObj.getString("Latitude"));
                        double longitude = Double.parseDouble(lmObj.getString("Longitude"));
                        double distance = calculateDistance(latitude,longitude);
                        String name = lmObj.getString("Name");
                        double rate = Double.parseDouble(lmObj.getString("rating"));
                        //String image = lmObj.getString("image_path");
                        String phone = lmObj.getString("Phone_Number");
                        Landmark lm = new Landmark(name, distance, rate, phone);
                        landmarkList.add(lm);
                    }
                    listView.setAdapter(landMarkAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main3Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    class LandMarkAdapter extends ArrayAdapter<Landmark> {
        //   private Context context;
        //    private List<Landmark> landmarkList;
        LandMarkAdapter(Context context, ArrayList<Landmark> landmarkList) {
            super(context, 0, landmarkList);
            //   this.context=context;
            //   this.landmarkList=landmarkList;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Get the data item for this position
            Landmark user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
            }
            // Lookup view for data population
            TextView tvName;
            tvName = convertView.findViewById(R.id.textViewTitle);
            TextView tvHome;
            tvHome = convertView.findViewById(R.id.textViewShortDesc);
            TextView ratingTextView;
            ratingTextView = convertView.findViewById(R.id.textViewRating);
            // Populate the data into the template view using the data object
            tvName.setText(user.getName());
            tvHome.setText(user.getDistance() + "");
            ratingTextView.setText(user.getRate()+"");
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public double calculateDistance(double A, double B) {
        Location temp = new Location("Landmark");
        temp.setLatitude(A);
        temp.setLongitude(B);
        currentLocation.setLongitude(10.0);
        currentLocation.setLatitude(10.0);
        System.out.println("X axiiiiiiiiiiiiis " + currentLocation.getLatitude());
        System.out.println("Y axiiiiiiiiiiiiiiiis " + currentLocation.getLongitude());
        return (double) temp.distanceTo(currentLocation);
    }

    public void gpsLoc(Context context) {
        android.location.LocationManager manager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager != null) {
            for (String provider : manager.getAllProviders()) {
                if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    return;
                }
                Location location = manager.getLastKnownLocation(provider);
                if (location != null) {
                    currentLocation.setLatitude(location.getLatitude());
                    currentLocation.setLongitude(location.getLongitude());
                    System.out.println("X axiiiiiiiiiiiiis " + currentLocation.getLatitude());
                    System.out.println("Y axiiiiiiiiiiiiiiiis " + currentLocation.getLongitude());
                }
            }
        }
    }
}