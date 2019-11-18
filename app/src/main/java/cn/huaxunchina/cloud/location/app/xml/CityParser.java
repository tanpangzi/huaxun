package cn.huaxunchina.cloud.location.app.xml;

import java.io.InputStream;
import java.util.List;

public interface CityParser {
	
	 /** 
     * 解析输入流 得到citys对象集合 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<CityModel> parse(InputStream is) throws Exception;  
      
     /**
      * 序列化citys对象集合 得到XML形式的字符串 
     *TODO(描述) 
     * @param citys
     * @return
     * @throws Exception
    */
    public String serialize(List<CityModel> citys) throws Exception; 
}
