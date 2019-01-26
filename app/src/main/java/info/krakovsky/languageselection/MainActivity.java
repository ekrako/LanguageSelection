package info.krakovsky.languageselection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.english:
                Toast.makeText(MainActivity.this, R.string.english, Toast.LENGTH_SHORT).show();
                setLocale("en");
                return true;
            case R.id.spanish:
                Toast.makeText(MainActivity.this, R.string.spanish, Toast.LENGTH_SHORT).show();
                setLocale("es");
                return true;
            default:
                return false;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences =  getSharedPreferences("info.krakovsky.languageselection", Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("lang","");
        if (lang.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.alert_title)
                    .setPositiveButton(R.string.english, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, R.string.english, Toast.LENGTH_SHORT).show();
                            setLocale("en");

                        }
                    }).setNegativeButton(R.string.spanish, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, R.string.spanish, Toast.LENGTH_SHORT).show();
                    setLocale("es");
                }
            })
                    .show();
        }else{
            setLocale(lang);
        }
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        sharedPreferences.edit().putString("lang",lang).commit();
        this.invalidateOptionsMenu();
        this.setContentView(R.layout.activity_main);

    }
}
