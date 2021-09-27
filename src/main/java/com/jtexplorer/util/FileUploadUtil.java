package com.jtexplorer.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 *
 * @author 明明如月 QQ  605283073
 */
public class FileUploadUtil {
    /**
     * 文件分隔符（跨平台）
     */
    private static String pathSeparator = File.separator;
    /**
     * 上传文件的文件夹
     */
    private File uploadDir;
    /**
     * 获取的上传文件数组
     */
    private MultipartFile mtpfiles;

    public FileUploadUtil(File uploadDir, MultipartFile mtpfiles) {
        super();
        this.uploadDir = uploadDir;
        this.mtpfiles = mtpfiles;
    }

    /**
     * 上传文件 单文件
     *
     * @param overwrite 如果存在是否覆盖
     * @return 返回文件名
     * @throws IOException IO异常
     */
    public File uploadFile(boolean overwrite) throws IOException {

        //判断文件夹是否存在
        checkDir(uploadDir);
        File fileUpload = null;
        int i = 0;

        //获取原始文件名称
        String originalFilename = mtpfiles.getOriginalFilename();
        //当前目标文件夹
        fileUpload = new File(uploadDir, originalFilename);
        String[] orgFileSplit = StringUtil.splitFileName(originalFilename);
        fileUpload = new File(uploadDir, orgFileSplit[0] + getHourMinSecond() + StringUtil.getRandomString(4) + "." + orgFileSplit[orgFileSplit.length - 1]);
        //将获取的文件传到指定文件
        mtpfiles.transferTo(fileUpload);
        return fileUpload;
    }


    /**
     * 封装的文件保存方法
     *
     * @param fileType 文件类型
     * @param file     文件主体
     * @param session  会话信息，用于获取webapp根目录
     * @return Map
     */
    public static Map uploadFile(String fileType,
                                 String webappPath,
                                 MultipartFile file,
                                 HttpSession session
    ) {
        Map<String, Object> result = new HashMap<>(6);
        if (file.getSize() > 1024 * 1024 * 1024) {
            result.put("success", false);
            result.put("failReason", "上传文件最大限制大小为：1G!");
            return result;
        }
        MultipartFile[] d = new MultipartFile[1];
        d[0] = file;
        //获取webapp根目录
//        String webappPath = session.getServletContext().getRealPath("/");
        //项目的目录
        String projectDirPath = "upload" + pathSeparator + getYearMonthDay();
        //父文件夹路径
        String uploadDirPath = webappPath + projectDirPath + pathSeparator + fileType;
        try {
            File uploadDir = new File(uploadDirPath);
            //上传文件[允许覆盖]
            File fileUpload = new FileUploadUtil(uploadDir, d[0]).uploadFile(false);
            //相对于项目的文件路径和名称如：upload\2016-06-29\图片60.png
            String projectFileName = projectDirPath + pathSeparator + fileType + pathSeparator + fileUpload.getName();
            //文件地址
            result.put("path", "/" + FileUtil.changePath(projectFileName));
            result.put("success", true);
            //文件名
            result.put("name", file.getOriginalFilename());
            //文件大小
            result.put("size", file.getSize());

        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("failReason", "状态异常!");
        } catch (IOException e) {
            result.put("success", false);
            result.put("failReason", "IO错误！");
        }
        return result;
    }


    /**
     * 判断文件夹是否存在  如果不存在 创建
     *
     * @param uploadDir 上传文件的目录
     */
    private void checkDir(File uploadDir) {
        if (!uploadDir.exists()) {
            //创建目录
            uploadDir.mkdirs();
        }
    }

    /**
     * 根据日期获取年月日
     *
     * @return 格式化的日期
     */

    private static String getYearMonthDay() {
        try {
            return TimeTools.transformDateFormat(new Date(), "yyyy-MM-dd");
        } catch (ParseException e) {
            return "1954-10-01";
        }
    }

    private String getHourMinSecond() {
        try {
            return TimeTools.transformDateFormat(new Date(), "hhmmss");
        } catch (ParseException e) {
            return "1954-10-01";
        }
        //return new SimpleDateFormat("hhmmss").format(Calendar.getInstance().getTime());
    }
}
