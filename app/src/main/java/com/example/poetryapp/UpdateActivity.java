package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.APi.ApiClient;
import com.example.poetryapp.APi.ApiInterface;
import com.example.poetryapp.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetryDataUpdate;
    AppCompatButton updateBtn;
    int poetryId;
    String poetryDataString;

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);



        intialization();
        setupactionbar();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p_data = poetryDataUpdate.getText().toString();

                if (p_data.equals("")){
                    poetryDataUpdate.setError("find is empty");

                }else {
                    callapi(p_data,poetryId+"");

                }

            }
        });



    }

    private  void intialization(){
        toolbar = findViewById(R.id.update_poetry_toolbar);
        poetryDataUpdate = findViewById(R.id.update_poetryData);
        updateBtn = findViewById(R.id.updateDataBtn);

        poetryId = getIntent().getIntExtra("p_id",0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetryDataUpdate.setText(poetryDataString);

        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);


    }

    private void setupactionbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void callapi(String pData,String pId){
        apiInterface.updatepoetry(pData,pId).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if (response.body().getStatus().equals("1")){
                        Toast.makeText(UpdateActivity.this, "Data Update Success", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(UpdateActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                        Log.e("Error","Aikhan a");
                    }
                }catch (Exception e){
                    Toast.makeText(UpdateActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

                Toast.makeText(UpdateActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}