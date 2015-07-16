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

import eduardo.com.example.acer.sociappevents.Rest.AccessTokenService;
import eduardo.com.example.acer.sociappevents.Rest.Credentials;
import eduardo.com.example.acer.sociappevents.Rest.EventsData;
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
    private ArrayList<EventsData> listaEventos = new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private RecyclerView listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapterDashboard = new AdapterDashboard(getApplication());
        listEvents = (RecyclerView)findViewById(R.id.listEvents);
        listEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken",Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token","");
        Toast.makeText(Dashboard.this, "token obtenido" + getPreferencia, Toast.LENGTH_LONG).show();
        Credentials credentials = new Credentials();
        credentials.setToken(getPreferencia);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.137.126:9000").build();

        AccessTokenService service = adapter.create(AccessTokenService.class);
/**
        EventsData datos = new EventsData();
        datos.setNombre("Cumpleaños de Denisse");
        datos.setFecha("15/10/15");
        datos.setNum_invitados(15);
        datos.setNum_fotos(20);
        datos.setTipo_invitado("Invitado");
        listEvents.setAdapter(adapterDashboard);
        adapterDashboard.setListaEventos(listaEventos);
**/
        service.getData(credentials, new Callback<EventsData>() {
            @Override
            public void success(EventsData eventsData, Response response) {
                eventsData.getNombre();
                eventsData.getFecha();
                eventsData.getNum_fotos();
                eventsData.getNum_invitados();
                eventsData.getTipo_invitado();
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
