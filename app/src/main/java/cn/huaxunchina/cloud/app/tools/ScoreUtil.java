package cn.huaxunchina.cloud.app.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.huaxunchina.cloud.app.model.FiveScoredsDatas;
import cn.huaxunchina.cloud.app.model.FiveScoredsInfos;
import cn.huaxunchina.cloud.app.model.FiveScoresInfo;
/**
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月23日 下午4:01:14 
 *
 */
public class ScoreUtil {
	
	static String courseNameLit[] = {"语文","数学","英语","物理","化学","历史","政治","生物","地理","美术","体育","音乐"};
	
	/**
	 * TODO(描述) 
	  * @param listdata
	  * @return
	 */
    public static Map<String,List<FiveScoresInfo>> getFiveScoresInfo(List<FiveScoresInfo> listdata){
    	Map<String,List<FiveScoresInfo>> map = new HashMap<String, List<FiveScoresInfo>>();
    	int data_size = listdata.size();
    	for(int i=0;i<data_size;i++){
    		FiveScoresInfo info = listdata.get(i);
    		String courseName = getCourseName(info.getCourseName());
    		boolean isexist =  isExist(map,courseName);
    		if(isexist){//存在的话添加到已有的集合里面去
    			List<FiveScoresInfo> new_data = map.get(courseName);
    			new_data.add(info);
    			map.put(courseName, new_data);
    		}else{//不存在重新保存新集合
    			List<FiveScoresInfo> new_data = new ArrayList<FiveScoresInfo>();
    			new_data.add(info);
    			map.put(courseName, new_data);
    		}
    	}
    	return map;
    }
    
    
    /**
     * TODO(描述) 
      * @param data
      * @return
     */
    public static Map<String,FiveScoredsInfos> getFiveScoresInfo(FiveScoredsDatas data){
    	Map<String, FiveScoredsInfos> map = new HashMap<String, FiveScoredsInfos>();
    	if(data!=null){
    		List<FiveScoredsInfos> scorelist = data.getData();
    		if(scorelist!=null){
    		int size = scorelist.size();
    		for(int i=0;i<size;i++){
    			FiveScoredsInfos info = scorelist.get(i);
    			String courseName = info.getCourseName();
    			map.put(courseName, info);
    		}
    		}
    	}
    	return map;
    }
    
    public static List<String> getAllkey(Map<String,FiveScoredsInfos> map){
    	List<String> list = new ArrayList<String>();
    	Set<Entry<String, FiveScoredsInfos>> set = map.entrySet();
    	Iterator<Entry<String, FiveScoredsInfos>> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next().getKey();
    		list.add(key);
		}
    	Collections.reverse(list);
    	return list;
    }
    
    
    /**
     * TODO(描述) 
      * @param map
      * @param courseName
      * @return
     */
    private static boolean isExist(Map<String,List<FiveScoresInfo>> map,String courseName){
    	boolean isexist = false;
    	Set<Entry<String, List<FiveScoresInfo>>> set = map.entrySet();
    	Iterator<Entry<String, List<FiveScoresInfo>>> iterator = set.iterator();
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
    
    public static List<String> getAllkeys(Map<String,List<FiveScoresInfo>> map){
    	List<String> list = new ArrayList<String>();
    	Set<Entry<String, List<FiveScoresInfo>>> set = map.entrySet();
    	Iterator<Entry<String, List<FiveScoresInfo>>> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next().getKey();
    		list.add(key);
		}
    	Collections.reverse(list);
    	return list;
    }

}
