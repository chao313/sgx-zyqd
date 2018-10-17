package demo.spring.boot;


import cn.sgx.zyqd.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Arrays;

public class PicTest {
    private static Logger logger = LoggerFactory.getLogger(PicTest.class);

    //
//    public static void main(String[] args) throws IOException {
////客户端
////1、创建客户端Socket，指定服务器地址和端口
//        Socket socket = new Socket("106.14.225.153", 7712);
////        logger.info("status:",socket.);
////2、获取输出流，向服务器端发送信息
//        OutputStream os = socket.getOutputStream();//字节输出流
//        File file = ResourceUtils.getFile("classpath:data.xml");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] bytes = new byte[1185];
//        while (fileInputStream.read(bytes)!=-1) {
//            os.write(bytes);
//        }
//        socket.shutdownOutput();
////3、获取输入流，并读取服务器端的响应信息
//        InputStream is = socket.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String info = null;
//        while ((info = br.readLine()) !=null){
//            System.out.println("我是客户端，服务器说：" + info);
//        }
//        System.out.println("我是客户端，服务器说：");
////4、关闭资源
//        br.close();
//        is.close();
//        os.close();
//        socket.close();
//    }
//
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            //创建客户端Socket，指定服务器地址和端口
            socket = new Socket("106.14.225.153", 7712);
            logger.info("local port : {}", socket.getLocalPort());
            //获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();//字节输出流
            File file = ResourceUtils.getFile("classpath:picdemo.xml");
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = null;
            String fileContnet = "";
            while ((line = reader.readLine()) != null) {
                fileContnet += line;
            }

            String head = "SHCS" + "00000011" +
                    new DecimalFormat("00000000").format(fileContnet.getBytes("UTF-8").length);
            fileContnet = "<" + fileContnet.substring(fileContnet.indexOf("<") + 1);
            fileContnet = head.trim() + fileContnet;
            fileContnet = fileContnet.substring(0, fileContnet.lastIndexOf(">")) + ">";
            logger.warn("{}", fileContnet);
            //
            /**
             * 处理图片流
             */
            File picFile = ResourceUtils.getFile("classpath:20181012.jpg");
            FileInputStream picInput = new FileInputStream(picFile);
            byte[] picBIn = FileUtil.toByteArray(picInput);

            testBin(picBIn);

            logger.info("image bytes length:{}", picBIn.length);
            //
            String imageBin = PicTest.conver2HexStr(picBIn);
            logger.info("imageBin length:{}", imageBin.length());
            logger.info("imageBin bytes length:{}", imageBin.getBytes("UTF-8").length/8);

//            logger.warn("fileContnet+image {}",fileContnet);
            os.write(fileContnet.getBytes());
            os.write(picBIn);

            socket.shutdownOutput();
            //获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
            }
            System.out.println("我是客户端，服务器说：");
            //关闭资源
            br.close();
            is.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            socket.close();
        }
    }

    public static String conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2));
        }
        return result.toString();
    }


    public static void testBin(byte[] bytes) throws FileNotFoundException {
        File filePicNew = new File(PicTest.class.getResource("/").getPath() + "filePicNewTest.jpg");
        OutputStream outputStream = new FileOutputStream(filePicNew);
        Arrays.asList(bytes).stream().forEach(b -> {
            try {
                outputStream.write(b);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}