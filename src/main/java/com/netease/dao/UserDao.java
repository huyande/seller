package com.netease.dao;

import com.netease.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author zhanglbjames@163.com
 * @version Created on 18-3-18.
 */
@Component //不加能正常运行，但是IDEA会提示报错
@Mapper // 映射为Mapper接口
public interface UserDao {
    String TABLE_NAME="user";
    String INSERT_FIELDS = " name, password, type ";
    String SELECT_FIELDS = " id, name, password, type ";

    //方式一： User类对象属性到user表列的映射
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "password", column = "password"),
            @Result(property = "type", column = "type")
    })

    // 方式二：将参数对象的参数与user表的列进行映射
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{type})"})
    int addUser(User user);

    // 方式三：使用@Param注解 注解的内的字符"id"与列名一致，参数名称(int id)不限
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(@Param("id") int id);

    // 方法四：当然不使用方法一二，也可以，则直接根据参数名和列名进行对应
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(@Param("id") int id,@Param("password") String password);
}
