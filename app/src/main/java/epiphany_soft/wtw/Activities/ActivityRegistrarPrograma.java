package epiphany_soft.wtw.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import epiphany_soft.wtw.R;

public class ActivityRegistrarPrograma extends AppCompatActivity{
        private EditText name,gen,sinopsis;
        private RadioButton pel,ser;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reg_pel_ser);
            pel=(RadioButton)findViewById(R.id.but_pel);
            ser=(RadioButton)findViewById(R.id.but_ser);
            name=(EditText)findViewById(R.id.name_programa);
            gen=(EditText)findViewById(R.id.gen_programa);
            sinopsis=(EditText)findViewById(R.id.sin_programa);
        }

        public void onClickRegistrar(View v)
        {
            String nombre=name.getText().toString();
            String genero=gen.getText().toString();
            String sinopsis=gen.getText().toString();
            if (pel.isChecked()==true)
            {

            }
            if (ser.isChecked()==true)
            {

            }
        }

}
