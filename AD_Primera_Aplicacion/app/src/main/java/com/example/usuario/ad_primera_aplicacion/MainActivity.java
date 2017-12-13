package com.example.usuario.ad_primera_aplicacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnBoton;
    TextView txvTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvTiempo = (TextView) findViewById(R.id.txvHora);


        btnBoton = (Button) findViewById(R.id.btnBoton);
        btnBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txvTiempo.setText(new Date().toString());
            }
        });
    }


}
