package one.auditfinder.server.common;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.auditfinder.server.statics.Values;


public class ExcelUtils {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	public static PropertyDescriptor[] getPropertyTypes( Object obj , String[] lst) throws Exception{
		PropertyDescriptor[] arrayp = new PropertyDescriptor[ lst.length];
		for(int i = 0; i< lst.length; i++) {
			String s = lst[i];
			if(s == null) {
				arrayp[i] = null;
				continue;
			}
			arrayp[i] = PropertyUtils.getPropertyDescriptor(obj, s);
		}
		return arrayp;
	}
	
	public static <T extends Object> List<T> readExcel( Class<T> clazz, InputStream is, String[] properties, int[] types ,int startRow) {
		return readExcel(clazz, is, properties, types, 0, startRow);
	}
	
	public static <T extends Object> List<T> readExcel( Class<T> clazz, InputStream is, String[] properties, int[] types , int sheetNo ,int startRow) {
		int cRow = startRow;
		List<T> lst = null;
		if( properties.length != types.length)
			return null;
		
		try {
			Workbook w = WorkbookFactory.create(is);
			Sheet s = w.getSheetAt(sheetNo);		
			Row r = s.getRow(cRow++);
			T itm = clazz.newInstance();
			if(r != null) {
				lst = new ArrayList<T>();
				do {
					for( int i = 0; i< properties.length; i++){
						if(properties[i] == null) continue;
						Cell c = r.getCell(i);
						if(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) continue;
						
						int ptype = types[i];
						if( ptype == Values.ID_TYPE_STRING) {
							if(c.getCellType() == Cell.CELL_TYPE_NUMERIC)
								PropertyUtils.setSimpleProperty(itm, properties[i], String.valueOf(c.getNumericCellValue()));
							else
								PropertyUtils.setProperty(itm, properties[i], c.getStringCellValue());
						} else if ( ptype == Values.ID_TYPE_INT ) {
							if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setSimpleProperty(itm, properties[i], (int)Math.round( c.getNumericCellValue()));
							} else {
								PropertyUtils.setProperty(itm, properties[i], Integer.parseInt(c.getStringCellValue()));
							}
						} else if ( ptype == Values.ID_TYPE_LONG ) {
							if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setSimpleProperty(itm, properties[i], Math.round( c.getNumericCellValue()));
							} else {
								PropertyUtils.setSimpleProperty(itm, properties[i], Long.parseLong(c.getStringCellValue()));
							}
						} else if ( ptype == Values.ID_TYPE_DATE ) { 
							PropertyUtils.setSimpleProperty(itm, properties[i],  c.getDateCellValue());
						} else if ( ptype == Values.ID_TYPE_BOOLEAN ) {
							PropertyUtils.setSimpleProperty(itm, properties[i],  c.getBooleanCellValue());
						} else if ( ptype == Values.ID_TYPE_DOUBLE ) {
							if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setSimpleProperty(itm, properties[i], c.getNumericCellValue());
							} else {
								PropertyUtils.setSimpleProperty(itm, properties[i], Double.parseDouble(c.getStringCellValue()));
							}
						} else if ( ptype == Values.ID_TYPE_FLOAT ) {
							if(c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setSimpleProperty(itm, properties[i], (float)c.getNumericCellValue());
							} else {
								PropertyUtils.setSimpleProperty(itm, properties[i], Float.parseFloat(c.getStringCellValue()));
							}
						}
					}
					lst.add(itm);
					itm = clazz.newInstance();
					r = s.getRow(cRow++);
				} while( r != null);
			}
			return lst;
		}catch (Exception e) {
			log.error( " Excel Read Error : " ,e);
		}
		return null;
	}
	public static <T extends Object> List<T> readExcel(Class<T> clazz, InputStream is, String[] properties,  int startRow) {
		return readExcel(clazz, is, properties, 0, startRow);
	}
		
	public static <T extends Object> List<T> readExcel(Class<T> clazz, InputStream is, String[] properties, int sheetNo, int startRow) {
		int cRow = startRow;
		List<T> lst =null;
		try {	
			Workbook w = WorkbookFactory.create(is);
			Sheet s = w.getSheetAt(sheetNo);		
			Row r = s.getRow(cRow++);
			T itm = clazz.newInstance();
			PropertyDescriptor[] lstp = getPropertyTypes(itm, properties);
			if(r != null) {
				lst = new ArrayList<T>();
				do {					
					for( int i = 0; i< properties.length; i++){
						if(properties[i] == null) continue;
						Cell c = r.getCell(i);
						if( c == null) continue;
						int cellType = c.getCellType();
						if(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) continue;
						PropertyDescriptor pd = lstp[i];
						if( pd.getPropertyType() == String.class) {
							if( cellType == Cell.CELL_TYPE_NUMERIC) {
								if(c.toString().contains("월")){ //엑셀에서 날짜 입력하면 제대로 안들어와서 변환
									String[] dateResult = c.toString().split("-");
									String year = dateResult[2].toString();
									String month = dateResult[1].substring(0, dateResult[1].length()-1).toString();
									String day = dateResult[0].toString();
									
									if(month.length() == 1){
										StringBuilder mstb = new StringBuilder();
										month = mstb.append("0").append(month).toString();
									}
									StringBuilder stb = new StringBuilder();
									stb.append(year).append("-").append(month).append("-").append(day);
									PropertyUtils.setProperty(itm, properties[i], stb.toString());
								}else{
									PropertyUtils.setProperty(itm, properties[i], String.valueOf(c.getNumericCellValue()));
								}
							} else { 
								PropertyUtils.setProperty(itm, properties[i], c.getStringCellValue());
							}
						} else if( pd.getPropertyType() == int.class ) {
							if(cellType == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setProperty(itm, properties[i], (int)Math.round( c.getNumericCellValue()));
							} else {
								PropertyUtils.setProperty(itm, properties[i], Integer.parseInt(c.getStringCellValue()));
							}
						} else if ( pd.getPropertyType() == long.class ) {
							if(cellType == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setProperty(itm, properties[i], Math.round( c.getNumericCellValue()));
							} else {
								PropertyUtils.setProperty(itm, properties[i], Long.parseLong(c.getStringCellValue()));
							}
						} else if ( pd.getPropertyType() == Date.class ) { 
							PropertyUtils.setSimpleProperty(itm, properties[i],  c.getDateCellValue());
						} else if ( pd.getPropertyType() == boolean.class ) {
							PropertyUtils.setSimpleProperty(itm, properties[i],  c.getBooleanCellValue());
						} else if ( pd.getPropertyType() == double.class ) {
							if(cellType == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setProperty(itm, properties[i], c.getNumericCellValue());
							} else {
								PropertyUtils.setProperty(itm, properties[i], Double.parseDouble(c.getStringCellValue()));
							}
						} else if ( pd.getPropertyType() == float.class ) {
							if(cellType == Cell.CELL_TYPE_NUMERIC) {
								PropertyUtils.setProperty(itm, properties[i], (float)c.getNumericCellValue());
							} else {
								PropertyUtils.setProperty(itm, properties[i], Float.parseFloat(c.getStringCellValue()));
							}
						}
					}
					lst.add(itm);
					itm = clazz.newInstance();
					r = s.getRow(cRow++);
				}while( r != null);
			}
			return lst;
		}catch (Exception e) {
			log.error( " Excel Read Error : " ,e);
		}
		return null;
	}
	
	
	@SuppressWarnings("resource")
	public static <T extends Object> boolean writeExcel(List<T> lst, InputStream is, OutputStream os, String[] properties, String sheetName, int sheetNo, String[] headNames, int startRow) {
		try {
			XSSFWorkbook w = null;
			if( is != null)
		    	w = new XSSFWorkbook(is);
			else 
				w = new XSSFWorkbook();
			XSSFSheet s = null;
			if( sheetNo < 0) {
				if( sheetName == null)
					s = w.createSheet();
				else 
					s = w.createSheet(sheetName);
			} else {
				if( sheetName != null){
					s = w.getSheet(sheetName);
					if( s == null )
						s = w.createSheet(sheetName);
				} else { 
					s = w.getSheetAt(sheetNo);
				}
			}
		    int cRow = startRow;
		    if( headNames != null) {
		    	Row r = s.getRow(cRow);
	    		if( r == null) {
	    			r = s.createRow(cRow);
	    		}
		    	for( int i =0; i < headNames.length; i++) {
		    		String nm = headNames[i];
		    		if( nm == null) continue;
		    		Cell c = r.getCell(i);
		    		if(c == null) c = r.createCell(i);
		    		c.setCellValue(nm);
		    	}
		    	cRow++;
		    }
		    T obj = lst.get(0);
		    PropertyDescriptor[] arrayp = getPropertyTypes(obj, properties);
		    for( T itm : lst) {
		    	Row r = s.getRow(cRow);
	    		if( r == null) {
	    			r = s.createRow(cRow);
	    		}
	    		for( int i = 0; i< properties.length; i++) {
	    			PropertyDescriptor pd = arrayp[i];
	    			Cell c = r.getCell(i);
	    			if( c == null) c = r.createCell(i);
	    			if( pd.getPropertyType() == String.class) {
	    				c.setCellValue((String)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else if(pd.getPropertyType() == int.class) {
	    				c.setCellValue( ((Integer)PropertyUtils.getSimpleProperty(itm, properties[i])).floatValue());
	    			} else if( pd.getPropertyType() == long.class ) {
	    				c.setCellValue( ((Long)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if(pd.getPropertyType() == float.class) {
	    				c.setCellValue(((Float)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if(pd.getPropertyType() == double.class ) {
	    				c.setCellValue(((Double)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if( pd.getPropertyType() == Date.class) {
	    				c.setCellValue((Date)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else if( pd.getPropertyType() == boolean.class) {
	    				c.setCellValue((boolean)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else {
	    				c.setCellValue(PropertyUtils.getSimpleProperty(itm, properties[i]).toString());
	    			}
	    		}
	    		cRow++;
		    }
		    w.write(os);
		    return true;
		}catch(Exception e) {
			log.error( " Excel Write Error : " ,e);
		}
		return false;
	}
	
	public static <T extends Object> boolean writeExcel(List<T> lst, OutputStream os, String[] properties, String sheetName, String[] headNames, int startRow) {
		try {
		    @SuppressWarnings("resource")
		    XSSFWorkbook w = new XSSFWorkbook();
			XSSFSheet s =  w.createSheet(sheetName);
		    int cRow = startRow;
		    if( headNames != null) {
		    	Row r = s.createRow(cRow);
	    		
		    	for( int i =0; i < headNames.length; i++) {
		    		String nm = headNames[i];
		    		if( nm == null) continue;
		    		Cell c = r.createCell(i);
		    		c.setCellValue(nm);
		    	}
		    	cRow++;
		    }
		    T obj = lst.get(0);
		    PropertyDescriptor[] arrayp = getPropertyTypes(obj, properties);
		    for( T itm : lst) {
		    	Row r = s.createRow(cRow);
	    		for( int i = 0; i< properties.length; i++) {
	    			PropertyDescriptor pd = arrayp[i];
	    			Cell c = r.createCell(i);
	    			if( pd.getPropertyType() == String.class) {
	    				c.setCellValue((String)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else if(pd.getPropertyType() == int.class) {
	    				c.setCellValue( ((Integer)PropertyUtils.getSimpleProperty(itm, properties[i])).floatValue());
	    			} else if( pd.getPropertyType() == long.class ) {
	    				c.setCellValue( ((Long)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if(pd.getPropertyType() == float.class) {
	    				c.setCellValue(((Float)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if(pd.getPropertyType() == double.class ) {
	    				c.setCellValue(((Double)PropertyUtils.getSimpleProperty(itm, properties[i])).doubleValue());
	    			} else if( pd.getPropertyType() == Date.class) {
	    				c.setCellValue((Date)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else if( pd.getPropertyType() == boolean.class) {
	    				c.setCellValue((boolean)PropertyUtils.getSimpleProperty(itm, properties[i]));
	    			} else {
	    				c.setCellValue(PropertyUtils.getSimpleProperty(itm, properties[i]).toString());
	    			}
	    		}
	    		cRow++;
		    }
		    w.write(os);
		    return true;
		}catch(Exception e) {
			log.error( " Excel Write Error : " ,e);
		}
		return false;
	}
	
	
}
