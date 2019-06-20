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

/**
 * 定时扫描图片文件 -> 入库
 */
@Configuration
@EnableScheduling
public class ScanDirAndSavePicTimer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ScanPicService scanPicService;


    @Autowired
    private PicDataService picDataService;


    @Scheduled(cron = "${timer.scandir}")
    public void task() {
        try {
            List<PicDataVo> picDataVos =
                    scanPicService.scanDirAndGetTodayPicNotInSql();
            picDataService.savePicDataVos(picDataVos);
            logger.info("[Timer Task ] sacn dir:{},pic today not in sql is : {}", scanPicService.getPicPath(), picDataVos);
        } catch (Exception e) {
            logger.error("[Timer Task ] sacn dir fail:{}", scanPicService.getPicPath(), e);
        }
    }
}
