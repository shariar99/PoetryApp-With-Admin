package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.APi.ApiClient;
import com.example.poetryapp.APi.ApiInterface;
import com.example.poetryapp.Response.DeleteResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText addPoetryData,addPoetName;
    AppCompatButton submitBtn;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        intialiazation();
        setupToolbar();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poetryDataString = addPoetryData.getText().toString();
                String poetNameString = addPoetName.getText().toString();


                if (poetryDataString.equals("")){
                    addPoetryData.setError("Field is empty");

                }else {
                    if(poetNameString.equals("")){
                        addPoetName.setError("Field is empty");

                    }else {
                        callapi(poetryDataString,poetNameString);
                        Toast.makeText(AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });


    }

    private void intialiazation(){
        toolbar = findViewById(R.id.add_poetry_toolbar);
        addPoetryData = findViewById(R.id.add_poetryData);
        addPoetName = findViewById(R.id.add_poetName);
        submitBtn = findViewById(R.id.submitDataBtn);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private  void callapi(String poetryData, String poetName ){

        apiInterface.addpoetry(poetryData,poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {
                    if (response.body().getStatus().equals("1")){

                    }else {
                        Toast.makeText(AddPoetry.this, "Not add success fully", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(AddPoetry.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

                Toast.makeText(AddPoetry.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}