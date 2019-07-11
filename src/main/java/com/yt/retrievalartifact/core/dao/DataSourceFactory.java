package com.yt.retrievalartifact.core.dao;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSourceFactory {

    //利用单例模式来创建数据源
    public  static volatile DruidDataSource instance;


    private DataSourceFactory(){

    }

    public  static DataSource getInstance(){
        //双重校验
        if(instance==null){
            synchronized (DataSourceFactory.class){
                if(instance==null){
                    instance=new DruidDataSource();
                    //url
                    //userName
                    //password

                    //这是连接MySQL的配置
//                   instance.setUrl("jdbc:mysql://localhost:3306/thing_store");
//                   instance.setUsername("root");
//                   instance.setPassword("root");
//                   instance.setDriverClassName("com.mysql.jdbc.Driver");

                    //这是连接H2数据库的配置
                    instance.setTestWhileIdle(false);
                    instance.setDriverClassName("org.h2.Driver");
                    String  path=System.getProperty("user.dir")+ File.separator+"my-retrieval-artifact";
                    instance.setUrl("jdbc:h2:"+path);

                    //数据库创建完成之后，初始化表的结构
                    databaseInit();
                }
            }
        }
        return instance;
    }

//    public static void main(String[] args) {
//        DataSource dataSource=DataSourceFactory.getInstance();
//        try (Connection connection=dataSource.getConnection();
//            PreparedStatement statement=
//                    connection.prepareStatement("insert into things (name,path,depth,file_type) values (?,?,?,?)")
//        ) {
//            statement.setString(1,"简历.pdf");
//            statement.setString(2,"D:\\java_prc\\Test");
//            statement.setInt(3,2);
//            statement.setString(4,"aa");
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
 //   }

    public static void databaseInit(){
       //将

        StringBuffer sb=new StringBuffer();
        try(
        InputStream in=
                DataSourceFactory.class.
                        getClassLoader().getResourceAsStream("database.sql");

        ) {

            if(in !=null){
                try(BufferedReader reader=new BufferedReader(new InputStreamReader(in))){

                    String line=null;
                    while((line=reader.readLine())!=null){
                        sb.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else{
                throw new RuntimeException("database.sql script can't load,please check it.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sql=sb.toString();
        try(Connection connection=getInstance().getConnection()) {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
