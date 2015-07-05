package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eduardo.com.example.acer.sociappevents.REST.AccesToken;
import eduardo.com.example.acer.sociappevents.REST.AccessTokenService;
import eduardo.com.example.acer.sociappevents.REST.Credentials;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
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
    private Toolbar toolbar;
    private boolean deviceLearnedToken;
    private boolean fromsavedinstancestate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceLearnedToken = Boolean.valueOf(readFromPreferences(getApplicationContext(),"false"));
        if (savedInstanceState!=null){
            fromsavedinstancestate=true;
        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= usuario.getText().toString();
                final String pasword= contrasenia.getText().toString();
                Credentials credentials = new Credentials();
                credentials.setEmail(email);
                credentials.setPassword(pasword);
                RestAdapter adapter = new RestAdapter.Builder().setEndpoint("Localhost:3000").build();
                AccessTokenService service= adapter.create(AccessTokenService.class);
                service.createAccessToken(credentials, new Callback<AccesToken>() {
                    @Override
                    public void success(AccesToken accesToken, Response response) {
                        Toast.makeText(MainActivity.this,"acceso correcto" + accesToken.getToken(),Toast.LENGTH_LONG).show();

                        if (!deviceLearnedToken){
                            deviceLearnedToken=true;
                            saveToPreferences(getApplicationContext(),accesToken.getToken());
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this,"Acceso denegado para el correo "+email+" y contraseña "+ pasword +", osn Incorrectos",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        //setSupportActionBar(toolbar);
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

        return super.onOptionsItemSelected(item);
    }

    public void saveToPreferences(Context context,String preferenceValue){

        SharedPreferences sharedPreferences = context.getSharedPreferences("settoken",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("settoken",preferenceValue);
        editor.apply();

    }

    public static String readFromPreferences(Context context, String defaultValue){

        SharedPreferences sharedPreferences = context.getSharedPreferences("gettoken",Context.MODE_PRIVATE);

        return sharedPreferences.getString("gettoken", defaultValue);
    }
}
