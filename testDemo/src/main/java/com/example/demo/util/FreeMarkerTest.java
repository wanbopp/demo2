package com.example.demo.util;

import cn.hutool.core.io.file.PathUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/4 9:46
 * @注释
 */
//模板引擎
public class FreeMarkerTest {


    private static final String FONT = "front/simhei.ttf";
    private static final String IMG_EXT = "png";


    private static Configuration configuration = null;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(FreeMarkerTest.class, "/template");
    }

    public static void main(String[] args) throws IOException, TemplateException, DocumentException, PdfException {
        //参数MAP
        Map<String, Object> data = new HashMap<>();
        data.put("payer_account", "1234567890");
        data.put("payee_account", "09876542321");
        data.put("payer_username", "人保金融服务有限公司");
        data.put("payee_username", "中国人民财产保险有限公司北京分公司");
        data.put("payer_bank", "中国农业银行北京西城支行");
        data.put("payee_bank", "中国工商银行北京石景山支行");
        data.put("amount_in_figures", "1299.00");
        data.put("amount_in_words", "人民币壹仟贰佰玖拾玖元");
        data.put("currency", "人民币");
        data.put("voucher_number", "99999999999");
        data.put("date", LocalDate.now());
        data.put("time", LocalTime.now());
        data.put("postscript", "这里是附言");
        data.put("receipt_id", "这里是回执单编号");
        //获取模板
        String s = freeMarkerRender(data, "template_freemarker1.ftl");
        ByteArrayOutputStream pdf = createPdf(s);
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\wanbo_pp\\Desktop\\aim.jpg"));
        pdfToImg(pdf.toByteArray(), 10, 1, "jpg", out);
    }

    public static ByteArrayOutputStream createPdf(String content) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, BaseFont.IDENTITY_H, true);
        // 解析html生成pdf
        render.setDocumentFromString(content);
        ;
        render.layout();//计算
        render.createPDF(byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = configuration.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    //根据PDF二进制流生成图片
    public static void pdfToImg(byte[] bytes, float scaling, int pageNum, String formatName, FileOutputStream outputStream) throws PdfException, IOException {
        //推荐的方法打开PDFDecoder
        PdfDecoder pdfDecoder = new PdfDecoder();
        FontMappings.setFontReplacements();
        //修改图片清晰度
        pdfDecoder.scaling = scaling;
        pdfDecoder.openPdfArray(bytes);//打开PDF文件，生成PDFDecoder
        //获取第num页的PDF
        BufferedImage img = pdfDecoder.getPageAsImage(pageNum);
        ImageIO.write(img, formatName, outputStream);

    }
}
