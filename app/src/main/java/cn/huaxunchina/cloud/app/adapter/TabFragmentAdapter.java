package cn.huaxunchina.cloud.app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;



public class TabFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentlist = null;
	private List<String> titlelist = null;
	public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragmentlist, List<String> titlelist){
		super(fm);
		this.fragmentlist=fragmentlist;
		this.titlelist=titlelist;
	}
	public TabFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titlelist.get(position);
	}
	@Override
	public int getCount() {
		return fragmentlist.size();
	}
	@Override
	public Fragment getItem(int position) {
		return fragmentlist.get(position);
	}


}
