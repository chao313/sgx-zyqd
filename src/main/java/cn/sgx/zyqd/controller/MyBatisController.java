package cn.sgx.zyqd.controller;

import cn.sgx.zyqd.framework.Code;
import cn.sgx.zyqd.framework.Response;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import cn.sgx.zyqd.service.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MyBatisController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StationDataService stationDataService;

    @Autowired
    private PicAndStationService picAndStationService;

    @Autowired
    private PicDataService picDataService;

    @Autowired
    private ScanPicService scanPicService;

    @Autowired
    private SocketPushService socketPushService;


    @GetMapping(value = "/stationData/queryByITotalWeight/{limitWeight}")
    public Response getStationDataVoByLimitWeight(@PathVariable(value = "limitWeight") Integer limitWeight) {
        logger.info("[ getStationDataVoByLimitWeight ] stationDataService/queryByITotalWeight ");
        Response<List<StationDataVo>> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<StationDataVo> vos =
                    stationDataService.queryByITotalWeight(limitWeight);
            logger.info("[ getStationDataVoByLimitWeight : stationDataVos  ]成功 vos:{}", vos);
            socketPushService.push(vos);
            response.setContent(vos);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[getStationDataVoByLimitWeight ]失败 path:{}", e.getMessage(), e);
        }
        return response;
    }


    @ApiOperation(value = "查询当天的，没有发送成功的数据", notes = "查询当天的，没有发送成功的数据")
    @GetMapping(value = "/picAndStation/queryByITotalWeight/{limitWeight}")
    public Response getPicStationVoByLimitWeight(@PathVariable(value = "limitWeight") Integer limitWeight) {
        logger.info("[ getPicStationVoByLimitWeight ] stationDataService/queryByITotalWeight ");
        Response<List<PicAndStationVo>> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<PicAndStationVo> vos =
                    picAndStationService.queryByITotalWeight(limitWeight);
            logger.info("[ getPicStationVoByLimitWeight : PicAndStationVo  ]成功 vos:{}", vos);
            response.setContent(vos);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[getPicStationVoByLimitWeight ]失败 path:{}", e.getMessage(), e);
        }
        return response;
    }

    @ApiOperation(value = "根据id来更新picAndStation的发送状态", notes = "根据id来更新picAndStation的发送状态")
    @GetMapping(value = "/picAndStation/updateStatusByID/{id}")
    public Response updatePicDataById(@PathVariable(value = "id") Integer id) {
        logger.info("[ updatePicDataById ] picDataService/updateStatusByID(id) ");
        Response<Boolean> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
           boolean bool =
                    picDataService.updateStatusByID(id);
            logger.info("[ updatePicDataById :   ]成功 vos:{}", bool);
            response.setContent(bool);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[updatePicDataById ]失败 path:{}", e.getMessage(), e);
        }
        return response;
    }

    @GetMapping(value = "/SocketPushService/push/{limitWeight}")
    public Response SocketPushService_push
            (@PathVariable(value = "limitWeight") Integer limitWeight) {
        logger.info("[ socket push ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
        Response<Boolean> response = new Response<Boolean>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<StationDataVo> vos =
                    stationDataService.queryByITotalWeight(limitWeight);
            logger.info("[ socket push : stationDataVos  ]成功 vos:{}", vos);
            socketPushService.push(vos);
            response.setContent(true);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[ socket push ]失败 path:{}", scanPicService.getPicPath(), e);
        }
        return response;
    }


    @ApiOperation(value = "只扫描当天图片.jpg结尾", notes = "只扫描当天图片.jpg结尾")
    @GetMapping(value = "/scanPicService/scanDir")
    public Response scanPicService_scanDir() {
        logger.info("[ 扫描文件夹 ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
        Response<List<PicDataVo>> response = new Response<List<PicDataVo>>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<PicDataVo> vos = scanPicService.scanDirAndGetTodayPicNotInSql();
            response.setContent(vos);
            logger.info("[ 扫描文件夹 ]成功 vos:{}", vos);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[ 扫描文件夹 ]失败 path:{}", scanPicService.getPicPath(), e);
        }
        return response;
    }

    @GetMapping(value = "/picDataService/savePicDataVos")
    public Response picDataService_scanDir() {
        logger.info("[ 扫描文件夹 && 存入数据库  ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
        Response<List<PicDataVo>> response = new Response<List<PicDataVo>>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<PicDataVo> vos = scanPicService.scanDirAndGetTodayPicNotInSql();
            logger.info("[ 扫描文件夹 ]成功 vos:{}", vos);
            picDataService.savePicDataVos(vos);
            logger.info("[ 存入数据库 ]成功 vos:{}", vos);
            response.setContent(vos);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.info("[ 扫描文件夹 && 存入数据库  ]path:{}", scanPicService.getPicPath(), e);
        }
        return response;
    }


}
