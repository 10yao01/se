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
                     Integer amount,
                     Integer score);

    @Insert("insert into `order`(o_id, customer_id, g_id, amount, score)" +
            "values (#{oid},#{customerid},#{gid},#{amount},#{score})")
    void insert(Order order);

    void delete(List<String> ids);

    void update(Order order);
}
