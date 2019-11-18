package cn.huaxunchina.cloud.app.model;

import java.util.Comparator;


public class MoreComparator implements Comparator<More> {

	
	@Override
	public int compare(More arg0, More arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId()<arg1.getId()){  
            return -1;  
        }  
        if(arg0.getId()>arg1.getId()){  
            return 1;  
        }  
        return 0;
        
	}

}
