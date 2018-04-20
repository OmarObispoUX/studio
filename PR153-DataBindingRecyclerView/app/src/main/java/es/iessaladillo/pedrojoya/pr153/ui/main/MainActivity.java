package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mooveit.library.Fakeit;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr153.BR;
import es.iessaladillo.pedrojoya.pr153.R;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr153.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityListAdapterBase adapter;
    private MainActivityViewModel viewModel;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(this);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        random = new Random();
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupRecyclerView() {
        binding.lstStudents.setHasFixedSize(true);
        // OJO. NO se puede hacer con Data Binding porque entonces no se recupera correctamente
        // el estado de la lista, ya que el LayoutManager sería asignado por el Data Binding más
        // tarde que la restauración del estado.
        binding.lstStudents.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new MainActivityListAdapterBase(BR.item);
        binding.lstStudents.setAdapter(adapter);
        viewModel.getStudents().observe(this, students -> adapter.submitList(students));
    }

    @SuppressWarnings("unused")
    public void onItemClick(View view, Object item, int position) {
        Snackbar.make(binding.lstStudents,
                getString(R.string.main_activity_student_clicked, ((Student) item).getName()),
                Snackbar.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    public void onItemLongClick(View view, Object item, int position) {
        if (viewModel.deleteStudent((Student) item) > 0) {
            Snackbar.make(binding.lstStudents, getString(R.string.main_activity_student_deleted, ((Student) item).getName
                            ()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onSwipedRight(RecyclerView.ViewHolder viewHolder, int direction, Object item,
            int position) {
        if (viewModel.deleteStudent((Student) item) > 0) {
            Snackbar.make(binding.lstStudents, getString(R.string.main_activity_student_deleted, ((Student) item).getName
                            ()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onSwipedLeft(RecyclerView.ViewHolder viewHolder, int direction, Object item,
            int position) {
        if (viewModel.deleteStudent((Student) item) > 0) {
            Snackbar.make(binding.lstStudents, getString(R.string.main_activity_student_archived, ((Student) item)
                            .getName()),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("unused")
    public void onFabClick(View view) {
        viewModel.insertStudent(createFakeStudent());
    }

    private Student createFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (random.nextInt(10) + 1) + "/");
    }

}
