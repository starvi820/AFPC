package one.auditfinder.server.controller.chart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import one.auditfinder.server.common.Triple;
import one.auditfinder.server.service.chart.ChartService;
import one.auditfinder.server.vo.common.CommonResult;

@Controller
public class ChartController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ChartService chartService;
	
	
	@RequestMapping(value = "/cChart/AutoCnt",produces = "application/json")
	@ResponseBody
	public List<Integer> cntChartAutoAndNonAuto() {
		if(log.isDebugEnabled())
			log.debug("Count Chart Auto");
		
		return chartService.getCnt();
	}
	
	
	@RequestMapping(value = "/cChart/ResultCnt",produces = "application/json")
	@ResponseBody
	public List<Integer> cntResultSaftyAndDanger(){
		if(log.isDebugEnabled())
			log.debug("Count Result");
		return chartService.getResultCnt();
	}
	
	@RequestMapping(value = "/cChart/cntAgentByDate",produces = "application/json")
	@ResponseBody
	public List<Integer> cntAgentByDate(){
		if(log.isDebugEnabled())
			log.debug("Count Agent by Date");
		
		return chartService.getAgentCntByDate();
		
	}
	
	@RequestMapping(value = "/cChart/cntAgentSearchByDate/{sDate}/{eDate}" , produces = "application/json" )
	@ResponseBody
	public List<Integer> modalAgentDateSearch(@Param("sDate") String sDate, @Param("eDate") String eDate){
		if(log.isDebugEnabled())
			log.debug("Modal Agent Search by Date");
		return chartService.ModalGetAgentCntByDateSearch(sDate, eDate);
	}
	
	@RequestMapping(value = "/cChart/getCheckDate",produces="application/json")
	@ResponseBody
	public List<String> getCheckDate() {
		if(log.isDebugEnabled())
			log.debug("Get CheckDate");
		
		return chartService.getCheckDate();
		
	}
	
	@RequestMapping(value = "/cChart/getTeam",produces = "application/json")
	@ResponseBody
	public List<String> getTeam(){
		if(log.isDebugEnabled())
			log.debug("Get Team Name");
		
		return chartService.getTeam();
		
	}
	
	@RequestMapping(value = "/cChart/getTeamResult",produces = "application/json")
	@ResponseBody
	public List<List<Integer>> getTeamResultCnt(){
		if(log.isDebugEnabled())
			log.debug("Get Team Agent Result Count");
		
		return chartService.getTeamResultCnt();
	}
	
	@RequestMapping(value = "/cChart/getResult1", produces = "application/json")
	@ResponseBody
	public List<Triple<Object, Integer, Integer>> getResult1(){
		if(log.isDebugEnabled())
			log.debug("Get Team Agent Result Count");
		
		return chartService.getResult();
	}
	
	@RequestMapping(value = "/cChart/getResultCnt/{sDate}/{eDate}",produces = "application/json")
	@ResponseBody
	public List<Integer> getResultCnt(@Param("sDate")String sDate,@Param("eDate")String eDate){
		if(log.isDebugEnabled())
			log.debug("Get Result Count");
		
		return chartService.getCnt();
		
	}
	
	

}
