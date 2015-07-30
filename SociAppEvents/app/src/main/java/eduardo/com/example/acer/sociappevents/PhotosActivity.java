package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import eduardo.com.example.acer.sociappevents.Rest.Photos;
import eduardo.com.example.acer.sociappevents.Rest.PhotosService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PhotosActivity extends ActionBarActivity {

    private static final String TAG = "Photos Activity";
    private String titulo;
    private String descripcion;
    private String tipo;
    private String fechaFoto;
    private String Base64;
    protected String data;
    protected Bitmap photo;
    private String getPreferencia;
    private int getuserId;
    private AdapterPhotos adapterPhotos;
    private RecyclerView listPhotos;
    private ArrayList<Photos> listaPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        adapterPhotos = new AdapterPhotos(getApplication());
        listPhotos = (RecyclerView) findViewById(R.id.listPhotos);
        listPhotos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        cargarPreferencias();

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://192.168.0.7:9000");
        final RestAdapter restAdapter = builder.build();

        PhotosService service = restAdapter.create(PhotosService.class);
        service.getEventPhotos(getPreferencia, getuserId, Dashboard.idEvento, new Callback<List<Photos>>() {
            @Override
            public void success(List<Photos> photos, Response response) {
                for (int i = 0; i < photos.size(); i++) {
                    titulo = photos.get(i).getTitle();
                    tipo = photos.get(i).getType();
                    Base64 = photos.get(i).getBase64();
                    descripcion = photos.get(i).getDescription();
                    fechaFoto = photos.get(i).getDatePhoto();

                    setData(Base64);
                    Photos data = new Photos();
                    data.setPhoto(photo);
                    data.setTitle(titulo);
                    data.setDatePhoto(fechaFoto);
                    data.setDescription(descripcion);
                    data.setType(tipo);

                    listaPhotos.add(data);

                }

                listPhotos.setAdapter(adapterPhotos);
                adapterPhotos.setListaFotos(listaPhotos);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
                Toast.makeText(PhotosActivity.this, "Error: "+ error, Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void cargarPreferencias() {

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token", "");

        SharedPreferences miPreferencia2 = getSharedPreferences("preferenceId", Context.MODE_PRIVATE);
        getuserId = miPreferencia2.getInt("UserId", -1);

        try {
            if (getPreferencia.isEmpty())
                Toast.makeText(PhotosActivity.this, "NECESITA EL ACCES_TOKEN", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public void setData(String data) {
        this.data = data;

        int newWidth = 200;
        int newHeight = 200;

        try {

            byte[] byteData = android.util.Base64.decode(data, android.util.Base64.DEFAULT);
            this.photo = BitmapFactory.decodeByteArray(byteData, 0, byteData.length);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
