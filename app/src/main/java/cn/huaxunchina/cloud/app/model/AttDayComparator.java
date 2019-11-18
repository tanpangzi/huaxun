package cn.huaxunchina.cloud.app.model;

import java.util.Comparator;

import cn.huaxunchina.cloud.app.model.AttendanceInfo.AttDay;
/**
 * 考勤排序
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月16日 下午4:15:20 
 *
 */
public class AttDayComparator implements Comparator<AttDay> {

	@Override
	public int compare(AttDay arg0, AttDay arg1) {
		// TODO Auto-generated method stub
		if(arg0.getAttendanceType()<arg1.getAttendanceType()){  
            return -1;  
        }  
        if(arg0.getAttendanceType()>arg1.getAttendanceType()){  
            return 1;  
        }  
        return 0;
        
	}

}
