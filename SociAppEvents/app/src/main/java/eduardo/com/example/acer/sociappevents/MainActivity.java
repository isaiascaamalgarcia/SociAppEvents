package eduardo.com.example.acer.sociappevents;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import REST.AccessToken;
import REST.AccessTokenService;
import REST.Credentials;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {
    @InjectView(R.id.app_bar)
    private Toolbar toolbar;

    @InjectView(R.id.button)
    private Button button;

    @InjectView(R.id.et_user)
    private EditText et_user;

    @InjectView(R.id.et_pass)
    private  EditText et_pass;

    private String user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar(toolbar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = et_user.getText().toString();
                pass = et_pass.getText().toString();
                Credentials credentials = new Credentials();
                credentials.setEmail(user);
                credentials.setPassword(pass);
                RestAdapter adapter = new RestAdapter.Builder().setEndpoint("192.168.173.1:9000").build();
                AccessTokenService service = adapter.create(AccessTokenService.class);
                service.createAccessToken(credentials,new Callback<AccessToken>() {
                    @Override
                    public void success(AccessToken accessToken, Response response) {
                        Toast.makeText(MainActivity.this, "Acceso correcto" + accessToken.getToken(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, "Acceso incorrecto", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
