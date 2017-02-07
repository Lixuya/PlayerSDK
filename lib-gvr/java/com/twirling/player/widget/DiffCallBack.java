package com.twirling.player.widget;

import android.support.v7.util.DiffUtil;

import com.twirling.player.model.OfflineModel;

import java.util.List;

/**
 * Target: 判断新旧Item是否相等
 */
public class DiffCallBack<T extends OfflineModel> extends DiffUtil.Callback {
	private List<T> mOldDatas, mNewDatas;//看名字

	public DiffCallBack(List<T> mOldDatas, List<T> mNewDatas) {
		this.mOldDatas = mOldDatas;
		this.mNewDatas = mNewDatas;
	}

	@Override
	public int getOldListSize() {
		return mOldDatas != null ? mOldDatas.size() : 0;
	}

	@Override
	public int getNewListSize() {
		return mNewDatas != null ? mNewDatas.size() : 0;
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		return mOldDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName());
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		T beanOld = mOldDatas.get(oldItemPosition);
		T beanNew = mNewDatas.get(newItemPosition);
		//如果有内容不同，就返回false
		if (!beanOld.equals(beanNew)) {
			return false;
		}
		//默认两个data内容是相同的
		return true;
	}
}