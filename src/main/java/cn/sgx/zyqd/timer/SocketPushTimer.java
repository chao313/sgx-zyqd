package cn.sgx.zyqd.timer;

import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import cn.sgx.zyqd.service.HttpService;
import cn.sgx.zyqd.service.PicDataService;
import cn.sgx.zyqd.service.ScanPicService;
import cn.sgx.zyqd.service.SocketPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class SocketPushTimer {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SocketPushService socketPushService;

    @Autowired
    private HttpService httpService;
    @Value(value = "${http.host}")
    private String host;
    @Value(value = "${http.limitWeight}")
    private Integer limitWeight;

    @Value(value = "${http.limit}")
    private Integer limit;


    List<String> dataSource = new ArrayList<>();

    public SocketPushTimer() {
        dataSource.add("dataSource196");
        dataSource.add("dataSource195");
        dataSource.add("dataSource197");
        dataSource.add("dataSource198");
    }

    //目前是每分钟执行一次
    @Scheduled(cron = "${timer.push}")
    public void task() {
        try {
            dataSource.stream().forEach(dataSource -> {
                try {
                    logger.info(" {} : [Timer Task ] [ Socket Push ] ", dataSource);
                    List<PicAndStationVo> picAndStationVos =
                            httpService.getPicAndStationVoLimit(host, limitWeight, limit, dataSource);
                    List<Integer> pushResults = null;
                    pushResults = socketPushService.push(picAndStationVos);
                    pushResults.stream().forEach(id -> {
                        try {
                            httpService.updatePicDataById(host, id, dataSource);
                        } catch (IOException e) {
                            logger.error(dataSource + ": [Timer Task ] FAIL  updatePicDataById ", e.getMessage(), e);
                        }
                    });
                    logger.info(dataSource + " : [Timer Task ] SocketPush : ip:port is {}, push vos is {} , success vo id is {}",
                            socketPushService.ip + " : " + socketPushService.port, picAndStationVos, pushResults);
                } catch (Exception e) {
                    logger.error(dataSource + " : [Timer Task ] SocketPush : ip:port is {}",
                            socketPushService.ip + socketPushService.port, e);
                }
            });
        } catch (Exception e) {
            logger.error("[Timer Task ] SocketPush : ip:port is {}",
                    socketPushService.ip + socketPushService.port, e);
        }
    }
}