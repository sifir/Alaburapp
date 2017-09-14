package ar.com.sifir.alaburapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //CHECK IN
    public void checkIn (View v) {
        Intent miIntent = new Intent(this, FingerActivity.class);
        miIntent.putExtra("isIngressValid", false);
        startActivity(miIntent);
    }

    //CHECK OUT
    public void checkOut (View v) {
        Intent miIntent = new Intent(this, FingerActivity.class);
        miIntent.putExtra("isIngressValid", true);
        startActivity(miIntent);
    }
}
