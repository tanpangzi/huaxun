package cn.huaxunchina.cloud.location.app.model.res;

import java.util.Comparator;

import cn.huaxunchina.cloud.app.model.AttendanceInfo.AttDay;
/**
 * 考勤排序
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月16日 下午4:15:20 
 *
 */
public class LocusComparator implements Comparator<ResLocusModel> {

	@Override
	public int compare(ResLocusModel arg0, ResLocusModel arg1) {
		// TODO Auto-generated method stub
		if(arg0.getCreatedLong()<arg1.getCreatedLong()){  
            return -1;  
        }  
        if(arg0.getCreatedLong()>arg1.getCreatedLong()){  
            return 1;  
        }  
        return 0;
        
	}

}
