package cn.itcast.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * <p>Title: CustomDateConverter</p>
 * <p>Description: 自定义日期转换器</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-3-20下午5:37:59
 * @version 1.0
 */
public class CustomDateConverter implements Converter<String, Date> {
	private SimpleDateFormat sdf[] = {new SimpleDateFormat("yyyy-MM-dd"),new SimpleDateFormat("yyyyMMdd"),new SimpleDateFormat("yyyy/MM/dd"),new SimpleDateFormat("yyyy年MM月dd日")};
	@Override
	public Date convert(String source) {
		Date date= null;
		if(source == null || source.trim() == "") {
			return date;
		}
		for(int i=0;i<sdf.length;i++){
			try {
				date= (Date) sdf[i].parse(source);
				if(date != null){
					return date;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				continue;
			}
			
		}
		
		return date;
	}

}
