package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import eduardo.com.example.acer.sociappevents.Rest.AccesToken;
import eduardo.com.example.acer.sociappevents.Rest.AccessTokenService;
import eduardo.com.example.acer.sociappevents.Rest.Credentials;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.http.Body;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {
    @InjectView(R.id.acceder)
    private Button loginButton;
    @InjectView(R.id.editText)
    private EditText usuario;
    @InjectView(R.id.editText2)
    private EditText contrasenia;
    private static final String TAG="MAINACTIVITY";
    private Toolbar toolbar;
    private String getPreferencia;
    private int getPreferencia2;
    private String tokenObtenido;
    private int idObtenido=0;
    private  String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.app_bar);

            cargarPreferencias();



            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    email = usuario.getText().toString();
                    password = contrasenia.getText().toString();

                    Credentials credentials = new Credentials();
                    credentials.setEmail(email);
                    credentials.setPassword(password);

                    RestAdapter adapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.5:9000").build();
                    AccessTokenService service = adapter.create(AccessTokenService.class);
                    service.createAccessToken(credentials, new Callback<AccesToken>() {

                        @Override
                        public void success(AccesToken accesToken, Response response) {

                            tokenObtenido = accesToken.getToken();
                            idObtenido = accesToken.getId();
                            List<Header> listheaders = response.getHeaders();

                            Toast.makeText(MainActivity.this,"ListHeader: " + listheaders, Toast.LENGTH_LONG).show();


                            Toast.makeText(MainActivity.this,"MainActivity "+"Token: " + tokenObtenido+"\n Id: "+idObtenido, Toast.LENGTH_LONG).show();

                            guardarPreferencias(tokenObtenido, idObtenido);

                            finish();
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));

                        }
                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getMessage());
                            Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
    }

    public void cargarPreferencias(){
        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken",Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token","");

        SharedPreferences miPreferencia2 = getSharedPreferences("preferenceId",Context.MODE_PRIVATE);
        getPreferencia2 = miPreferencia2.getInt("id", -1);
        try {
            if (getPreferencia.isEmpty()) {
                Toast.makeText(MainActivity.this, "Es necesario iniciar sesion", Toast.LENGTH_LONG).show();
            }else{
                finish();
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        }catch(Exception e){}
    }

    public void guardarPreferencias(String token, int userId){

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        editor.putString("token",token);
        editor.commit();

        SharedPreferences UserId = getSharedPreferences("preferenceId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = UserId.edit();
        editor2.putInt("UserId", userId);
        editor2.commit();

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
