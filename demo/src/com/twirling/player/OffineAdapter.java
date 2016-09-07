package com.twirling.player;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twirling.player.activity.SimpleVrVideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 谢秋鹏 on 2016/5/26.
 */
public class OffineAdapter extends RecyclerView.Adapter<OffineAdapter.ViewHolder> {
    //
    private List<String> datas = new ArrayList<String>();

    public OffineAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public OffineAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_download, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OffineAdapter.ViewHolder holder, final int position) {
        Log.w("www", datas.size() + "");
        holder.tv_name.setText(datas.get(position));
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Constants.isDownload = false;
                holder.cv_card.setVisibility(View.GONE);
                Constants.deleteFile();
            }
        });
        //
        holder.cv_card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("uri", Constants.PAPH_DOWNLOAD + datas.get(position));
                intent.setClass(holder.itemView.getContext(), SimpleVrVideoActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.cv_card)
        CardView cv_card;
        @BindView(R.id.tv_name)
        TextView tv_name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}