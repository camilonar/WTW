package epiphany_soft.wtw.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import epiphany_soft.wtw.ActivityBase;
import epiphany_soft.wtw.Fonts.RobotoFont;
import epiphany_soft.wtw.Fonts.SpecialFont;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 11/04/2016.
 */
public class ActivityAgregarCanal extends ActivityBase {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_canal);

        setTitle("AGREGAR CANAL");

        setSpecialFonts();
    }

    private void setSpecialFonts(){
        TextView nombreLabel=(TextView) findViewById(R.id.lblNombreCanal);
        nombreLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        TextView numeroLabel=(TextView) findViewById(R.id.lblNumeroCapitulo);
        numeroLabel.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        //Los textos
        TextView nombreTxt=(TextView) findViewById(R.id.txtNombreCanal);
        nombreTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
        TextView numeroTxt=(TextView) findViewById(R.id.txtNumeroCanal);
        numeroTxt.setTypeface(RobotoFont.getInstance(this).getTypeFace());
    }

    public void onClickRegistrarCanal(View v) {

    }
}
