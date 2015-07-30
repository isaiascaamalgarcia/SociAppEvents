package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.velocity.runtime.directive.Parse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eduardo.com.example.acer.sociappevents.Rest.AccessTokenService;
import eduardo.com.example.acer.sociappevents.Rest.Credentials;
import eduardo.com.example.acer.sociappevents.Rest.EventsData;
import eduardo.com.example.acer.sociappevents.Rest.EventsService;
import eduardo.com.example.acer.sociappevents.Rest.Photos;
import eduardo.com.example.acer.sociappevents.Rest.PhotosService;
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
    private static final String TAG = "Dashboard";
    private String getPreferencia;
    private int getuserId;
    private int eventId;
    private String descripcionobtenida;
    private String nombre_evento;
    private String fechaobtenida;
    private ArrayList<EventsData> listaEventos = new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private RecyclerView listEvents;
    private int listSize = 0;
    public int[] idEvent;
    public int[] posision;
    public static int idEvento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapterDashboard = new AdapterDashboard(getApplication());
        listEvents = (RecyclerView) findViewById(R.id.listEvents);
        listEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        cargarPreferencias();

        Toast.makeText(Dashboard.this, "Dashboard: " + "Token: " + getPreferencia + "\n Id: " + getuserId, Toast.LENGTH_LONG).show();

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://192.168.0.7:9000");
        RestAdapter restAdapter = builder.build();

        EventsService service = restAdapter.create(EventsService.class);

        service.getUserEvents(getuserId, getPreferencia, new Callback<List<EventsData>>() {
            @Override
            public void success(List<EventsData> eventsData, Response response) {
                try {

                    listSize = eventsData.size();
                    idEvent= new int[listSize];
                    posision = new int[listSize];
                    for (int i = 0; i < listSize; i++) {

                        nombre_evento = eventsData.get(i).getName();
                        descripcionobtenida = eventsData.get(i).getDescription();
                        fechaobtenida = eventsData.get(i).getDay();
                        eventId = eventsData.get(i).getId();

                        EventsData eventsData1 = new EventsData();
                        eventsData1.setName(nombre_evento);
                        eventsData1.setDescription(descripcionobtenida);
                        eventsData1.setDay(fechaobtenida);

                        idEvent[i] = eventId;
                        posision[i] = i;

                        listaEventos.add(eventsData1);

                    }

                    listEvents.setAdapter(adapterDashboard);
                    adapterDashboard.setListaEventos(listaEventos);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al capturar los datos: " + e, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
                Toast.makeText(getApplicationContext(), "Fatal error: " + error, Toast.LENGTH_LONG).show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
                preferencia.edit().clear().commit();

                SharedPreferences preferencia2 = getSharedPreferences("preferenceId", Context.MODE_PRIVATE);
                preferencia2.edit().clear().commit();

                finish();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        listEvents.addOnItemTouchListener(new RecyclerTouchListener(getApplication(), listEvents, new CLickListener() {

            @Override
            public void onClick(View view, int position) {

                for (int i = 0; i < listSize; i++) {

                    if (posision[i] == position) {

                        idEvento = idEvent[i];

                        startActivity(new Intent(getApplicationContext(), PhotosActivity.class));

                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void cargarPreferencias() {

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token", "");

        SharedPreferences miPreferencia2 = getSharedPreferences("preferenceId", Context.MODE_PRIVATE);
        getuserId = miPreferencia2.getInt("UserId", -1);

        try {
            if (getPreferencia.isEmpty())
                Toast.makeText(Dashboard.this, "Unauthorized  ACCES_TOKEN required", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
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

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private CLickListener cLickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CLickListener clicklistener) {

            this.cLickListener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = listEvents.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, listEvents.getChildPosition(child));
                    }

                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && cLickListener != null && gestureDetector.onTouchEvent(e)) {

                cLickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public static interface CLickListener{

        public void onClick(View view, int position);
        public void onLongClick(View view, int position);

    }



    }
