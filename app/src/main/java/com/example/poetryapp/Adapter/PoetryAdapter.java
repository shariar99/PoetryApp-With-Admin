package com.example.poetryapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.APi.ApiClient;
import com.example.poetryapp.APi.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.R;
import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.UpdateActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter  extends RecyclerView.Adapter<PoetryAdapter.ViewHolder>{

    Context context;
    List<PoetryModel> poetryModels;

    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit = ApiClient.getclient();
        apiInterface= retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.poetry_list_design,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.date_time.setText(poetryModels.get(position).getDate_time());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletepoetry(poetryModels.get(position).getId()+"",position);

            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView poetName, poetry,date_time;
        AppCompatButton updateBtn, deleteBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poetName = itemView.findViewById(R.id.poetName);
            poetry = itemView.findViewById(R.id.poetryData);
            date_time = itemView.findViewById(R.id.poetryDateTime);
            updateBtn= itemView.findViewById(R.id.updateBTN);
            deleteBtn = itemView.findViewById(R.id.deleteBTN);

        }
    }

    private void deletepoetry(String id , int pose){
        apiInterface.deletepoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if (response!= null){

                        if (response.body().getStatus().equals("1")){
                            poetryModels.remove(pose);
                            notifyDataSetChanged();
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
