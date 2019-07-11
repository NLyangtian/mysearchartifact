package com.yt.retrievalartifact.core.dao.FileIndexImpl;

import com.yt.retrievalartifact.core.dao.DataSourceFactory;
import com.yt.retrievalartifact.core.dao.FileIndexDao;
import com.yt.retrievalartifact.core.model.Condition;
import com.yt.retrievalartifact.core.model.FileType;
import com.yt.retrievalartifact.core.model.Things;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileIndexDaoImpl implements FileIndexDao {

    //DataSource.getConnection-->通过数据源工厂获取DataSource实例化对象

    //被final修饰的变量要么在定义的时候初始化，要么在构造方法中初始化
    private  final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public void insert(Things things) {
        //JDBC 操作
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=this.dataSource.getConnection();
            String sql="insert into things (name,path,depth,file_type) values(?,?,?,?)";
            statement=connection.prepareStatement(sql);
            statement.executeUpdate();
        //预编译命令中SQL的占位符赋值
            statement.setString(1,things.getName());
            statement.setString(2,things.getPath());
            statement.setInt(3,things.getDepth());
            statement.setString(4,things.getFileType().name());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            releaseResource(null,statement,connection);
        }

    }

    @Override
    public void delete(Things things) {
        //JDBC 操作
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=this.dataSource.getConnection();
            String sql="delete from things where path=?";
            statement=connection.prepareStatement(sql);
            statement.executeUpdate();
            //预编译命令中SQL的占位符赋值
            statement.setString(1,things.getPath());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            releaseResource(null,statement,connection);
        }
    }

    @Override
    public List<Things> query(Condition condition) {
        //JDBC 操作
        List<Things> things=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=this.dataSource.getConnection();

            StringBuilder sb=new StringBuilder();
            sb.append("select name,path,depth,file_type from things");
            sb.append(" where ");
            //采用模糊匹配
            //前模糊
            //后模糊
            //前后模糊
            sb.append(" name like  '"+condition.getName()).append("%'");
                if(condition.getFileType()!=null){
                FileType fileType=FileType.lookUpByName(condition.getFileType());

                sb.append(" and file_type='"+fileType.name()+"'");
            }
            sb.append("order by depth").append(condition.getOrderByDepthAsc() ? "asc" :"desc");
                sb.append("limit").append(condition.getLimit());
            System.out.println(sb.toString());
            statement=connection.prepareStatement(sb.toString());
            resultSet=statement.executeQuery();

            //处理结果集
            while(resultSet.next()){
                //将数据库记录转换为things
                Things things1=new Things();
                things1.setName(resultSet.getString("name"));
                things1.setPath(resultSet.getString("path"));
                things1.setDepth(resultSet.getInt("depth"));
                things1.setFileType(FileType.lookUpByName(resultSet.getString("file_type")));
                things.add(things1);

            }        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            releaseResource(resultSet,statement,connection);
        }
        return things;




    }
    //代码重构
    private void releaseResource(ResultSet resultSet,PreparedStatement statement,Connection connection){
       if(resultSet!=null){
           try {
               resultSet.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
