package one.auditfinder.server.service.list;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.auditfinder.server.mapper.list.ListMapper;
import one.auditfinder.server.vo.list.ListVo;

@Service
public class ListService {
	
	@Autowired
	private ListMapper listMapper;
	
	public List<ListVo> getList(){
		return listMapper.getList();
	}
	
	public void updateList(ListVo vo) {
		listMapper.updateList(vo);
	}

}
