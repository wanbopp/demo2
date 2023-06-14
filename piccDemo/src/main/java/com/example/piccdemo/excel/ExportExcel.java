package com.example.piccdemo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.piccdemo.domain.Students;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/14 13:29
 * @注释
 */
@Controller
@Slf4j
public class ExportExcel {


    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     **/

    public List<Students> simpleRead() {
        // 写法1：
        String fileName = "C:\\Users\\wanbo_pp\\Desktop\\aim\\aim.xlsx";
        ArrayList<Students> students = new ArrayList<>();
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        ExcelReaderSheetBuilder sheet = EasyExcel.read(fileName, Students.class, new PageReadListener<Students>(students::addAll)).sheet();
        sheet.doRead();


        // 写法2：
        //匿名内部类  不用额外写一个DemoDataListener类
        //这里需要指定哪一个类去读，然后读取第一个sheet
        //文件流会自动关闭
        EasyExcel.read(fileName, Students.class, new ReadListener<Students>() {
            /**
             * 单词缓存的数量
             */
            private static final int BATCH_COUNT = 100;


            /**
             * 临时存储数据
             */
            private List<Students> list = new ArrayList<Students>();



            /**
             * 每读取一次就会调用invoke方法
             */
            @Override
            public void invoke(Students o, AnalysisContext analysisContext) {
                list.add(o);
                if (list.size() >= BATCH_COUNT) {
                    saveData();
                    //完成后清理list
                    list.clear();
                }
            }


            /**
             * 读取完后会调用
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                saveData();
                log.info("所有数据解析完成！");
            }


            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", list.size());
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();





        return students;
    }




    public static void main(String[] args) {
        ExportExcel exportExcel = new ExportExcel();
        List<Students> students = exportExcel.simpleRead();
        students.forEach(System.out::println);
    }

    //导出excel
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
    }


}
