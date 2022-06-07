package com.example.aula9;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etNomeCurso;
    private EditText etIdCurso;
    private Button btCadastrar;
    private Button btEditar;
    private ListView lvListaCurso;

    private CursoService requestCurso;
    private CursoResponse cursoResponse;
    private List<String> listaNomeCursos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaNomeCursos = new ArrayList<>();

        etNomeCurso = findViewById(R.id.etNomeCurso);
        etIdCurso = findViewById(R.id.etIdCurso);
        btCadastrar = findViewById(R.id.btCadastrar);
        btEditar = findViewById(R.id.btEditar);
        lvListaCurso = findViewById(R.id.lvListaDepts);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaNomeCursos);
        lvListaCurso.setAdapter(adapter);

        requestCurso = new RetrofitConfig()
                .criarService();

        CursoPost sendCursoBody = new CursoPost();

        btCadastrar.setOnClickListener(view -> {
            sendCursoBody.setName(etNomeCurso.getText().toString());
            executarRequestPost(sendCursoBody);
        });

        btEditar.setOnClickListener(view -> {
            sendCursoBody.setName(etNomeCurso.getText().toString());
            executarRequestPut(sendCursoBody, cursoResponse.getId());
        });

    }

    public void executarRequestGetAll(View view) {

        requestCurso.createRequestGetAll().enqueue(new Callback<List<CursoResponse>>() {
            @Override
            public void onResponse(Call<List<CursoResponse>> call, Response<List<CursoResponse>> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();

                List<CursoResponse> cursoLista = response.body();

                for (CursoResponse curso : cursoLista) {
                    Log.i(">>> Resultado", curso.getId() + " " + curso.getName());

                    listaNomeCursos.add(curso.getName());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CursoResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void executarRequestDelete(View view) {
        String idDigitado = etIdCurso.getText().toString();
        int id = Integer.parseInt(idDigitado);

        requestCurso.createRequestDelete(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void executarRequestPost(CursoPost cursoPost) {
        requestCurso.createRequestPost(cursoPost).enqueue(new Callback<CursoResponse>() {
            @Override
            public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                cursoResponse = response.body();
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CursoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha na request", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void executarRequestPut(CursoPost cursoPut, int id) {
        requestCurso.createRequestPut(cursoPut, id).enqueue(new Callback<CursoResponse>() {
            @Override
            public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CursoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha na request", Toast.LENGTH_LONG).show();
            }
        });
    }
}