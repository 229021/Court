package com.san.platform.config;

import java.io.*;

public class FileUtil {
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void copy(InputStream in, OutputStream out) throws Exception {
        byte[] buf = new byte[1024];
        int len = 0;
        /*读取文件内容并写入文件字节流中*/
        while((len = in.read(buf))!=-1) {
            out.write(buf, 0, len);
        }
    }

    public static void copyFile (String srcPathStr, String desPathStr) {
        //获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //目标文件地址
        System.out.println("源文件:"+newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        System.out.println("目标文件地址:"+desPathStr);
        try
        {
            FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!=-1)//循环读取数据
            {
                fos.write(datas,0,len);
            }
            fis.close();//释放资源
            fos.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String srcPathStr = "D:\\lib-image\\img-visit\\_byCardNo[372922199902015417_20190723171335_IDInfoPic.jpg"; //源文件地址
        String desPathStr = "D:\\lib-image\\img-person"; //目标文件地址
        copyFile(srcPathStr, desPathStr);//将E:\\java task\\zhl.txt文件拷贝到E:\\java task\\zhlll
    }

}
