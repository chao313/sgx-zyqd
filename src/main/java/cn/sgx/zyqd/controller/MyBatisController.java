package cn.sgx.zyqd.controller;

import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import cn.sgx.zyqd.service.StationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MyBatisController {

    @Autowired
    private StationDataService stationDataService;

    @GetMapping(value = "/StationDataVo/{id}")
    public List<StationDataVo> queryById(@PathVariable(value = "limitWeight") Integer limitWeight) {
        return stationDataService.queryByITotalWeight(limitWeight);
    }


}
