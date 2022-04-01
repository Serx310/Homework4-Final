package com.nagel.lab4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallViewHolder> {
    private List<String> numbers;
    private LayoutInflater layoutInflater;
    public CallAdapter(Context context, List<String> numbers) {
        this.layoutInflater = LayoutInflater.from(context);
        this.numbers = numbers;
    }
    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_layout,parent,false);
        return new CallViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        String number = numbers.get(position);
        TextView txtnumber = holder.itemView.findViewById(R.id.txtNumber);
        TextView txtTime = holder.itemView.findViewById(R.id.txtTime);
        String[] array = number.split(",",2);
        Log.i("TAG", array[0] + " : " + array[1]);

        txtnumber.setText(array[0]);
        txtTime.setText(array[1]);

        txtnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: "+txtnumber.getText().toString()));
                view.getContext().startActivity(callIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return numbers.size();
    }




}
