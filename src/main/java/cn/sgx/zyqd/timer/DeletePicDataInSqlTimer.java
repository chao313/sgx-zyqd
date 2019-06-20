package cn.sgx.zyqd.timer;

import cn.sgx.zyqd.service.PicDataService;
import cn.sgx.zyqd.service.ScanPicService;
import cn.sgx.zyqd.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * 定时删除数据库中图片数据
 */
@Configuration
@EnableScheduling
public class DeletePicDataInSqlTimer {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ScanPicService scanPicService;


    @Autowired
    private PicDataService picDataService;

    @Value(value = "${pic.del.day}")
    private Integer day;


    @Scheduled(cron = "${timer.delPicInSql}")
    public void task() {
        try {
            String dayYyyyMMdd = DateUtils.getFormatDateTime(
                    DateUtils.addDays(new Date(), -day), DateUtils.DATE_SHORT_FORMAT);
            picDataService.deleteBeforeyyyyMMddDay(dayYyyyMMdd);
            logger.info("[Timer Task ] sacn dir:{} delete before {} day pic data in sql ",
                    scanPicService.getPicPath(), scanPicService.getDay());
        } catch (Exception e) {
            logger.error("[Timer Task ] sacn dir fail:{}", scanPicService.getPicPath(), e);
        }
    }
}
