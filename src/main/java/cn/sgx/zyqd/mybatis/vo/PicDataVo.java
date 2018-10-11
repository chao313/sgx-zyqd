package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;

@Data
public class PicDataVo {
    private Integer id;
    private String picName;
    private byte[] picBin;

    @Override
    public String toString() {
        return "PicDataVo{" +
                "id=" + id +
                ", picName='" + picName + '\'' +
                '}';
    }
}
