package com.xiaoqee.hadoop.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Properties;

public class HdfsUtil {

    public static FileSystem getFileSystem() throws Exception {
	Configuration conf = new Configuration();
	Properties p = PropertiesUtil.loadConfiguration("hdfs.properties");
	String FSURI = p.getProperty("hdfsURI");
	conf.set("fs.default.name", FSURI);

	FileSystem fileSystem = FileSystem.get(new URI(FSURI), conf);

	return fileSystem;
    }

    public static void readHDFSFile(String hdfsUri) throws Exception {

	FileSystem fileSystem = getFileSystem();

	FSDataInputStream inStream = fileSystem.open(new Path(hdfsUri));

	try {
	    IOUtils.copyBytes(inStream, System.out, 4096, false);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    IOUtils.closeStream(inStream);
	}
    }

    /**
     * 
     * @param hdfsUri
     *            hdfs文件系统的URI路径
     * @param localFile
     *            本地要上传的文件
     * @throws Exception
     */
    public static void writeHDFSFile(String hdfsUri, String localFile) throws Exception {

	FileSystem fileSystem = getFileSystem();

	FSDataOutputStream outStream = fileSystem.create(new Path(hdfsUri));

	FileInputStream inStream = new FileInputStream(new File(localFile));

	try {
	    IOUtils.copyBytes(inStream, outStream, 4096, false);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    IOUtils.closeStream(inStream);
	    IOUtils.closeStream(outStream);

	}
    }

    // 上传
    public static void uploadFile(String localFile, String hdfsUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	Path srcPath = new Path(localFile);
	Path dstPath = new Path(hdfsUri);
	// 注意有好多几个重载的方法copyFromLocalFile
	fileSystem.copyFromLocalFile(srcPath, dstPath);
    }

    // 下载
    public static void downloadFile(String localFile, String hdfsUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	// Path srcPath = new Path(hdfsUri);
	// Path dstPath = new Path(localFile);
	// fileSystem.copyToLocalFile(srcPath, dstPath);
	// fileSystem.close();
	FSDataInputStream inStream = fileSystem.open(new Path(hdfsUri));
	FileOutputStream outStream = new FileOutputStream(new File(localFile));
	try {
	    IOUtils.copyBytes(inStream, outStream, 4096, false);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    IOUtils.closeStream(inStream);
	    IOUtils.closeStream(outStream);
	}

    }

    // 重命名
    public static void reNameFile(String hdfsSrcUri, String hdfsDstUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	Path srcPath = new Path(hdfsSrcUri);
	Path dstPath = new Path(hdfsDstUri);
	fileSystem.rename(srcPath, dstPath);
    }

    public static void delFile(String hdfsUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	Path srcPath = new Path(hdfsUri);

	fileSystem.delete(srcPath, false);

    }

    public static void mkDir(String hdfsDirUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	Path hdfsDirPath = new Path(hdfsDirUri);
	fileSystem.mkdirs(hdfsDirPath);
    }

    public static void delDir(String hdfsDirUri) throws Exception {
	FileSystem fileSystem = getFileSystem();
	Path hdfsDirPath = new Path(hdfsDirUri);

	fileSystem.delete(hdfsDirPath, true);
    }

    public static void getFileListInfo(String hdfsDirUri) throws Exception {
	FileSystem fs = getFileSystem();
	Path hdfsDirPath = new Path(hdfsDirUri);

	RemoteIterator<LocatedFileStatus> rit = fs.listFiles(hdfsDirPath, true);

	while (rit.hasNext()) {
	    System.out.println("############################");
	    LocatedFileStatus lfs = rit.next();
	    System.out.println("Path:" + lfs.getPath());
	    System.out.println("isDirectory:" + lfs.isDirectory());
	    System.out.println("isFile:" + lfs.isFile());
	    System.out.println("isFile:" + lfs.isFile());
	    System.out.println("Owner:" + lfs.getOwner());
	    System.out.println("Len:" + lfs.getLen());
	    System.out.println("Replication():" + lfs.getReplication());
	    BlockLocation[] bls = lfs.getBlockLocations();
	    for (BlockLocation bl : bls) {
		System.out.println("Hosts:");
		for (String host : bl.getHosts()) {
		    System.out.println(host);
		}
		System.out.println("Names:" + bl.getNames());
		for (String host : bl.getNames()) {
		    System.out.println(host);
		}
		System.out.println("Length:" + bl.getLength());
		System.out.println("Offset:" + bl.getOffset());
	    }
	}
    }

    public static void getDNInfo() throws Exception {
	FileSystem fs = getFileSystem();
	DistributedFileSystem hdfs = (DistributedFileSystem) fs;
	DatanodeInfo[] dnInfo = hdfs.getDataNodeStats();
	for (DatanodeInfo info : dnInfo) {
	    System.out.println("###################");
	    System.out.println("HostName:" + info.getHostName());
	    System.out.println("InfoAddr:" + info.getInfoAddr());
	    System.out.println("InfoPort:" + info.getInfoPort());
	    System.out.println("Name:" + info.getName());
//	    System.out.println("StorageID:" + info.getStorageID());
	    System.out.println("DfsUsed:" + info.getDfsUsed());
	}
    }

    /**
     * 查找某文件在集群中的位置
     * 
     * @throws Exception
     */
    public static void getFileInDnPosition(String hdfsUri) throws Exception {
	FileSystem fs = getFileSystem();
	Path hdfsUriPath = new Path(hdfsUri);
	FileStatus fstatus = fs.getFileStatus(hdfsUriPath);

	BlockLocation[] bls = fs.getFileBlockLocations(fstatus, 0, fstatus.getLen());

	int blockLen = bls.length;

	for (int i = 0; i < blockLen; ++i) {
	    String[] hosts = bls[i].getHosts();
	    System.out.println("block_" + i + "_location:" + hosts[i]);
	    System.out.println("Length:" + bls[i].getLength());
	    System.out.println("Offset:" + bls[i].getOffset());
	}
	// for(BlockLocation bl :bls){
	// System.out.println("Hosts:");
	// for(String host : bl.getHosts()){
	// System.out.println(host);
	// }
	// System.out.println("Names:"+bl.getNames());
	// for(String host : bl.getNames()){
	// System.out.println(host);
	// }
	// System.out.println("Length:"+bl.getLength());
	// System.out.println("Offset:"+bl.getOffset());
	// }

    }

    public static void createFileAndWriteStr(String hdfsUri, String strInto) throws Exception {
	FileSystem fs = getFileSystem();
	Path path = new Path(hdfsUri);
	FSDataOutputStream out = fs.create(path);
	out.writeUTF(strInto);
	fs.close();
    }
}
