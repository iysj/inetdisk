package com.kingsj.hadoop.action;

import com.kingsj.hadoop.common.HdfsUtil;
import com.kingsj.hadoop.common.MyWebConstant;
import com.kingsj.hadoop.model.*;
import com.kingsj.hadoop.service.HdfsFileService;
import com.kingsj.hadoop.service.UserFileService;
import com.kingsj.hadoop.service.UserParamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.*;

@Controller
@SessionAttributes(MyWebConstant.USER_CURRENT_LOGIN)
public class HDFSController {

    Logger logger = LoggerFactory.getLogger(HDFSController.class);

    @Autowired
    HdfsFileService hdfsFileService;

    @Autowired
	UserFileService userFileService;
    @Autowired
    UserParamService userParamService;

    @RequestMapping(value = { "/user/filetree" })
    @ResponseBody
    public List<FileTree> fileTree(@RequestParam(value = "id", required = false) String pid, Model model, @RequestParam(value = "isdir", required = false) String isdir,
	    @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {

	List<FileTree> treeResult = new ArrayList<FileTree>();

	List<Hfile> treeList = null;
	if (StringUtils.isNotBlank(pid)) {

	    treeList = hdfsFileService.getUserFileTree(user.getUserid(), pid, isdir);
	} else {
	    treeList = hdfsFileService.getUserFileTree(user.getUserid(), "0", isdir);
	}
	if (CollectionUtils.isEmpty(treeList)) {
	    return treeResult;
	} else {
	    for (Hfile file : treeList) {
		FileTree leaf = new FileTree();

		leaf.setId(file.getFid());
		leaf.setText(file.getFname());
		leaf.setState(file.getFtype() == 0 ? "closed" : "open");

		leaf.setFid(file.getFid());
		leaf.setPid(file.getPid());
		leaf.setFpath(file.getFpath());
		leaf.setFname(file.getFname());
		leaf.setFmd5(file.getFmd5());
		leaf.setFsize(file.getFsize());
		leaf.setFtype(file.getFtype());
		leaf.setAddtime(file.getAddtime());
		treeResult.add(leaf);
	    }
	}
	return treeResult;
    }

    @RequestMapping(value = { "/user/disk" })
    public String disk(Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {

	return "disk";
    }

    @RequestMapping(value = { "/user/mkdir" })
    @ResponseBody
    public String mkdir(@RequestParam(value = "dir", required = true) String dir, @RequestParam(value = "pid", required = false) String pid, Model model,
	    @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {
	String dirName = dir;

	if (StringUtils.isBlank(pid)) {
	    dir = "/" + dir;
	    pid = "0";
	} else {
	    Hfile pfile = hdfsFileService.selectByPrimaryKey(pid);
	    dir = pfile.getFpath() + "/" + dir;
	}

	try {
	    // 文件系统操作
	    HdfsUtil.mkDir(dir);

	    saveFile(pid, "-1", user, dir, -1l, dirName, 0);

	} catch (Exception e) {
	    return "-1";
	}

	return "0";
    }

    @RequestMapping(value = { "/user/upfile" })
    @ResponseBody
    public Map<String, Object> upfile(@RequestParam(value = "pid", required = false) String pid, @RequestParam(value = "md5", required = false) String md5, HttpServletRequest request, Model model,
	    @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {

	Map<String, Object> resultMap = new HashMap<String, Object>();

	resultMap.put("status", -1);
	resultMap.put("msg", "上传失败");
	String dir = "";

	if (StringUtils.isBlank(pid)) {
	    dir = "/";
	    pid = "0";
	} else {
	    Hfile pfile = hdfsFileService.selectByPrimaryKey(pid);
	    dir = pfile.getFpath();
	}

	// 转型为MultipartHttpRequest：
	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	// 获得文件：
	MultipartFile file = multipartRequest.getFile("file");
	// 获得文件名：
	String filename = file.getOriginalFilename();
	String hdfspath = dir + "/" + UUID.randomUUID().toString() + "_" + filename;
	// 检查是否已经存在这个文件
	if (StringUtils.isNotBlank(md5)) {
	    List<Hfile> hfileList = hdfsFileService.getFileByMd5(md5);
	    if (CollectionUtils.isNotEmpty(hfileList)) {
		hdfspath = hfileList.get(0).getFpath();
		saveFile(pid, md5, user, hdfspath, file.getSize(), filename, 1);

		resultMap.put("status", 0);
		resultMap.put("msg", "上传成功");

		return resultMap;
	    }
	}

	InputStream input = null;
	FSDataOutputStream outStream = null;
	try {
	    // 文件系统操作
	    input = file.getInputStream();

	    FileSystem fileSystem = HdfsUtil.getFileSystem();

	    outStream = fileSystem.create(new Path(hdfspath));

	    IOUtils.copyBytes(input, outStream, 4096, false);

	    saveFile(pid, md5, user, hdfspath, file.getSize(), filename, 1);

	    resultMap.put("status", 0);
	    resultMap.put("msg", "上传成功");

	} catch (Exception e) {
	    logger.error("上传文件失败" + e.getMessage());
	} finally {
	    IOUtils.closeStream(input);
	    IOUtils.closeStream(outStream);
	}

	return resultMap;
    }

    @RequestMapping(value = { "/user/delfile" })
    @ResponseBody
    public Map<String, Object> delFile(@RequestParam(value = "fid") String fid, Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {

	Map<String, Object> resultMap = new HashMap<String, Object>();

	resultMap.put("status", -1);
	resultMap.put("msg", "删除失败");

	try {
	    delFile(fid, user.getUserid());
	    resultMap.put("status", 0);
	    resultMap.put("msg", "删除成功");
	} catch (Exception e) {
	    logger.error("文件删除失败" + e.getMessage());
	}

	return resultMap;
    }

    @RequestMapping(value = { "/user/download" })
    @ResponseBody
    public Map<String, Object> download(@RequestParam(value = "fid") String fid, Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {

	Map<String, Object> resultMap = new HashMap<String, Object>();

	resultMap.put("status", -1);
	resultMap.put("msg", "下载失败");

	try {
	    UserParam userParam = userParamService.selectByParamKey(user.getUserid(), MyWebConstant.USER_DEFAULT_SAVE_PATH);
	    String basePath = "";
	    if(userParam == null){
		basePath = "d:\\";
	    }else{
		basePath = userParam.getParamval();
	    }
	    download(fid,basePath);
	    
	    resultMap.put("status", 0);
	    resultMap.put("msg", "下载成功");
	    resultMap.put("path", basePath);
	} catch (Exception e) {
	    logger.error("文件下载失败" + e.getMessage());
	}

	return resultMap;
    }

    private void download(String fid,String baseDir) throws Exception {
	Hfile hfile = hdfsFileService.selectByPrimaryKey(fid);
	if (hfile != null) {
	    if (hfile.getFtype().equals(1)) {
		String path = baseDir + File.separator +  hfile.getFpath().substring(0, hfile.getFpath().lastIndexOf("/"));
		String localPath = path  + File.separator + hfile.getFname();
		
		File f = new File(path);
		if(!f.exists()){
		    f.mkdirs();
		}
		
		
//		String localPath = baseDir + File.separator + hfile.getFpath();
		HdfsUtil.downloadFile(localPath, hfile.getFpath());
	    } else {
		// 是文件夹
		String curDir = baseDir + hfile.getFpath();
		File f = new File(curDir);
		if(!f.exists()){
		    f.mkdirs();
		}
		
		List<Hfile> fileList = hdfsFileService.getFileByPid(fid);
		if (CollectionUtils.isNotEmpty(fileList)) {
		    for (Hfile file : fileList) {
			download(file.getFid(),baseDir);
		    }
		}
		
	    }
	}

    }

    /**
     * 
     * @param fid
     * @param userId
     * @throws Exception
     */
    private void delFile(String fid, String userId) throws Exception {
	// 判断是文件还是文件夹，
	// 是文件,执行1，是文件夹,for{1}
	// 1,
	// 查找文件md5看是否有相同的文件
	// 有相同，直接删除用户记录，文件记录
	// 没有相同，删除用户记录，文件记录，hdfs文件
	Hfile hfile = hdfsFileService.selectByPrimaryKey(fid);
	UserFile ufile = userFileService.selectUserFileByFid(userId, fid);
	if (hfile.getFtype().equals(1)) {
	    // 查找用户是否有这个文件
	    String fmd5 = hfile.getFmd5();
	    List<Hfile> fileList = hdfsFileService.getFileByMd5(fmd5);
	    if (CollectionUtils.isEmpty(fileList)) {
		// 有相同的文件不能删除hdfs文件系统中的文件，删除用户相关数据库记录
		HdfsUtil.delFile(hfile.getFpath());// 调用HDFS API删除HDFS文件
	    }
	    hdfsFileService.deleteByPrimaryKey(fid);// 删除文件系统记录
	    userFileService.deleteByPrimaryKey(ufile.getId());// 删除用户文件记录
	} else {
	    // 是文件夹
	    List<Hfile> fileList = hdfsFileService.getFileByPid(fid);
	    if (CollectionUtils.isNotEmpty(fileList)) {
		for (Hfile file : fileList) {
		    delFile(file.getFid(), userId);
		}
	    }

	    hdfsFileService.deleteByPrimaryKey(fid);// 删除文件系统记录
	    userFileService.deleteByPrimaryKey(ufile.getId());// 删除用户文件记录

	    HdfsUtil.delDir(hfile.getFpath());// 调用HDFS API删除HDFS文件
	}

    }

    private Hfile saveFile(String pid, String md5, User user, String dir, Long fileSize, String filename, Integer ftype) {
	// DB操作
	String fid = UUID.randomUUID().toString();
	Date now = new Date();
	UserFile userFile = new UserFile();
	userFile.setUserid(user.getUserid());
	userFile.setAddtime(now);
	userFile.setFid(fid);
	userFile.setId(UUID.randomUUID().toString());
	userFileService.insert(userFile);

	Hfile hfile = new Hfile();
	hfile.setFid(fid);
	hfile.setFmd5(md5);
	hfile.setFsize(fileSize);
	hfile.setFtype(ftype);
	hfile.setPid(pid);
	hfile.setAddtime(now);
	hfile.setFname(filename);
	hfile.setFpath(dir);

	hdfsFileService.insert(hfile);

	return hfile;
    }
}
