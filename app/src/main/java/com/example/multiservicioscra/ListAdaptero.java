package com.example.multiservicioscra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdaptero extends RecyclerView.Adapter<ListAdaptero.ViewHolder>{
    private List<ListElemento> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdaptero(List<ListElemento> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() { return mData.size();}


    @Override
    public ListAdaptero.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdaptero.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ListAdaptero.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }


    public void setItems(List<ListElemento> items){mData = items;}

    //metodos para ver si jala lo del swipe-to-refresh
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView IconImage;
        TextView titulo, descripcion, estado;

        ViewHolder(View itemView){
            super(itemView);
            IconImage = itemView.findViewById(R.id.logo);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            estado = itemView.findViewById(R.id.status);
        }

        void bindData(final ListElemento item){
            titulo.setText(item.getTitulo());
            descripcion.setText(item.getDescripcion());
            estado.setText(item.getEstado());
        }
    }

}
