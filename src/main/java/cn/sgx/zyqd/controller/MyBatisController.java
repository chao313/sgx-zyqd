package cn.sgx.zyqd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sgx.zyqd.service.CatService;
import cn.sgx.zyqd.mybatis.vo.Cat;

@RestController
public class MyBatisController {

    @Autowired
    private CatService catService;

    @GetMapping(value = "/queryById/{id}")
    public Cat queryById(@PathVariable(value = "id") Integer id) {
        return catService.queryById(id);
    }

    @PostMapping(value = "/insert")
    public Integer queryById(@RequestBody Cat cat) {
        return catService.insert(cat);
    }

    @PutMapping(value = "/update")
    public Integer updateById(@RequestBody Cat cat) {
        return catService.update(cat);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Integer deleteById(@PathVariable(value = "id") Integer id) {
        return catService.delete(id);
    }
}
