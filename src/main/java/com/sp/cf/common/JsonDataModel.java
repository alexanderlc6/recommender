/**
 * json封装符合mahout的DataModel
 * alexlu
 */

package com.sp.cf.common;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang.StringUtils;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.json.CDL;
import org.json.JSONArray;
import java.io.*;

public final class JsonDataModel extends FileDataModel {

    public JsonDataModel(String jsonArrayStr) throws Exception {
        super(convertGLFile(jsonArrayStr));
    }

    private static File convertGLFile(String jsonArrayStr) throws Exception {
        JSONArray jsonarray = new JSONArray(jsonArrayStr);
        String csv = CDL.toString(jsonarray);
        csv = StringUtils.substringAfter(csv, "\n");
        String tempPath = new File(System.getProperty("java.io.tmpdir")).getParent()+File.separator+"Recommander";
        File tempFile = new File(tempPath);
        tempFile.mkdir();
        File resultFile = new File(tempFile, "jsonArray.txt");
        System.out.println(tempPath);
        if (resultFile.exists()) {
            resultFile.delete();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(resultFile), Charsets.UTF_8)) {
            writer.write(csv);
        } catch (IOException ioe) {
            resultFile.delete();
            throw ioe;
        }
        return resultFile;
    }

    @Override
    public String toString() {
        return "GroupLensDataModel";
    }

}
