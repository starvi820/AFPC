package one.auditfinder.server.mapper.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.info.InfoVo;

public interface InfoMapper {
	
	final String SELECT_INFO_COUNT = "select count(seq) cnt from af_info";
	
	final String SELECT_INFO = "select seq, af_admin_id, title, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate, content from af_info order by seq desc limit #{num} offset #{offset}";
	
	@Select(SELECT_INFO_COUNT)
	int selectInfoCnt();
	
	@Select(SELECT_INFO)
	List<InfoVo> selectInfo(@Param("num") int num, @Param("offset") int offset);
	
	@SelectProvider(type=one.auditfinder.server.mapper.info.InfoMapperProvider.class, method="infoSearchCnt")
	int infoSearchCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.info.InfoMapperProvider.class, method="infoSearch")
	List<InfoVo> searchInfo(Map<String, Object> map);
	
	final String INSERT_INFO = "insert into af_info(af_admin_id, title, regDate, content) values(#{af_admin_id}, #{title}, now(), #{content})";
	
	final String UPDATE_INFO = "update af_info set title = #{title}, content = #{content} where seq = #{seq}";
	
	final String DELETE_INFO = "delete from af_info where seq = #{seq}";
	
	@Insert(INSERT_INFO)
	void insertInfo(InfoVo vo);
	
	@Update(UPDATE_INFO)
	void updateInfo(InfoVo vo);
	
	@Delete(DELETE_INFO)
	void deleteInfo(@Param("seq") int seq);
	
}
