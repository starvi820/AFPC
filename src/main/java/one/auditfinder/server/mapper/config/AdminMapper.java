package one.auditfinder.server.mapper.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.config.AdminVo;

public interface AdminMapper {
	
	final String SELECT_ADMIN_COUNT = "select count(seq) from af_admin";
	
	final String SELECT_ADMIN = "select seq, id, name, email, tel, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate from af_admin order by seq desc limit #{num} offset #{offset}";

	final String SELECT_ADMIN_BYSEQ = "select seq, id, pw, name, email, tel, date_format(regDate, '%Y-%m-%d %H:%m:%s') regDate from af_admin where seq = #{seq}";
	
	@Select(SELECT_ADMIN_COUNT)
	int selectAdminCount();
	
	@Select(SELECT_ADMIN)
	List<AdminVo> selectAdmin(@Param("num") int num, @Param("offset") int offset);
	
	@Select(SELECT_ADMIN_BYSEQ)
	AdminVo selectAdminBySeq(@Param("seq") int seq);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.AdminMapperProvider.class, method="adminSearchCnt")
	int adminSearchCnt(Map<String, Object> map);
	
	@SelectProvider(type=one.auditfinder.server.mapper.config.AdminMapperProvider.class, method="adminSearch")
	List<AdminVo> adminSearch(Map<String, Object> map);
	
	final String ID_CHK = "select count(*) from af_admin where id = #{id}";
	
	@Select(ID_CHK)
	int idChk(@Param("id") String id);
	
	final String INSERT_ADMIN = "insert into af_admin(id, name, pw, email, tel, regDate) values(#{id}, #{name}, #{pw}, #{email}, #{tel}, now())";
	
	final String DELETE_ADMIN = "delete from af_admin where seq = #{seq}";
	
	final String UPDATE_ADMIN = "update af_admin set name = #{name}, pw = #{pw}, email = #{email}, tel = #{tel} where seq = #{seq}";

	@Insert(INSERT_ADMIN)
	void insertAdmin(AdminVo vo);
	
	@Delete(DELETE_ADMIN)
	void deleteAdmin(@Param("seq") int seq);
	
	@Update(UPDATE_ADMIN)
	void updateAdmin(AdminVo vo);
}
