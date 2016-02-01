package es.iessaladillo.pedrojoya.pr168.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.iessaladillo.pedrojoya.pr168.R;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.ListaAlumnosFragment.OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr168.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends AppCompatActivity implements
        OnListaAlumnosFragmentListener, SiNoDialogListener {

    private static final String TAG_LISTA_FRAGMENT = "tag_lista_fragment";
    private static final String TAG_FRG_DIALOGO = "tag_frg_dialogo";
    private static final int RC_AGREGAR = 1;
    private static final int RC_EDITAR = 2;

    private FloatingActionButton btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initVistas();
        cargarFragmento();
    }

    // Configura la toolbar.
    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        findViewById(R.id.btnAgregar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAgregarAlumno();
            }
        });
        btnAgregar = (FloatingActionButton) findViewById(R.id.btnAgregar);
    }

    // Carga el fragmento de la lista.
    private void cargarFragmento() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_LISTA_FRAGMENT) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContenido, new ListaAlumnosFragment(), TAG_LISTA_FRAGMENT)
                    .commit();
        }
    }

    // Muestra la actividad de alumno para agregar.
    @Override
    public void onAgregarAlumno() {
        AlumnoActivity.startForResult(this, RC_AGREGAR);
    }

    // Muestra la actividad de alumno para editar. Recibe el id del alumno.
    @Override
    public void onEditarAlumno(long id) {
        AlumnoActivity.startForResult(this, id, RC_EDITAR);
    }

    // Muestra el fragmento de diálogo de confirmación de eliminación.
    @Override
    public void onConfirmarEliminarAlumnos() {
        SiNoDialogFragment frgDialogo = new SiNoDialogFragment();
        frgDialogo.show(getSupportFragmentManager(), TAG_FRG_DIALOGO);
    }

    // Se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        // Se llama al método del fragmento para eliminar los alumnos
        // seleccionados.
        ListaAlumnosFragment frg = (ListaAlumnosFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_LISTA_FRAGMENT);
        if (frg != null) {
            frg.eliminarAlumnosSeleccionados();
        }
    }

    // No se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // Método requerido por la interfaz SiNoDialogListener.
    }

    @Override
    public void onShowFAB() {
        btnAgregar.show();
    }

    @Override
    public void onHideFAB() {
        btnAgregar.hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ListaAlumnosFragment frg = (ListaAlumnosFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_LISTA_FRAGMENT);
            if (frg != null) {
                frg.cargarAlumnos();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
