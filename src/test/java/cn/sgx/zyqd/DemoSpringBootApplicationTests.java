package cn.sgx.zyqd;

import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.service.HttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoSpringBootApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());
//	http:
//	limit: 5
//	host: localhost:65500/chao

    @Value(value = "${http.limit}")
    private Integer limit;

    @Value(value = "${http.limitWeigeht}")
    private Integer limitWeigeht;
    @Value(value = "${http.host}")
    private String host;

    @Autowired
    HttpService httpService;


    @Test
    public void testHttpService() {
        try {
            List<PicAndStationVo> picAndStationVos =
                    httpService.getPicAndStationVoLimit(host, limitWeigeht, limit,"dataSource196");
            logger.error("[ SUCCESS testHttpService getPicAndStationVoLimit ] vos: {} ",picAndStationVos);
        } catch (IOException e) {
            logger.error("[ ERROR testHttpService getPicAndStationVoLimit ] msg: {} ",e.getMessage(),e);
        }
    }

    @Test
    public void xx(){
        System.out.println(4.5f == 4.5); // 4.5f会转换成4.5(double类型的）因此true
        System.out.println(4.4f == 4.4); // false
    }

}


