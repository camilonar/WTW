package epiphany_soft.wtw.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Negocio.Genero;
import epiphany_soft.wtw.R;

public class ActivityRegistrarPrograma extends AppCompatActivity{
        private EditText name,sinopsis;
        private Spinner spnGenero;
        private RadioButton pel,ser;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reg_pel_ser);
            pel=(RadioButton)findViewById(R.id.but_pel);
            ser=(RadioButton)findViewById(R.id.but_ser);
            name=(EditText)findViewById(R.id.name_programa);
            sinopsis=(EditText)findViewById(R.id.sin_programa);
            crearSpinnerGeneros();
        }

        public void onClickRegistrar(View v)
        {
            String nombre=name.getText().toString();
            String sinopsisS=sinopsis.getText().toString();
            int idGen=((Genero)spnGenero.getSelectedItem()).getId();
            if (pel.isChecked()==true) {

            }
            if (ser.isChecked()==true) {

            }
        }

        public void crearSpinnerGeneros(){
            spnGenero = (Spinner) findViewById(R.id.spnGenero);
            DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
            Cursor c=db.consultarAllGeneros();
            ArrayList<Genero> generos = new ArrayList<Genero>();
            while (c.moveToNext()){
                Genero g=
                        new Genero(c.getInt(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID)),
                                c.getString(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE)));
                generos.add(g);
            }
            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,generos);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spnGenero.setAdapter(adapter);
        }

}
