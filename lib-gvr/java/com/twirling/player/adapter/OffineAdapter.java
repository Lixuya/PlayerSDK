package com.twirling.player.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twirling.lib_cobb.util.FileUtil;
import com.twirling.player.Constants;
import com.twirling.player.R;
import com.twirling.player.activity.VRPlayerActivity;
import com.twirling.player.databinding.ItemDownloadBinding;
import com.twirling.player.model.OfflineModel;
import com.twirling.player.widget.BindingViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Target: 适配本地列表单项
 */
public class OffineAdapter extends RecyclerView.Adapter<BindingViewHolder> {
	//
	private List<OfflineModel> models = new ArrayList<>();

	public OffineAdapter(List<OfflineModel> models) {
		this.models = models;
	}

	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ItemDownloadBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_download, parent, false);
		return new BindingViewHolder<>(binding);
	}

	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {
		holder.getBinding().setVariable(com.twirling.player.BR.presenter, new Presenter());
		holder.getBinding().setVariable(com.twirling.player.BR.item, models.get(position));
		holder.getBinding().executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return models.size();
	}

	public class Presenter {
		public void onIvDeleteClick(View view, OfflineModel item) {
			FileUtil.delete(new File(Constants.PATH_DOWNLOAD + item.getName()));
			models.remove(item.getPosition());
//			DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack<>(oldModels, models), true);
//			result.dispatchUpdatesTo(OffineAdapter.this);
//			setModels(models);
//			notifyItemRemoved(position);
			notifyDataSetChanged();
		}

		public void onCvCardClick(View view, String name) {
			Intent intent = new Intent();
			String uri = Constants.PATH_DOWNLOAD + name;
			if (name.endsWith("asset")) {
				uri = null;
			}
			intent.putExtra("VideoItem", uri);
			intent.putExtra("Is3D", Constants.IS3D);
			intent.setClass(view.getContext(), VRPlayerActivity.class);
			view.getContext().startActivity(intent);
		}
	}
}