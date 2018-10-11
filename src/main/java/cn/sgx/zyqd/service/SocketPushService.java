package cn.sgx.zyqd.service;

import cn.sgx.zyqd.bo.DataVo;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SocketPushService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value(value = "${socket.ip}")
    private String ip;
    @Value(value = "${socket.port}")
    private Integer port;
    @Value(value = "${socket.xml.data}")
    private String dataXml;
    @Value(value = "${socket.xml.pic}")
    private String picXml;
    @Value(value = "${socket.stationID}")
    private String stationID;


    private Socket socket;

    public boolean push(List<StationDataVo> vos) throws Exception {

        //创建客户端Socket，指定服务器地址和端口
        if (null == socket || socket.isClosed()) {
            socket = new Socket(ip, port);
        }
        for (int i = 0; i < vos.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            DataVo dataVo = new DataVo();
            BeanUtils.copyProperties(vos.get(i), dataVo);
            dataVo.init(stationID,vos.get(i).getILane(),vos.get(i).getITotalWeight());
            map.put("data", dataVo);
            StringBuffer buffer = FreemarkUtil.generateXmlByTemplate(map, dataXml);
            logger.info("写入的数据：{}", buffer);
            socket.getOutputStream().write(buffer.toString().getBytes());

            //获取输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                logger.info("我是客户端，服务器说{}" + info);
            }
            logger.info("我是客户端，服务器说{}");
            //关闭资源
            br.close();
            is.close();
        }
//        socket.close();
        return true;

    }


}
