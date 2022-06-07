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


public class MenuDepartment extends AppCompatActivity {

    private EditText etNomeDept;
    private EditText etIdDept;
    private Button btCadastrarDept;
    private Button btEditarDept;
    private ListView lvListaDept;

    private DeptService requestDept;
    private DeptResponse DeptResponse;
    private List<String> listaNomeDepts;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_department);

        listaNomeDepts = new ArrayList<>();


        etNomeDept = findViewById(R.id.etNomeDept);
        etIdDept = findViewById(R.id.etIdDept);
        btCadastrarDept = findViewById(R.id.btCadastrarDept);
        btEditarDept = findViewById(R.id.btEditarDept);
        lvListaDept = findViewById(R.id.lvListaDepts);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaNomeDepts);
        lvListaDept.setAdapter(adapter);

        requestDept = new RetrofitConfig()
                .criarServiceDept();

        DeptPost sendDeptBody = new DeptPost();

        btCadastrarDept.setOnClickListener(view -> {
            sendDeptBody.setName(etNomeDept.getText().toString());
            executarRequestPostDepts(sendDeptBody);
        });

        btEditarDept.setOnClickListener(view -> {
            sendDeptBody.setName(etNomeDept.getText().toString());
            executarRequestPutDepts(sendDeptBody, DeptResponse.getId());
        });
    }

    public void executarRequestGetAllDepts(View view) {

        requestDept.createRequestGetAllDepts().enqueue(new Callback<List<DeptResponse>>() {
            @Override
            public void onResponse(Call<List<DeptResponse>> call, Response<List<DeptResponse>> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();

                List<DeptResponse> deptLista = response.body();

                for (DeptResponse dept : deptLista) {
                    Log.i(">>> Resultado", dept.getId() + " " + dept.getName());

                    listaNomeDepts.add(dept.getName());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DeptResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void executarRequestDeleteDepts(View view) {
        String idDigitado = etIdDept.getText().toString();
        int id = Integer.parseInt(idDigitado);

        requestDept.createRequestDeleteDepts(id).enqueue(new Callback<Object>() {
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


    private void executarRequestPostDepts(DeptPost deptPost) {
        requestDept.createRequestPostDepts(deptPost).enqueue(new Callback<DeptResponse>() {
            @Override
            public void onResponse(Call<DeptResponse> call, Response<DeptResponse> response) {
                DeptResponse = response.body();
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DeptResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha na request", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void executarRequestPutDepts(DeptPost deptPut, int id) {
        requestDept.createRequestPutDepts(deptPut, id).enqueue(new Callback<DeptResponse>() {
            @Override
            public void onResponse(Call<DeptResponse> call, Response<DeptResponse> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<com.example.aula9.DeptResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha na request", Toast.LENGTH_LONG).show();

            }


        });
    }



}