package com.twirling.player.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.twirling.lib_cobb.util.FileUtil;
import com.twirling.player.Constants;
import com.twirling.player.R;
import com.twirling.player.activity.VRPlayerActivity;
import com.twirling.player.databinding.ItemDownloadBinding;
import com.twirling.player.model.OfflineModel;
import com.twirling.player.widget.BindingViewHolder;

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
		models.get(position).setPosition(position);
		holder.getBinding().setVariable(com.twirling.player.BR.item, models.get(position));
		holder.getBinding().setVariable(com.twirling.player.BR.presenter, new Presenter());
		holder.getBinding().executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return models.size();
	}

	public class Presenter {
		public void onIvDeleteClick(View view, final OfflineModel item) {
			// 删除本地文件
			new MaterialDialog.Builder(view.getContext())
					.onPositive(new MaterialDialog.SingleButtonCallback() {
						@Override
						public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
							FileUtil.Delete.deleteByPath(Constants.PATH_MOVIES + item.getName());
							//
							models.remove(item.getPosition());
							//
//											DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack<>(oldModels, models), true);
//											result.dispatchUpdatesTo(OffineAdapter.this);
//											setModels(models);
//											notifyItemRemoved(position);
							notifyDataSetChanged();
						}
					})
					.theme(Theme.LIGHT)
					.title(R.string.title)
					.positiveText(R.string.agree)
					.negativeText(R.string.disagree)
					.content(String.format(view.getContext().getResources().getString(R.string.delete), item.getName()))
//					.icon(WidgetIcon.getTrashIcon(view.getContext()))
					.show();
			notifyDataSetChanged();
		}

		public void onCvCardClick(View view, final OfflineModel item) {
			Intent intent = new Intent();
			String uri = Constants.PATH_MOVIES + item.getName();
			intent.putExtra("VideoItem", uri);
			intent.putExtra("stereo", Constants.stereo);
			intent.putExtra("asset", item.isAsset());
			intent.setClass(view.getContext(), VRPlayerActivity.class);
			view.getContext().startActivity(intent);
		}
	}
}