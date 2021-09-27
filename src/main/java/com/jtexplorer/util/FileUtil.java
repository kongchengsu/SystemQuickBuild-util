package com.jtexplorer.util;

import org.apache.poi.util.IOUtils;
//import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by 刘汪洋 on 2016/7/6.
 */
public class FileUtil {


    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 转化为操作系统中路径
     *
     * @param path
     * @return
     */
    public static String getRealFilePath(String path) {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    /**
     * 格式化为HTTP传输的路径
     *
     * @param path
     * @return
     */
    public static String getHttpURLPath(String path) {
        return path.replace("\\", "/");
    }

    /**
     * 将inputstream装成字符串
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); // 实例化输入流，并获取网页代码
        String s; // 依次循环，至到读的值为空
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        reader.close();

        String str = sb.toString();
        return str;
    }

    /**
     * 转换文件路径 如 a\sdfdf\2.png
     * 转化为  a/sdfdf/2.png
     *
     * @param origPath 原始文件目录
     * @return 转换后的目录
     */
    public static String changePath(String origPath) {
        return origPath.replaceAll("\\\\", "/");
    }


    /**
     * 将上传的录音转为mp3格式
     *
     * @param webroot    项目的根目录
     * @param sourcePath 文件的相对地址
     */
    public static String ToWav(String webroot, String sourcePath) {
        //File file = new File(sourcePath);
        webroot = "F://ffmpegwin64";
        String[] a = sourcePath.split("\\.");
        String targetPath = a[0] + ".wav";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start = System.currentTimeMillis();
            //Process p=run.exec(webroot+"files/ffmpeg -i "+webroot+sourcePath+" -acodec libmp3lame "+webroot+targetPath);
            // 执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            Process p = run.exec(webroot + "/bin/ffmpeg -i " + sourcePath + " " + targetPath);
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            long end = System.currentTimeMillis();
            //System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
            //删除原来的文件
            /*if(file.exists()){
            file.delete();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }

        return targetPath;
    }

    /**
     * 根据文件名，返回文件类型
     *
     * @param fileName
     * @return
     */
    public static String fileType(String fileName) {
        if (fileName == null) {
            fileName = "文件名为空！";
            return fileName;


        } else {
// 获取文件后缀名并转化为写，用于后续比较
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
// 创建图片类型数组
            String img[] = {"bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                    "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"};
            for (int i = 0; i < img.length; i++) {
                if (img[i].equals(fileType)) {
                    return "图片";
                }
            }


// 创建文档类型数组
            String document[] = {"txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt"};
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(fileType)) {
                    return "文档";
                }
            }
// 创建视频类型数组
            String video[] = {"mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm"};
            for (int i = 0; i < video.length; i++) {
                if (video[i].equals(fileType)) {
                    return "视频";
                }
            }
// 创建音乐类型数组
            String music[] = {"mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                    "m4a", "vqf"};
            for (int i = 0; i < music.length; i++) {
                if (music[i].equals(fileType)) {
                    return "音频";
                }
            }

        }
        return "其它";
    }

    /**
     * 将MultipartFile转为File
     *
     * @param multipartFile 文件
     * @return File
     */
    public static File multipartFileToFile(MultipartFile multipartFile,String savePath) throws IOException {
        File file = new File(savePath);
        OutputStream output = new FileOutputStream(file);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(multipartFile.getBytes());
        bufferedOutput.flush();
        bufferedOutput.close();
        return file;
    }

    /**
     * 将File转为MultipartFile
     *
     * @param file 文件
     * @return File
     */
    public static MultipartFile fileToMultipartFile(File file) {
        MultipartFile multipartFile = null;
        try {
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }

}
