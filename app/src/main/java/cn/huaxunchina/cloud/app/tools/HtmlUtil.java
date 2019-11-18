package cn.huaxunchina.cloud.app.tools;

public class HtmlUtil {
	
	public static String getHtml(String content){
		String html = "<!doctype html><html><head><meta charset=\"utf-8\"> <meta name=\"viewport\" content=\"width=device-width,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">"
				+ "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><meta name=\"format-detection\" content=\"telephone=no\">"
				+ " <title></title><style type=\"text/css\">body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,"
				+ "legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,"
				+ "menu,nav,section {margin:0;padding:0;}input,select,textarea{font-size:100%;}h1,h2,h3,h4,h5,h6 {font-size:100%;font-weight:500;}"
				+ "address,caption,cite,code,dfn,em,strong,th,var {font-style:normal;font-weight:500;}fieldset,img {border:0;}"
				+ "abbr,acronym{border:0;font-variant:normal;}table{border-collapse:collapse;border-spacing:0;}"
				+ "th{text-align:inherit;}"
				+ "body { font-family:Helvetica,STHeiti,Droid Sans Fallback; background-color:none !important; color:#333333;}"
				+ " div, dl, dt, dd, ul, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, input, textarea, blockquote, p { padding: 0; margin: 0 }"
				+ "ul { list-style: none }"
				+ " ul li { list-style-type: none }"
				+ "img { border: 0 }"
				+ "h1, h2, h3, h4, h5, h6 ,textarea{ font-size: inherit; font-weight: normal }"
				+ " address, cite, code, em, th, i { font-weight: normal; font-style: normal }"
				+ ".article{word-break:break-all;text-decoration:none;}"
				+ ".article p{text-align:justify;text-justify:inter-ideograph;}"
				+ ".article a{text-decoration:none}"
				+ ".article img{clear:both;display:block;margin:5px auto;max-width:100%;height:auto;font-size:0}"
				+ ".article,.article *{ background:none !important; font-size:16px !important;line-height:24px !important; color:#808080}"
				+ " .article p{margin-bottom:15px;}"
				+ "</style>"
				+ "</head><body><div id=\"content\" class=\"article\">"+content+"</div></body></html>";
		return html;
	}
	
	public static String getHtml(String title,String time,String source,String content){
		String html = "<!doctype html><html><head><meta charset=\"utf-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">"
				+ "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">"
				+ "<meta name=\"format-detection\" content=\"telephone=no\">"
				+ "<title></title>"
						+ "<style type=\"text/css\">"
						+ "body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section {margin:0;padding:0;}"
						+ "input,select,textarea{font-size:100%;}"
						+ "h1,h2,h3,h4,h5,h6 {font-size:100%;font-weight:500;}"
						+ "address,caption,cite,code,dfn,em,strong,th,var {font-style:normal;font-weight:500;}"
						+ "fieldset,img {border:0;}"
						+ "abbr,acronym{border:0;font-variant:normal;}"
						+ "table{border-collapse:collapse;border-spacing:0;}"
						+ "th{text-align:inherit;}"
						+ "body { font-family:Helvetica,STHeiti,Droid Sans Fallback;}"
						+ "div, dl, dt, dd, ul, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, input, textarea, blockquote, p { padding: 0; margin: 0 }"
						+ "ul { list-style: none }"
						+ "ul li { list-style-type: none }"
						+ "img { border: 0 }"
						+ "h1, h2, h3, h4, h5, h6 ,textarea{ font-size: inherit; font-weight: normal }"
						+ "address, cite, code, em, th, i { font-weight: normal; font-style: normal }"
						+ ".title{line-height:28px;font-size:18px; color:#333333;word-break:break-all;text-align:justify;text-justify:inter-ideograph;}"
						+ ".bar{padding:15px 0 10px;font-size:12px;color:#808080}"
						+ ".bar .fr{ float:right}"
						+ ".article{word-break:break-all;text-decoration:none;}"
						+ ".article p{text-align:justify;text-justify:inter-ideograph;}"
						+ ".article a{text-decoration:none}"
						+ ".article img{clear:both;display:block;margin:5px auto;max-width:100%;height:auto;font-size:0}"
						+ ".article,.article *{ background:none !important; font-size:16px !important;line-height:26px !important; color:#333}"//444
						+ ".article p{margin-bottom:18px;}"
						+ "</style></head>"
						+ "<body>"
						+ "<h1 class=\"title\">"
						+title+"</h1>"
						+ "<div class=\"bar\"><span class=\"fr\">"
						+source+ "</span><span>"
						+time+ "</span></div>"
						+ "<div id=\"content\" class=\"article\">"
						+content+ "</div></body><script>"
						+ "function  writeLine(string)"
						+ "{"
						+ "document.getElementById('content').innerHTML+= string;}"
						+ "</script></html>";
		return html;
	}

}
