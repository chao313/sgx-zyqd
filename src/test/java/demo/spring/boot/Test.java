package demo.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.Socket;

public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
//客户端
//1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("106.14.225.153", 7712);
//        logger.info("status:",socket.);
//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        File file = ResourceUtils.getFile("classpath:demo.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1185];
        while (fileInputStream.read(bytes)!=-1) {
            os.write(bytes);
        }
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while ((info = br.readLine()) !=null){
            System.out.println("我是客户端，服务器说：" + info);
        }
        System.out.println("我是客户端，服务器说：");
//4、关闭资源
        br.close();
        is.close();
        os.close();
        socket.close();
    }
}
