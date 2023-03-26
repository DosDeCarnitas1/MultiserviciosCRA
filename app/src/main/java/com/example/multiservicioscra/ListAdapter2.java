package com.example.multiservicioscra;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder> {
    private List<ListRefaccion> mData2;

    private LayoutInflater mInflater2;

    private Context context2;

    public ListAdapter2(List<ListRefaccion> itemList2, Context context2){
        this.mInflater2 = LayoutInflater.from(context2);
        this.context2 = context2;
        this.mData2 = itemList2;
    }

    @Override
    public int getItemCount(){return mData2.size();}

    @Override
    public ListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater2.inflate(R.layout.list_refaccion, null);
        return new ListAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter2.ViewHolder holder, final int position){
        holder.bindDatas(mData2.get(position));
    }

    public void setItems(List<ListRefaccion> items){mData2 = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewRefaccion;
        TextView nombreRefa, cantidadRefa;
        String imageName;

        ViewHolder(View itemView){
            super(itemView);
            nombreRefa = itemView.findViewById(R.id.nombreRefa);
            imageViewRefaccion = itemView.findViewById(R.id.imageViewRefaccion);
            cantidadRefa = itemView.findViewById(R.id.cantidadRefa);
        }

        void bindDatas(final ListRefaccion item){
            nombreRefa.setText(item.getNombre());
            cantidadRefa.setText(item.getCantidad());
            Picasso.with(this).load("http://127.0.0.1:8000/img/refacciones/"+imageName).placeholder(R.drawable.refaccionPlaceholder).into(imageViewRefaccion);
        }

    }

}
