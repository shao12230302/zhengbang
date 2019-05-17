package com.zb.byb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



@Mapper
public interface WxAppTokenMapper {
    @Update("update T_APP_WeiXinToken set FTokenId = #{token} where FAPPID = #{appId}")
    void updateWxAccessToken(@Param("appId")String appId, @Param("token")String token);

    @Select("select fid from T_APP_WeiXinToken where FAPPID = #{appId}")
    String getAppConfig(@Param("appId")String appId);

    @Update("insert into T_APP_WeiXinToken (fid,FAPPID,FTokenId) values ('Va4Arehrhe7474ugR',#{appId},#{token})")
    void addWxAccessToken(@Param("appId")String appId, @Param("token")String token, @Param("appSecret")String appSecret);
}
