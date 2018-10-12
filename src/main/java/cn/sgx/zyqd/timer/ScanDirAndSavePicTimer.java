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
            logger.info("【定时任务】扫描文件夹并保存数据{},数据库当天不存在的数pic为：{}", scanPicService.getPicPath(), picDataVos);
        } catch (Exception e) {
            logger.error("定时任务】扫描文件夹失败:{}", scanPicService.getPicPath(), e);
        }
    }
}
