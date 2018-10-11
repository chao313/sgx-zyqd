package cn.sgx.zyqd.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkUtil {

    private static Configuration configuration = new Configuration();

    public static StringBuffer generateXmlByTemplate(Map<String, Object> map, String templatePath)
            throws IOException, TemplateException {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);
        configuration.setDirectoryForTemplateLoading(
                ResourceUtils.getFile("classpath:"));
        Template template =
                configuration.getTemplate(templatePath); //文件名
        java.io.StringWriter
                stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.getBuffer();
    }

}
