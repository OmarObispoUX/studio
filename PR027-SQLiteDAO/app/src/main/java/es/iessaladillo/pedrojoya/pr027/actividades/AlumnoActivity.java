package es.iessaladillo.pedrojoya.pr027.actividades;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import es.iessaladillo.pedrojoya.pr027.R;
import es.iessaladillo.pedrojoya.pr027.fragmentos.AlumnoFragment;

public class AlumnoActivity extends ActionBarActivity {

    private AlumnoFragment frgAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        // Se carga el fragmento de detalle en el FrameLayout de la actividad
        // principal, pasándole como argumento el modo en el que debe funcionar
        // el fragmento y el id del alumno.
        frgAlumno = AlumnoFragment.newInstance(
                getIntent().getExtras().getString(AlumnoFragment.EXTRA_MODO),
                getIntent().getExtras().getLong(AlumnoFragment.EXTRA_ID));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContenido, frgAlumno)
                .commit();
    }

}
