package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import cn.sgx.zyqd.util.DateUtils;
import cn.sgx.zyqd.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScanPicService {
    @Value(value = "${pic.path}")
    private String picPath;
    @Value(value = "${pic.type}")
    private String suffix;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<PicDataVo> scanDir() throws Exception {
        File file = new File(picPath);
        List<PicDataVo> picDataVos = new ArrayList<>();
        if (!file.isDirectory()) {
            logger.error("【ScanPicService】指定目录不是文件夹：{}", picPath);
            String message = MessageFormat.format("指定目录不是文件夹{0}", picPath);
            throw new Exception(message);
        } else {
            String todayTime = DateUtils.getFormatDateTime(new Date(), DateUtils.DATE_SHORT_FORMAT);
            String[] fileNames = file.list();
            for (int i = 0; i < fileNames.length; i++) {
                //以当天日期开头，并且以.jpg结尾
                if (fileNames[i].startsWith(todayTime) && fileNames[i].endsWith(suffix)) {
                    File picFile = new File(fileNames[i]);
                    FileInputStream fileInputStream = null;
                    fileInputStream = new FileInputStream(picPath + picFile);
                    byte[] picBIn = FileUtil.toByteArray(fileInputStream);
                    fileInputStream.close();
                    //数据注入
                    PicDataVo vo = new PicDataVo();
                    vo.setPicBin(picBIn);
                    vo.setPicName(fileNames[i]);
                    picDataVos.add(vo);

                    logger.info("【ScanPicService】正在处理的图片文件:{} total:{}", fileNames[i], fileNames.length);
                }
            }

        }
        return picDataVos;
    }


    public String getPicPath() {
        return picPath;
    }
}
