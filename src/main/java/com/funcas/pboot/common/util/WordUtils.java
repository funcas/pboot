package com.funcas.pboot.common.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年09月30日
 */
public class WordUtils {
    private static Logger logger = LoggerFactory.getLogger(WordUtils.class);

    /**
     * 设置aspose的license
     * @throws Exception
     */
    public static void getLicense() throws Exception {
        try {
            InputStream is = com.aspose.words.Document.class.getResourceAsStream("/resources/license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            is.close();
        } catch (Exception e) {
            logger.error(null, e);
            throw e;
        }
    }

    /**
     * doc转pdf
     * @param inPath
     * @param outPath
     * @throws Exception
     */
    public static void doc2pdf(String inPath, String outPath) {
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath);
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            logger.debug("convert OK! " + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            logger.error(null, e);
        }
    }

//    public static String renderWord2Pdf(Map<String,Object> dataMap, String tplName) throws Exception {
//        String tmpDir = System.getProperty("java.io.tmpdir");
//        String destFile = tmpDir + "pmo.pdf";
//        List<String> fields = Lists.newArrayList();
//        List<Object> vals = Lists.newArrayList();
//        for(String key : dataMap.keySet()){
//            fields.add(key);
//            vals.add(dataMap.get(key));
//        }
//        String classpath = WordUtils.class.getClassLoader().getResource("").getPath();
//        Document doc = new Document(classpath + "tpls" + File.separator + tplName);
//
//        doc.getMailMerge().execute(fields.toArray(new String[0]), vals.toArray());
//        doc.save(destFile, SaveFormat.PDF);
//        return destFile;
//    }
}
