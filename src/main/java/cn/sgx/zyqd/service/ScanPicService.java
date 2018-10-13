package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import cn.sgx.zyqd.util.DateUtils;
import cn.sgx.zyqd.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ScanPicService {
    @Value(value = "${pic.path}")
    private String picPath;
    @Value(value = "${pic.type}")
    private String suffix;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PicDataService picDataService;

    /**
     * 注意：只扫描当天的数据
     *
     * @return
     * @throws Exception
     */
    public List<PicDataVo> scanDirAndGetTodayPicNotInSql() throws Exception {
        File file = new File(picPath);
        List<PicDataVo> picDataVos = new ArrayList<PicDataVo>();
        if (!file.isDirectory()) {
            logger.error("[ ScanPicService ] the path is not dir :{}", picPath);
            String message = MessageFormat.format("the path is not dir {0}", picPath);
            throw new Exception(message);
        } else {
            String todayTime = DateUtils.getFormatDateTime(new Date(), DateUtils.DATE_SHORT_FORMAT);
            String[] fileNames = file.list();
            fileNames = this.filterfileNames(fileNames);//过滤已经当天已经存在的图片
            for (int i = 0; i < fileNames.length; i++) {
                //以当天日期开头，并且以.jpg结尾
                if (fileNames[i].startsWith(todayTime) && fileNames[i].endsWith(suffix)) {
                    File picFile = new File(picPath + fileNames[i]);
                    FileInputStream fileInputStream = new FileInputStream(picFile);
                    byte[] picBIn = FileUtil.toByteArray(fileInputStream);
                    fileInputStream.close();
                    //数据注入
                    PicDataVo vo = new PicDataVo();
                    vo.setPicBin(picBIn);
                    vo.setPicName(fileNames[i]);
                    vo.setLastModified(picFile.lastModified());
                    vo.init();
                    picDataVos.add(vo);

                    logger.info("[ ScanPicService ] the pic is dealing :{} ,the num is :{} , total:{}", fileNames[i], i , fileNames.length);

                }
            }

        }
        return picDataVos;
    }


    public String getPicPath() {
        return picPath;
    }

    /**
     * filter 数据
     */
    private String[] filterfileNames(String[] fileNames) {
        List<String> fileNameList = Arrays.asList(fileNames);
        List<String> todayPicNameList = picDataService.getTodayPicNames();
        Collection exists= new ArrayList<String>(fileNameList);
        exists.removeAll(todayPicNameList);
        String[] result = new String[exists.size()];
        exists.toArray(result);
        return result;

    }

}
