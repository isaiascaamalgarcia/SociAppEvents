package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eduardo.com.example.acer.sociappevents.Rest.AccessTokenService;
import eduardo.com.example.acer.sociappevents.Rest.Credentials;
import eduardo.com.example.acer.sociappevents.Rest.EventsData;
import eduardo.com.example.acer.sociappevents.Rest.EventsService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_dashboard)
public class Dashboard extends RoboActivity {

    @InjectView(R.id.botonsalir)
    private Button logout;
    private String getPreferencia;
    private int getuserId;
    private ArrayList<EventsData> listaEventos = new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private RecyclerView listEvents;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapterDashboard = new AdapterDashboard(getApplication());
        listEvents = (RecyclerView)findViewById(R.id.listEvents);
        listEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken",Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token","");

        SharedPreferences userid = getSharedPreferences("preferenceUserId",Context.MODE_PRIVATE);
        getuserId = userid.getInt("userid",0);

        Toast.makeText(Dashboard.this, "token obtenido" + getPreferencia, Toast.LENGTH_LONG).show();

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://192.168.0.1:9000");
        RestAdapter restAdapter = builder.build();

        EventsService service = restAdapter.create(EventsService.class);

        service.getUserEvents(getuserId, getPreferencia, new Callback<List<EventsData>>() {
            @Override
            public void success(List<EventsData> eventsDatas, Response response) {



            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
                preferencia.edit().clear().commit();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_cerrar) {
            Toast.makeText(this, "Se cerrara la sesion", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
