package cn.huaxunchina.cloud.app.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.huaxunchina.cloud.app.activity.fragment.ClassHomeWrokFragment;
import cn.huaxunchina.cloud.app.activity.fragment.ClassNoticeFragment;
import cn.huaxunchina.cloud.app.activity.fragment.SchoolNoticeFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
	private SchoolNoticeFragment schoolNoticeFragment;
	private ClassNoticeFragment classNoticeFragment;
	private ClassHomeWrokFragment classHomeWorkFragment;
	private Fragment fragment = null;
	private String[] titles;
	private String flag;

	public MyPagerAdapter(FragmentManager fm, String[] titles, String flag) {
		super(fm);
		this.titles = titles;
		this.flag = flag;

	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			if (flag.equals("notice")) {
				if (schoolNoticeFragment == null) {
					schoolNoticeFragment = new SchoolNoticeFragment();
				}
				fragment = schoolNoticeFragment;
			} else if (flag.equals("homeWrok")) {
				if (classHomeWorkFragment == null) {
					classHomeWorkFragment = new ClassHomeWrokFragment();
				}
				fragment = classHomeWorkFragment;
			}
			return fragment;
		case 1:
			if (classNoticeFragment == null) {
				classNoticeFragment = new ClassNoticeFragment();
			}
			return classNoticeFragment;
		case 2:
//			if (educationNoticeFragment == null) {
//				educationNoticeFragment = new EducationNoticeFragment();
//			}
//			return educationNoticeFragment;
		default:
			return null;
		}
	}
}
