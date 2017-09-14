package ar.com.sifir.alaburapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import be.appfoundry.nfclibrary.activities.NfcActivity;

/**
 * Created by Sifir on 14/09/2017.
 */

public class NFCActivity extends NfcActivity {
    private final byte[] pass = {123, 17, -120, -112};
    private static final String TAG = NfcActivity.class.getName();
    Boolean isIngressValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        //INTENTS
        Intent intent = getIntent();
        Bundle miBundle = intent.getExtras();
        isIngressValid = miBundle.getBoolean("isIngressValid");
    }

    private String formatPassValue(byte value) {
        String s = Integer.toHexString(value + 128);
        if (s.length() == 1) s = "0" + s;
        return s.toUpperCase();
    }

    private void drawKey(final int position, final boolean valid) {
        final Context self = this;
        final EditText editText = (EditText) findViewById(
                position == 0 ? R.id.text_key_1 :
                        position == 1 ? R.id.text_key_2 :
                                position == 2 ? R.id.text_key_3 : R.id.text_key_4
        );
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setText(formatPassValue(pass[position]));
                editText.setTextColor(ContextCompat.getColor(self,
                        valid ? android.R.color.holo_green_light : android.R.color.holo_red_light));
            }
        }, (position + 1)* 50);
    }

    private boolean isValidId(byte[] arr) {
        for (int i = 0; i < 4; i++) {
            drawKey(i, arr[i] == pass[i]);
            if (arr[i] != pass[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle b = intent.getExtras();
        byte[] arr = b.getByteArray("android.nfc.extra.ID");

        if (arr != null) {
            if (isValidId(arr)) {
                if (isIngressValid == true) {
                    Toast.makeText(this, "Buen trabajo, hasta la proxima!", Toast.LENGTH_LONG).show();
                    Intent miIntent = new Intent(this, MainActivity.class);
                    miIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(miIntent);
                } else {
                    Toast.makeText(this, "Lectura correcta, a laburar!", Toast.LENGTH_LONG).show();
                    Intent miIntent = new Intent(this, MainActivity.class);
                    miIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(miIntent);
                }
            } else {
                Toast.makeText(this, "Error, chip NFC erroneo", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error de lectura de tarjeta", Toast.LENGTH_LONG).show();
        }
    }
}
