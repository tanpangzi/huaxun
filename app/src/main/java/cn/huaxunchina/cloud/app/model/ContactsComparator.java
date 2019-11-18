package cn.huaxunchina.cloud.app.model;

import java.util.Comparator;

/**
 * 通讯录的排序
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月16日 下午5:15:59 
 *
 */
public class ContactsComparator implements Comparator<ContactsInfo>{

	@Override
	public int compare(ContactsInfo o1, ContactsInfo o2) {
		// TODO Auto-generated method stub
		if (o1.getSortLetters().equals("@")|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	
	}
		
		

}
