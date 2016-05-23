package epiphany_soft.wtw.Activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.Negocio.Horario;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 22/05/2016.
 */
public class ActivityAgregarHorarioPelicula extends ActivityBase implements
        Serializable, TimePickerDialog.OnTimeSetListener {
    private EditText horaTxt;
    private Spinner spnDia, spnMes, spnAnio;
    private Horario mHorario;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_horario_pelicula);
        horaTxt=(EditText) findViewById(R.id.txtHora);
        spnDia = (Spinner) findViewById(R.id.spnDia);
        spnMes = (Spinner) findViewById(R.id.spnMes);
        spnAnio = (Spinner) findViewById(R.id.spnAnio);
        mHorario = new Horario();
        setSpecialFonts();
    }

    public void showTimePickerDialog() {
        Bundle b = new Bundle();
        b.putSerializable("horario", this);
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(b);
        newFragment.show(this.getSupportFragmentManager(), "timePicker");
    }

    public void onClickCambiarHora(View v){
        showTimePickerDialog();
    }

    @Override
    //Este se activa cuando se fija la hora en el reloj
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String text =Integer.toString(hourOfDay)+":"+((minute < 10)? 0 + Integer.toString(minute):Integer.toString(minute));
        // esto le agregamos para la hora
        horaTxt.setText(text);
        mHorario.setHora(text);
    }

    public static class TimePickerFragment extends DialogFragment {
        private ActivityAgregarHorarioPelicula ev;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            Bundle b = getArguments();
            ev = (ActivityAgregarHorarioPelicula)b.getSerializable("horario");
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(this.getContext(), ev, hour, minute,
                    DateFormat.is24HourFormat(this.getContext()));
        }
    }

    private void setSpecialFonts(){
        TextView horaLabel=(TextView) findViewById(R.id.lblHora);
        horaLabel.setTypeface(SpecialFont.getInstance(this.getBaseContext()).getTypeFace());
        TextView fechaLabel=(TextView) findViewById(R.id.lblFecha);
        fechaLabel.setTypeface(SpecialFont.getInstance(this.getBaseContext()).getTypeFace());
        //Los textos
        horaTxt.setTypeface(RobotoFont.getInstance(this.getBaseContext()).getTypeFace());
    }
}
