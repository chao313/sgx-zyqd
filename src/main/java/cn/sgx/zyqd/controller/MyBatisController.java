package cn.sgx.zyqd.controller;

import cn.sgx.zyqd.datasource.DataSourceChange;
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


    //    @GetMapping(value = "/stationData/queryByITotalWeight/{limitWeight}")
//    public Response getStationDataVoByLimitWeight(@PathVariable(value = "limitWeight") Integer limitWeight) {
//        logger.info("[ getStationDataVoByLimitWeight ] stationDataService/queryByITotalWeight ");
//        Response<List<StationDataVo>> response = new Response<>();
//        try {
//            response.setCode(Code.System.OK);
//            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
//            List<StationDataVo> vos =
//                    stationDataService.queryByITotalWeight(limitWeight);
//            logger.info("[ getStationDataVoByLimitWeight : stationDataVos  ]成功 vos:{}", vos);
//            socketPushService.push(vos);
//            response.setContent(vos);
//
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.toString());
//            response.addException(e);
//            logger.error("[getStationDataVoByLimitWeight ]FAIL path:{}", e.getMessage(), e);
//        }
//        return response;
//    }
//

    @DataSourceChange()
    @ApiOperation(value = "查询当天的，没有发送成功的数据", notes = "查询当天的，没有发送成功的数据")
    @GetMapping(value = "/picAndStation/queryByITotalWeight/{limitWeight}/{dataSource}")
    public Response getPicStationVoByLimitWeight(@PathVariable(value = "limitWeight") Integer limitWeight,
                                                 @PathVariable(value = "dataSource") String dataSource) {
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
            logger.error("[getPicStationVoByLimitWeight ]FAIL path:{}", e.getMessage(), e);
        }
        return response;
    }

    /**
     * 由SocketPushTimer调用的，请求查询当天的，没有发送成功的数据
     * @param limitWeight
     * @param limit
     * @param dataSource
     * @return
     */
    @DataSourceChange()
    @ApiOperation(value = "查询当天的，没有发送成功的数据,限制查询的limit", notes = "查询当天的，没有发送成功的数据，限制查询的limit")
    @GetMapping(value = "/picAndStation/queryByITotalWeightLimit/{limitWeight}/{limit}/{dataSource}")
    public Response getPicStationVoByLimitWeightLimit(@PathVariable(value = "limitWeight") Integer limitWeight,
                                                      @PathVariable(value = "limit") Integer limit,
                                                      @PathVariable(value = "dataSource") String dataSource) {
        logger.info("[ getPicStationVoByLimitWeight ] stationDataService/queryByITotalWeight ");
        Response<List<PicAndStationVo>> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<PicAndStationVo> vos =
                    picAndStationService.queryByITotalWeightLimit(limitWeight, limit);
            logger.info("[ queryByITotalWeightLimit : PicAndStationVo  ]成功 vos:{}", vos);
            response.setContent(vos);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.error("[queryByITotalWeightLimit ]FAIL path:{}", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 由SocketPushTimer调用的 查询当天的，没有发送成功的数据,限制查询的limit
     * @param limit
     * @param dataSource
     * @return
     */
    @DataSourceChange()
    @ApiOperation(value = "查询当天的，没有发送成功的数据,限制查询的limit", notes = "查询当天的，没有发送成功的数据，限制查询的limit")
    @GetMapping(value = "/picAndStation/queryAllLimit/{limit}/{dataSource}")
    public Response getPicStationVoAllByLimit(@PathVariable(value = "limit") Integer limit,
                                                      @PathVariable(value = "dataSource") String dataSource) {
        logger.info("[ getPicStationVoByLimitWeight ] stationDataService/queryByITotalWeight ");
        Response<List<PicAndStationVo>> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            List<PicAndStationVo> vos =
                    picAndStationService.queryAllLimit(limit);
            logger.info("[ queryByITotalWeightLimit : PicAndStationVo  ]成功 vos:{}", vos);
            response.setContent(vos);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.error("[queryByITotalWeightLimit ]FAIL path:{}", e.getMessage(), e);
        }
        return response;
    }

    /**
     *  由SocketPushTimer调用的 根据id来更新picAndStation的发送状态
     * @param id
     * @param dataSource
     * @return
     */
    @DataSourceChange()
    @ApiOperation(value = "根据id来更新picAndStation的发送状态", notes = "根据id来更新picAndStation的发送状态")
    @GetMapping(value = "/picAndStation/updateStatusByID/{id}/{dataSource}")
    public Response updatePicDataById(@PathVariable(value = "id") Integer id,
                                      @PathVariable(value = "dataSource")String dataSource) {
        logger.info("[ updatePicDataById ] picDataService/updateStatusByID(id) ");
        Response<Boolean> response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
            boolean bool =
                    picDataService.updateStatusByID(id);
            logger.info("[ updatePicDataById :   ]SUCCESS vos:{}", bool);
            response.setContent(bool);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            logger.error("[updatePicDataById ]FAIL path:{}", e.getMessage(), e);
        }
        return response;
    }

//    @GetMapping(value = "/SocketPushService/push/{limitWeight}")
//    public Response SocketPushService_push
//            (@PathVariable(value = "limitWeight") Integer limitWeight) {
//        logger.info("[ socket push ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
//        Response<Boolean> response = new Response<Boolean>();
//        try {
//            response.setCode(Code.System.OK);
//            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
//            List<StationDataVo> vos =
//                    stationDataService.queryByITotalWeight(limitWeight);
//            logger.info("[ socket push : stationDataVos  ]SUCCESS vos:{}", vos);
//            socketPushService.push(vos);
//            response.setContent(true);
//
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.toString());
//            response.addException(e);
//            logger.error("[ socket push ]FAIL path:{}", scanPicService.getPicPath(), e);
//        }
//        return response;
//    }


//    @ApiOperation(value = "只扫描当天图片.jpg结尾", notes = "只扫描当天图片.jpg结尾")
//    @GetMapping(value = "/scanPicService/scanDir")
//    public Response scanPicService_scanDir() {
//        logger.info("[ 扫描文件夹 ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
//        Response<List<PicDataVo>> response = new Response<List<PicDataVo>>();
//        try {
//            response.setCode(Code.System.OK);
//            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
//            List<PicDataVo> vos = scanPicService.scanDirAndGetTodayPicNotInSql();
//            response.setContent(vos);
//            logger.info("[ 扫描文件夹 ]SUCCESS vos:{}", vos);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.toString());
//            response.addException(e);
//            logger.error("[ 扫描文件夹 ]FAIL path:{}", scanPicService.getPicPath(), e);
//        }
//        return response;
//    }
//
//    @GetMapping(value = "/picDataService/savePicDataVos")
//    public Response picDataService_scanDir() {
//        logger.info("[ 扫描文件夹 && 存入数据库  ]scanPicService/scanDirAndGetTodayPicNotInSql :{}", scanPicService.getPicPath());
//        Response<List<PicDataVo>> response = new Response<List<PicDataVo>>();
//        try {
//            response.setCode(Code.System.OK);
//            response.setMsg(Code.System.SERVER_SUCCESS_MSG);
//            List<PicDataVo> vos = scanPicService.scanDirAndGetTodayPicNotInSql();
//            logger.info("[ 扫描文件夹 ]SUCCESS vos:{}", vos);
//            picDataService.savePicDataVos(vos);
//            logger.info("[ 存入数据库 ]SUCCESS vos:{}", vos);
//            response.setContent(vos);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.toString());
//            response.addException(e);
//            logger.error("[ 扫描文件夹 && 存入数据库  ]path:{}", scanPicService.getPicPath(), e);
//        }
//        return response;
//    }


}
