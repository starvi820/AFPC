package one.auditfinder.server.mapper.list;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import one.auditfinder.server.vo.list.ListVo;

public interface ListMapper {
	
	final String GET_LIST = "select seq, listName, listScore, listLevel, listContent, listSolution, listUseYn from af_check_list";
	
	@Select(GET_LIST)
	List<ListVo> getList();
	
	final String UPDATE_LIST = "update af_check_list set listName = #{listName}, listContent = #{listContent}, listSolution = #{listSolution}, listUseYn = #{listUseYn} where seq = #{seq}";
	
	@Update(UPDATE_LIST)
	void updateList(ListVo vo);

}
