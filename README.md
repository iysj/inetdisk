#网盘功能介绍
##框架

springmvc + mybatis + mysql + velocity + easyui


##主要功能

 * 新建文件夹
 * 文件上传
 	
	支持秒传功能 基于[SparkMD5](https://github.com/satazor/SparkMD5)在本地计算文件MD5值，与服务器对比实现秒传

 * 文件下载
 	
	支持下载路径设置
	
	支持文件夹递归下载
 * 文件删除
	
	支持文件递归删除


##使用帮助
	
数据库脚本在doc/sql，数据库名称netdisk


HDFS文件系统，根据自己文件系统修改resource/hdfs.properties文件中hdfsURI值

	hdfsURI=hdfs://king:8080

默认登录用户名密码为kingsj.yuan/123456
