package cn.sgx.zyqd.timer;

import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import cn.sgx.zyqd.service.PicDataService;
import cn.sgx.zyqd.service.ScanPicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScanDirAndDelPicTimer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ScanPicService scanPicService;


    @Autowired
    private PicDataService picDataService;


    @Scheduled(cron = "${timer.delPic}")
    public void task() {
        try {
            scanPicService.delPic();
            logger.info("[Timer Task ] sacn dir:{} delete before {} day files ",
                    scanPicService.getPicPath(), scanPicService.getDay());
        } catch (Exception e) {
            logger.error("[Timer Task ] sacn dir fail:{}", scanPicService.getPicPath(), e);
        }
    }
}
