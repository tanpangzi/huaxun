package cn.huaxunchina.cloud.location.app.tools;

import cn.huaxunchina.cloud.app.application.ApplicationController;

public class Instruction {
	
	private static String[] QH = {"130","131","132","145","155","156","176","185","186","133","153","177","180","181","189"};//电信和联通
	
	public static String getYE(){
		String sim = ApplicationController.getSim();
		boolean b = false;
		int length = QH.length;
		sim = sim.substring(0, 2);
		for(int i=0;i<length;i++){
			if(sim.equals(QH[i])){
				b=true;
			}
		}
		
		String ZL = "";
		if(b){
			ZL="#"+ApplicationController.getPwd()+"#YE*101";
		}else{
			ZL = "#"+ApplicationController.getPwd()+"#YE*YE";
		}
		
		return ZL;
	}

	public static String getLocation(){
		return "#"+ApplicationController.getPwd()+"#apkdw*1";
	}
	/**
	 * TODO(描述) 
	  * @param time1 填写间隔时间
	  * @param time2 填写跟踪时间
	  * @return
	 */
	public static String getLocus(int time1,int time2){
		
		return " #"+ApplicationController.getPwd()+"#APKGZ*"+time1+"*"+time2;
	}
}
