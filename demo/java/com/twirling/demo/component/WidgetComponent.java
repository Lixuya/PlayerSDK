package com.twirling.demo.component;

/**
 * Target：component
 */
public class WidgetComponent implements android.databinding.DataBindingComponent{
	private DataBindingAdapter dbAdapter = new DataBindingAdapter();

	public DataBindingAdapter getDbAdapter() {
		return dbAdapter;
	}
}