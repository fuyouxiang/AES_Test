package AES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class XMLtest {
	
    public static String sendPost(String url, String param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();// 设置通用请求的属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");// 我加的一个头
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 我加的一个头
            conn.setRequestProperty("Dms-Access-Token", "XXXXXXXXXXXXXXXXXXXXXXX");
             
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    result += line;
                     
                }
            } catch (IOException e) {
                System.out.println("发送POST请求出现异常");
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return result;
    }

	
    public static void main(String[] args) throws IOException {
    	
    	String Url = "http://yonyougov.vip/";
    	//String portName = "signStampByNosAndSend";
    	String portName = "LoginServlet";
    	String signID = "TC1377498122";
    	String admDivCode = "420000";
    	String stYear ="2020";
    	String vtCode="5201";
    	String voucherMessage_header="";
    	String voucherMessage_content="";
    	String stamp="";
    	String srcOrgType="";
    	String decOrgType="";
    	
        //发送 POST请求
        String sr = XMLtest.sendPost("http://yonyougov.vip/LoginServlet", "[{\"userName\":\"付有翔\",\"password\":\"2\",\"set_year\":\"2020\"}]");
        System.out.println(sr);

    }
}
