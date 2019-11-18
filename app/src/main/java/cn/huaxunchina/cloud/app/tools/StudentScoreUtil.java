package cn.huaxunchina.cloud.app.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.huaxunchina.cloud.app.model.StudentScore;

/**
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月23日 下午4:01:14 
 *
 */
public class StudentScoreUtil {
	
	static String courseNameLit[] = {"语文","数学","英语","物理","化学","历史","政治","生物","地理","美术","体育","音乐"};
	
	/**
	 * TODO(描述) 
	  * @param listdata
	  * @return
	 */
    public static Map<String,List<StudentScore>> getScoresMap(List<StudentScore> listdata){
    	Map<String,List<StudentScore>> map = new HashMap<String, List<StudentScore>>();
    	int data_size = listdata.size();
    	for(int i=0;i<data_size;i++){
    		StudentScore info = listdata.get(i);
    		String courseName = getCourseName(info.getCourseName());
    		boolean isexist =  isExist(map,courseName);
    		if(isexist){//存在的话添加到已有的集合里面去
    			List<StudentScore> new_data = map.get(courseName);
    			new_data.add(info);
    			map.put(courseName, new_data);
    		}else{//不存在重新保存新集合
    			List<StudentScore> new_data = new ArrayList<StudentScore>();
    			new_data.add(info);
    			map.put(courseName, new_data);
    		}
    	}
    	return map;
    }
    
    /**
     * TODO(描述) 
      * @param map
      * @param courseName
      * @return
     */
    private static boolean isExist(Map<String,List<StudentScore>> map,String courseName){
    	boolean isexist = false;
    	Set<Entry<String, List<StudentScore>>> set = map.entrySet();
    	Iterator<Entry<String, List<StudentScore>>> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next().getKey();
    		if(key.equals(courseName)){
    			isexist=true;
    		}
		}
    	return isexist;
    }
    
    /**
      * @param courseName
      * @return
     */
    private static String getCourseName(String courseName){
    	String str = "";
    	int length = courseNameLit.length;
    	boolean b = false;
    	for(int i=0;i<length;i++){
    		if(courseNameLit[i].equals(courseName)){
    			str=courseName;
    			 b=true;
    		} 
    	}
    	if(!b){
    		str=courseName;
    	}
    	return str;
    }
    
    /**
     * TODO(描述) 返回所以的科目 
      * @param map
      * @return
     */
    public static List<String> getAllkey(Map<String,List<StudentScore>> map){
    	List<String> list = new ArrayList<String>();
    	Set<Entry<String, List<StudentScore>>> set = map.entrySet();
    	Iterator<Entry<String, List<StudentScore>>> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next().getKey();
    		list.add(key);
		}
    	//倒序
    	Collections.reverse(list);
    	return list;
    }

}
