package epiphany_soft.wtw.Activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import epiphany_soft.wtw.Activities.Canal.ActivityAsociarCanal;
import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
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
    private int currentDay, currentMonth, currentYear;
    private int idPrograma;
    private String idCanal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_horario_pelicula);
        Bundle b = getIntent().getExtras();
        idPrograma = b.getInt(DataBaseContract.ProgramaContract.COLUMN_NAME_PROGRAMA_ID);
        idCanal = b.getString(DataBaseContract.CanalContract.COLUMN_NAME_CANAL_ID);
        horaTxt=(EditText) findViewById(R.id.txtHora);
        spnDia = (Spinner) findViewById(R.id.spnDia);
        spnMes = (Spinner) findViewById(R.id.spnMes);
        spnAnio = (Spinner) findViewById(R.id.spnAnio);
        mHorario = new Horario();
        mHorario.setIdPrograma(idPrograma);
        mHorario.setNombreCanal(idCanal);
        setTitle("AGREGAR HORARIO");
        crearSpinners();
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

    public void onClickAgregar(View v){
        mHorario.setFecha(currentDay, currentMonth, currentYear);
        DataBaseConnection db = new DataBaseConnection(this);
        if (db.insertarHorario(mHorario)){
            ActivityConsultarHorarioPelicula.actualizado=true;
            ActivityAsociarCanal.actualizado=true;
            this.createToast("Horario agregado");
            this.finish();
        } else this.createToast("Ocurrió un error");
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

    public ArrayList<Integer> llenarSpinnerDias(int anio, int mes){
        int numDias;
        ArrayList<Integer> dias = new ArrayList<Integer>();
        switch (mes){
            case (Calendar.JANUARY):
                numDias = 31;
                break;
            case (Calendar.FEBRUARY):
                numDias = ((anio%4==0 && anio%100!=0 || anio%400==0)? 29 : 28);
                break;
            case (Calendar.MARCH):
                numDias = 31;
                break;
            case (Calendar.APRIL):
                numDias = 30;
                break;
            case (Calendar.MAY):
                numDias = 31;
                break;
            case (Calendar.JUNE):
                numDias = 30;
                break;
            case (Calendar.JULY):
                numDias = 31;
                break;
            case (Calendar.AUGUST):
                numDias = 31;
                break;
            case (Calendar.SEPTEMBER):
                numDias = 30;
                break;
            case (Calendar.OCTOBER):
                numDias = 31;
                break;
            case (Calendar.NOVEMBER):
                numDias = 30;
                break;
            case (Calendar.DECEMBER):
                numDias = 31;
                break;
            default:
                numDias = 30;
                break;
        }
        for (int i=1;i<=numDias;i++)
            dias.add(i);
        return dias;
    }
    private void crearSpinnerDias(int anio, int mes){
        final ArrayList<Integer> dias = llenarSpinnerDias(anio,mes-1);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,dias){
            public View getView(int position,View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                if (position <= dias.size()) {
                    ((TextView) v).setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                }
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.simple_spinner_item, parent,
                        false);
                TextView make = (TextView) row.findViewById(R.id.text1);
                make.setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                make.setText(dias.get(position).toString());
                return make;
            }
        };
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnDia.setAdapter(adapter);
        spnDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentDay = (Integer) spnDia.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private ArrayList<Integer> llenarSpinnerMeses(){
        int numMeses = 12;
        ArrayList<Integer> meses = new ArrayList<Integer>();
        for (int i=1;i<=numMeses;i++)
            meses.add(i);
        return meses;
    }
    private ArrayList<Integer> llenarSpinnerAnios(){
        int maxAnio = 2020;
        int minAnio = 1980;
        ArrayList<Integer> anios = new ArrayList<Integer>();
        for (int i=minAnio;i<=maxAnio;i++)
            anios.add(i);
        return anios;
    }

    private void crearSpinners(){
        Calendar c = Calendar.getInstance();
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentMonth = c.get(Calendar.MONTH)+1;
        currentYear = c.get(Calendar.YEAR);
        crearSpinnerDias(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
        final ArrayList<Integer> meses = llenarSpinnerMeses();
        ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.simple_spinner_item,meses){
            public View getView(int position,View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                if (position <= meses.size()) {
                    ((TextView) v).setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                }
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.simple_spinner_item, parent,
                        false);
                TextView make = (TextView) row.findViewById(R.id.text1);
                make.setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                make.setText(meses.get(position).toString());
                return make;
            }
        };
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnMes.setAdapter(adapter2);
        spnMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentMonth!=(Integer)spnMes.getSelectedItem()) {
                    crearSpinnerDias((Integer) spnAnio.getSelectedItem(), (Integer) spnMes.getSelectedItem());
                    currentMonth = (Integer)spnMes.getSelectedItem();
                    currentDay = (currentDay<=spnDia.getCount())? currentDay:spnDia.getCount();
                    spnDia.setSelection(currentDay-1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final ArrayList<Integer> anios = llenarSpinnerAnios();
        ArrayAdapter adapter3 = new ArrayAdapter(this,R.layout.simple_spinner_item,anios){
            public View getView(int position,View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                if (position <= anios.size()) {
                    ((TextView) v).setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                }
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.simple_spinner_item, parent,
                        false);
                TextView make = (TextView) row.findViewById(R.id.text1);
                make.setTypeface(RobotoFont.getInstance(this.getContext()).getTypeFace());
                make.setText(anios.get(position).toString());
                return make;
            }
        };
        adapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnAnio.setAdapter(adapter3);
        spnAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentYear!=(Integer)spnAnio.getSelectedItem()) {
                    crearSpinnerDias((Integer) spnAnio.getSelectedItem(), (Integer) spnMes.getSelectedItem());
                    currentYear = (Integer)spnAnio.getSelectedItem();
                    currentDay = (currentDay<=spnDia.getCount())? currentDay:spnDia.getCount();
                    spnDia.setSelection(currentDay-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnMes.setSelection(c.get(Calendar.MONTH));
        spnAnio.setSelection(c.get(Calendar.YEAR) - 1980);
        spnDia.setSelection(c.get(Calendar.DAY_OF_MONTH) - 1);
    }
}
