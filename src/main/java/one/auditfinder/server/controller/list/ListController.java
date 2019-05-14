package one.auditfinder.server.controller.list;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import one.auditfinder.server.comps.IAuthUtils;
import one.auditfinder.server.service.list.ListService;
import one.auditfinder.server.vo.list.ListVo;

@Controller
public class ListController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAuthUtils authUtils;
	
	@Autowired
	private ListService listService;
	
	@RequestMapping(value="/cList/getList", produces="application/json")
	@ResponseBody
	public List<ListVo> getList(){
		if(log.isErrorEnabled()) {
			log.debug("Get List");
		}
		
		authUtils.checkLoginApi();
		
		return listService.getList();
	}
	
	@RequestMapping(value="/cList/updateList", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void updateList(@RequestBody ListVo vo) {
		if(log.isDebugEnabled()) {
			log.debug("Update List : {}", vo.getSeq());
		}
		listService.updateList(vo);
	}

}
