package cn.sgx.zyqd.bo;

import lombok.Data;

import java.util.Arrays;

@Data
public class PicVo {
    private String byteLength;
    private byte[] picBin;
    private String pinBinBin;

//    public PicVo init (){
//
//        StringBuffer stringBuffer = new StringBuffer();
//
//        Arrays.asList( picBin.toCharArray()).stream().forEach(chars -> {
//            //...把16进行2进制 待处理
//        });
//        return this;
//    }

}
