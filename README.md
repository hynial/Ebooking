reference_url：http://blog.csdn.net/huagong_adu/article/details/6929817

javac -d $BIN_PATH/$ONSSERVER -classpath $JAR_PATH/jdom.jar:$JAR_PATH/oro-2.0.8.jar @$SRC_PATH/sources.list 

其中-classpath参数毕竟太麻烦了，需要一个一个将用到的jar文件加上，可以直接将所有用到的.jar包复制到jdk/jer/lib/ext/下；另外-d $BIN_PATH/$ONSSERVER ，这样也太麻烦了，直接将源文件夹copy一份，不指定目标文件存放路径，默认生存到源文件夹下，然后执行find ./ -name *.java | xargs rm 直接删错所有源文件，然后将生成的.class文件copy到项目文件夹/WebRoot/WEB-INF/classes下，再直接将WebRoot的文件复制到Tomcat下的项目文件夹下，直接启动Tomcat就可以运行了

下面是文件run，用于执行程序：(这个不是我的项目)
#!/bin/sh  
  
# Define some constants  
ONSSERVER=ONSServer  
PROJECT_PATH=/root/iot-oid  
JAR_PATH=$PROJECT_PATH/lib  
BIN_PATH=$PROJECT_PATH/bin  
  
# Run the project as a background process  
nohup java -classpath $BIN_PATH:$JAR_PATH/jdom.jar:$JAR_PATH/oro-2.0.8.jar com.ONSServer.DoUDPRequest &  
