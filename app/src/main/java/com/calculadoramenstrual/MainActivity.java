package com.calculadoramenstrual;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button fecha;
    private int month, year,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        Toast.makeText(this, "Ingrese fecha en que termino su ultimo periodo menstrual", Toast.LENGTH_LONG).show();

        fecha = (Button)findViewById(R.id.button);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        Bundle args = new Bundle();
        TextView txtFecha = (TextView) findViewById(R.id.FechaUltPeriodo);
        if (txtFecha!=null)
            args.putString(DatePickerFragment.ARG_TXT_FECHA, txtFecha.getText().toString());
        args.putInt(DatePickerFragment.ARG_ID_VIEW, R.id.FechaUltPeriodo);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }


    public void calculateNextPeriod(String fecha){

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_datos);
        linearLayout.setVisibility(View.VISIBLE);

        String  dias[] = fecha.split("/");
        Calendar calendar = Calendar.getInstance();
        day = Integer.valueOf(dias[0]);
        month = Integer.valueOf(dias[1]) - 1;
        year = Integer.valueOf(dias[2]);
        Log.d("fecha separada ", dias[0] + "/" + dias[1] + "/" + dias[2]);
        calendar.set(year, month, day);

        calendar.add(Calendar.DAY_OF_MONTH, +28);
        Date d = calendar.getTime();

        //coloco la fecha para proximo periodo menstrual en base a 29 dias
        TextView nextPeriod = (TextView) findViewById(R.id.txtNextPeriodo);
        SimpleDateFormat newFechaPeriod = new SimpleDateFormat("dd/MM/yyyy");
        nextPeriod.setText(newFechaPeriod.format(d));
        Log.d("newFechaPeriod", newFechaPeriod.format(d));

        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date inicioFertilidad = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +7);
        Date finFertilidad = calendar.getTime();
        TextView diasF = (TextView) findViewById(R.id.txtNextFertilidad);
        SimpleDateFormat newFechadiasF = new SimpleDateFormat("dd/MM/yyyy");
        diasF.setText("Desde: "+newFechaPeriod.format(inicioFertilidad)+" hasta: "+newFechadiasF.format(finFertilidad));
        Log.d("newFechadiasF", newFechadiasF.format(inicioFertilidad));

    }

}
