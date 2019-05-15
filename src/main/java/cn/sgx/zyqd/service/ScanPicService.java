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
    @Value(value = "${pic.del.hour}")
    private Integer hour;
    @Value(value = "${pic.del.day}")
    private Integer day;

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

            List<File> allFileNamesList = new ArrayList<>();
            File[] firstFileDirs = file.listFiles();
            for (File fileDir : firstFileDirs) {
                if (fileDir.isDirectory()) {
                    //如果是文件夹
                    File[] list = fileDir.listFiles();
                    allFileNamesList.addAll(Arrays.asList(list));
                }

            }
            /**
             *  临时注释
             */
            allFileNamesList = this.filterfileNames(allFileNamesList);//过滤已经当天已经存在的图片
            for (File fileVo : allFileNamesList) {
                //以当天日期开头，并且以.jpg结尾
                if (fileVo.getName().startsWith(todayTime) && fileVo.getName().endsWith(suffix)) {
                    FileInputStream fileInputStream = new FileInputStream(fileVo);
                    byte[] picBIn = FileUtil.toByteArray(fileInputStream);
                    fileInputStream.close();
                    //数据注入
                    PicDataVo vo = new PicDataVo();
                    vo.setPicBin(picBIn);
                    vo.setPicName(fileVo.getName());
                    vo.setLastModified(fileVo.lastModified());
                    vo.init();
                    picDataVos.add(vo);
                    logger.info("[ ScanPicService ] the pic is dealing :{} , total:{}", fileVo, allFileNamesList.size());

                }
            }
        }
        return picDataVos;
    }


    /**
     * filter 数据
     */
    private List<File> filterfileNames(List<File> files) {
        /**
         * 查询出数据库中存在的数据
         */
        List<String> todayPicNameList = picDataService.getTodayPicNames();
        List<File> targetFiles = new ArrayList<>();
        /**
         * 过滤出数据库中不存在的图片
         */
        for (File file : files) {
            if (!todayPicNameList.contains(file.getName())) {
                targetFiles.add(file);
            }
        }
        return targetFiles;
    }


    /**
     * 删除N天前的数据
     * v2-> 由于数据存储比较大，删除前一个小时的文件
     *
     * @throws Exception
     */
    public void delPic() throws Exception {
        File file = new File(picPath);
        List<PicDataVo> picDataVos = new ArrayList<PicDataVo>();
        if (!file.isDirectory()) {
            logger.error("[ ScanPicService ] the path is not dir :{}", picPath);
            String message = MessageFormat.format("the path is not dir {0}", picPath);
            throw new Exception(message);
        } else {

            List<File> allFileList = new ArrayList<>();
            File[] firstFileDirs = file.listFiles();
            for (File fileDir : firstFileDirs) {
                if (fileDir.isDirectory()) {
                    //如果是文件夹
                    File[] list = fileDir.listFiles();
                    allFileList.addAll(Arrays.asList(list));
                }

            }
            logger.info("allFileNamesList的size:{}", allFileList.size());
            for (File fileVo : allFileList) {
                Integer fileTimeMMDDHH = Integer.valueOf(DateUtils.getFormatDateTime(new Date(fileVo.lastModified()), DateUtils.SGX_ZYQD_FORMAT_MMDDHH));
                Integer nowTimeMMDDHH = Integer.valueOf(DateUtils.getFormatDateTime(new Date(), DateUtils.SGX_ZYQD_FORMAT_MMDDHH));
                if (nowTimeMMDDHH - fileTimeMMDDHH > hour) {
                    if (fileVo.getPath().endsWith(".jpg")
                            || fileVo.getPath().endsWith(".bin")
                            || fileVo.getPath().endsWith(".inf")
                            || fileVo.getPath().endsWith(".bmp")
                            || fileVo.getPath().endsWith(".txt"))
                        fileVo.delete();
                    logger.info("file删除成功:{}", fileVo.getPath());
                }
            }
        }


//
//        File file = new File(picPath);
//        if (!file.isDirectory()) {
//            logger.error("[ ScanPicService ] the path is not dir :{}", picPath);
//            String message = MessageFormat.format("the path is not dir {0}", picPath);
//            throw new Exception(message);
//        } else
//            String dayYyyyMMdd = DateUtils.getFormatDateTime(
//                    DateUtils.addDays(new Date(), -day), DateUtils.DATE_SHORT_FORMAT);
//            String[] fileNames = file.list();
//            for (int i = 0; i < fileNames.length; i++) {
//                String fileyyyMMdd = fileNames[i].substring(0, 7);//获取文件的年月日
//                if (fileyyyMMdd.compareTo(dayYyyyMMdd) < 0) {//such as 20121010 和 20181011
//                    boolean delete = new File(picPath + fileNames[i]).delete();
//                    if (true == delete) {
//                        logger.info("[ ScanPicService ] delete file SUCCESS : {} ", fileNames[i]);
//                    } else {
//                        logger.error("[ ScanPicService ] delete file FAIL : {} ", fileNames[i]);
//                    }
//                }
//            }
//        }

    }

    public String getPicPath() {
        return picPath;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}

