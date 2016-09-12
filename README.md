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

	hdfsURI=hdfs://hadoop-zengjr.xiaoqee.com:8020

默认登录用户名密码为zengjr/zengjr

上传文件大小限制
修改配置文件resources/netdisk-servlet.xml中multipartResolver

	<bean id="multipartResolver"
			class="org.springframework.web.multipart.commons.CommonsMultipartResolver"
			p:defaultEncoding="utf-8">
			<!-- 设置上传文件大小的参数 10M -->
			<property name="maxUploadSize" value="10000000" />
		</bean>

##功能截图

 * 登录
  
	![](/doc/pic/login.jpg)

 * 主界面
  
	![](/doc/pic/main.jpg)

 * 网盘菜单
  
	![](/doc/pic/menu.jpg)

 * 上传
  
	![](/doc/pic/upload.jpg)

 * 下载
  
	![](/doc/pic/download.jpg)

 * 新建文件夹
  
	![](/doc/pic/createdir.jpg)

 * 下载路径设置
  
	![](/doc/pic/set.jpg)
 * 修改密码
  
	![](/doc/pic/pwd.jpg)

 * 退出
  
	![](/doc/pic/logout.jpg)