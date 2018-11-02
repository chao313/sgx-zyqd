package cn.sgx.zyqd.service;

import cn.sgx.zyqd.bo.DataVo;
import cn.sgx.zyqd.bo.PicVo;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.util.FreemarkUtil;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocketPushService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value(value = "${socket.ip}")
    public String ip;
    @Value(value = "${socket.port.server}")
    public Integer port;
    @Value(value = "${socket.xml.data}")
    private String dataXml;
    @Value(value = "${socket.xml.pic}")
    private String picXml;
    @Value(value = "${socket.stationID}")
    private String stationID;
    @Value(value = "${socket.port.local}")
    private Integer localPort;
    @Value(value = "${control.picpush}")
    private boolean picpush; //控制图片是否开启传输


    @Autowired
    private FreemarkUtil freemarkUtil;


    private Socket socket;

    public List<Integer> push(List<PicAndStationVo> vos) throws Exception {
        List<Integer> ids = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        vos.stream().forEach(vo -> {
            try {
                boolean dataResult = this.socketPushData(vo, map);
                boolean picResult = false;
                if (picpush == true) {
                    //如果开启图片传输，就传输
                    picResult = this.socketPushPic(vo, map);
                } else if (picpush == false) {
                    //如果不开启图片传输，就直接为true
                    picResult = true;
                }
                if (dataResult == true && picResult == true) {
                    ids.add(vo.getId());
                }
            } catch (Exception e) {
                logger.error("push ERROR vo ：{}", vo, e);
            }
        });
        return ids;
    }


    /**
     * 传data
     *
     * @param vo
     * @param map
     * @return
     * @throws Exception
     */
    public Boolean socketPushData(PicAndStationVo vo, Map map) throws Exception {
        try {
            if (null == socket || socket.isClosed()) {
                socket = new Socket(ip, port);
            }
            StringBuffer buffer;
            InputStream is = null;
            BufferedReader br = null;
            String info = null;
            logger.info("push station start ：{}", vo.getSzVehicleLicense());
            DataVo dataVo = new DataVo();
            BeanUtils.copyProperties(vo, dataVo);
            dataVo.init(stationID, vo.getILane(), vo.getITotalWeight());
            map.put("data", dataVo);
            buffer = freemarkUtil.generateXmlByTemplate(map, dataXml);
            String cleanXml = "<" + buffer.substring(buffer.indexOf("<") + 1);
            int num = cleanXml.getBytes("UTF-8").length;
            String head = "SHCS" + dataVo.getNo() + new DecimalFormat("00000000").format(num);

            String bufferStr = head.trim() + cleanXml.trim();
            logger.info("the date to write ：{}", bufferStr);
            socket.getOutputStream().write(bufferStr.getBytes("UTF-8"));
            socket.getOutputStream().flush();
            //获取输入流
            socket.shutdownOutput();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            info = br.readLine();
            logger.info("l am socketPushData , server info is {}", info);
            logger.info("push station end ：{}", vo.getSzVehicleLicense());
            return info.contains("Ret=\"1\"");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != socket && socket.isClosed() == false) {
                //关闭资源
                socket.close();
            }

        }
        return false;
    }


    /***
     * 传图片
     * @param vo
     * @param map
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public Boolean socketPushPic(PicAndStationVo vo, Map map) throws IOException, TemplateException {
        StringBuffer buffer;
        InputStream is = null;
        BufferedReader br = null;
        String info = null;
        if (null == socket || socket.isClosed()) {
            socket = new Socket(ip, port);
        }
        logger.info("push pic start ：{}", vo.getSzVehicleLicense());
        PicVo picVo = new PicVo();
        picVo.setByteLength(String.valueOf(vo.getPicBin().length));
        picVo.setPicBin(vo.getPicBin());
        map.put("pic", picVo);
        buffer = freemarkUtil.generateXmlByTemplate(map, picXml);
        String cleanXml = "<" + buffer.substring(buffer.indexOf("<") + 1); //去掉奇怪的字符
        cleanXml = buffer.substring(0, buffer.lastIndexOf(">")) + ">";
        int num = cleanXml.getBytes("UTF-8").length;//计算出xml体// 的长度
        String head = "SHCS" + ((DataVo) map.get("data")).getNo() + new DecimalFormat("00000000").format(num);
        String bufferStr = head.trim() + cleanXml.trim();
        logger.info("the date to write ：{}", bufferStr);
        socket.getOutputStream().write(bufferStr.getBytes("UTF-8"));//写入xml体
        socket.getOutputStream().write(picVo.getPicBin());//写入图片数据
        this.base64StringToImage(picVo.getPicBin());
        socket.shutdownOutput();
        //获取输入流
        is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        info = br.readLine();
        logger.info("l am socketPushPic , server info is {}" + info);
        logger.info("push pic end ：{}", vo.getSzVehicleLicense());
        if (socket.isClosed() == false) {
            //关闭资源
            br.close();
            is.close();
            socket.close();
        }
        return info.contains("Ret=\"1\"");
    }

    static int i = 1;

    static void base64StringToImage(byte[] bytes) {

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File(i++ + ".jpg");// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
