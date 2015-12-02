package com.calculadoramenstrual;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	public static final String ARG_ID_VIEW= "label_fecha";
    public static final String ARG_TXT_FECHA= "fecha";
	private int idLabelFecha;
	private MainActivity activity;

	public DatePickerFragment(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		Calendar c = Calendar.getInstance();

        if((getArguments().getString(ARG_TXT_FECHA)!=null)&&(!getArguments().getString(ARG_TXT_FECHA).isEmpty())){
            String fecha = getArguments().getString(ARG_TXT_FECHA);
            c.setTime(Convertir_fechaStr_a_date_BDD(fecha));
        }
        int year = c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //obtener id label fecha
		this.idLabelFecha = getArguments().getInt(ARG_ID_VIEW);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day)	{
		// Do something with the date chosen by the user
		Date d = null;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		d = cal.getTime();

		String strFecha = Convertir_de_date_a_string(d);
		TextView labelFecha = (TextView) getActivity().findViewById(this.idLabelFecha);
		if(labelFecha!=null){
			labelFecha.setText(strFecha);
		}

		activity.calculateNextPeriod(strFecha);
	}

	public static Date Convertir_fechaStr_a_date_BDD(String Fecha) {

		SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return FormatoFecha.parse(Fecha);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static String Convertir_de_date_a_string(Date Fecha) {
		SimpleDateFormat FormatoFinal = new SimpleDateFormat("dd/MM/yyyy");
		return FormatoFinal.format(Fecha);
	}


}
