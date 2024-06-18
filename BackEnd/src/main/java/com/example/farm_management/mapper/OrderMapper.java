package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> list(String oid,
                     String customerid,
                     String gid,
                     String gname,
                     Integer amount,
                     Integer score);

    @Insert("insert into `order`(o_id, customer_id, g_id, g_name, amount, score)" +
            "values (#{oid},#{customerid},#{gid},#{gname},#{amount},#{score})")
    void insert(Order order);

    void delete(List<String> ids);

    void update(Order order);
}
