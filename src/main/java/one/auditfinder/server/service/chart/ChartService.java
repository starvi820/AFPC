package one.auditfinder.server.service.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.auditfinder.server.common.Pair;
import one.auditfinder.server.common.Triple;
import one.auditfinder.server.mapper.chart.ChartMapper;


@Service
public class ChartService {
	
	@Autowired
	private ChartMapper chartMapper;


	
	
	public List<Integer> getAutoCnt(int type , String sDate , String eDate){
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(chartMapper.checkCnt(type));
		lst.add(chartMapper.checkCntSearch(type, sDate, eDate));
		return lst;
	}
	
	public List<Integer> getCnt(){
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(chartMapper.checkCnt(1));
		lst.add(chartMapper.checkCnt(0));
		return lst;
	}
	
	public List<Integer> getCntSearch(String sDate, String eDate){
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(chartMapper.checkCntSearch(1, sDate, eDate ));
		lst.add(chartMapper.checkCntSearch(0, sDate, eDate ));
		return lst;
	}
	
	
	public List<Integer> getAgentCntByDate(){
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(chartMapper.cntCheckDateAgent());
		return lst;
	}
	
	public List<Integer> ModalGetAgentCntByDateSearch(String sDate , String eDate){
		List<Integer> lst = new ArrayList<>();
		lst.add(chartMapper.cntCheckDateAgent());
		lst.add(chartMapper.AgentCheckSearch(sDate, eDate));
		return lst;
	}
	
	public List<String> getCheckDate() {
		return chartMapper.getCheckDate();
	}
	
	
	public List<Integer> getResultCnt(){
		List<Integer> lst = new ArrayList<>();
		lst.add(chartMapper.cntResultSafty());
		lst.add(chartMapper.cntResultDanger());
		return lst;
	}
	
	public List<String> getTeam(){
		return chartMapper.getTeam();
	}
	
	
	public List<List<Integer>> getTeamResultCnt(){
		List<List<Integer>> lst = new ArrayList<>();
		lst.add(chartMapper.cntTeamAgent());
		lst.add(chartMapper.cntTeamSafty());
				
		return lst;
	}
	
	
	public List<Pair<Integer, Integer>> getResultCNT(){
		List<Pair<Integer, Integer>> list = new ArrayList<>();
		list.add(chartMapper.cntResultAll());
		
		return list;
		
	}
	
	
	

	public List<Triple<Object, Integer, Integer>> getResult(){
		
		List<Triple<Object, Integer, Integer>> result = new ArrayList<Triple<Object, Integer, Integer>>();
		Triple<Object, Integer, Integer> triple = new Triple<Object, Integer, Integer>();
		
		triple.setFirst("연구1팀");
		triple.setSecond(chartMapper.getResult(1));
		triple.setThird(chartMapper.getResult(0));
		
		
		result.add(triple);
		return result;
	}
		
	
	

}
