package cn.sgx.zyqd.service;

import cn.sgx.zyqd.bo.DataVo;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.util.FreemarkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
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


    private Socket socket;

    public List<Integer> push(List<PicAndStationVo> vos) throws Exception {

        //创建客户端Socket，指定服务器地址和端口
        if (null == socket || socket.isClosed()) {
            socket = new Socket(ip, port, null, localPort);
        }
        for (int i = 0; i < vos.size(); i++) {

            boolean stationResult = false;

            logger.info("push station start ：{}", vos.get(i).getSzVehicleLicense());
            Map<String, Object> map = new HashMap<String, Object>();
            DataVo dataVo = new DataVo();
            BeanUtils.copyProperties(vos.get(i), dataVo);
            dataVo.init(stationID, vos.get(i).getILane(), vos.get(i).getITotalWeight());
            map.put("data", dataVo);
            StringBuffer buffer = FreemarkUtil.generateXmlByTemplate(map, dataXml);
            logger.info("the date to write ：{}", buffer);
            socket.getOutputStream().write(buffer.toString().getBytes());

            //获取输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                logger.info("l am client , server info is {}" + info);
                // if(){}
            }
            logger.info("push station end ：{}", vos.get(i).getSzVehicleLicense());


            // 下面开始传图片


            //关闭资源
            br.close();
            is.close();
        }
//        socket.close();
        return new ArrayList<>();

    }


}
