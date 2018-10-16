package cn.sgx.zyqd.service;

import cn.sgx.zyqd.bo.DataVo;
import cn.sgx.zyqd.bo.PicVo;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.util.FreemarkUtil;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            map = new HashMap<String, Object>();
            DataVo dataVo = new DataVo();
            BeanUtils.copyProperties(vo, dataVo);
            dataVo.init(stationID, vo.getILane(), vo.getITotalWeight());
            map.put("data", dataVo);
            buffer = FreemarkUtil.generateXmlByTemplate(map, dataXml);
            String head = "SHCS" + dataVo.getNo() + new DecimalFormat("00000000").format(buffer.length() + 2);
            String bufferStr = head + buffer.toString();
            logger.info("the date to write ：{}", bufferStr);
            socket.getOutputStream().write(bufferStr.getBytes());
            socket.getOutputStream().flush();
            //获取输入流
            socket.shutdownOutput();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
            }
            logger.info("l am client , server info is {}", info);
            logger.info("push station end ：{}", vo.getSzVehicleLicense());
            return null == info || info.contains("Ret=\"1\"");
        } finally {
            if (null != socket && socket.isClosed() == false) {
                //关闭资源
                socket.close();
            }

        }
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
            socket = new Socket(ip, port, null, localPort);
        }
        logger.info("push station start ：{}", vo.getSzVehicleLicense());
        PicVo picVo = new PicVo();
        picVo.setByteLength(String.valueOf(vo.getPicBin().length()));
        picVo.setPicBin(vo.getPicBin());
        map.put("pic", picVo);
        buffer = FreemarkUtil.generateXmlByTemplate(map, picXml);
        logger.info("the date to write ：{}", buffer);
        socket.getOutputStream().write(buffer.toString().getBytes());
        socket.shutdownOutput();
        //获取输入流
        is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        info = null;
        while ((info = br.readLine()) != null) {
            logger.info("l am client , server info is {}" + info);
        }
        logger.info("push station end ：{}", vo.getSzVehicleLicense());
        if (socket.isClosed() == false) {
            //关闭资源
            br.close();
            is.close();
            socket.close();
        }
        return info.contains("Ret=\"1\"");
    }

}
