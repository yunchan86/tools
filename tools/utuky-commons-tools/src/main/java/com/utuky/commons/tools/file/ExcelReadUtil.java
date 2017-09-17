package com.utuky.commons.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelReadUtil {

	protected static String[] getSheetTitle(Sheet sheet) {
		String result[] = null;
		if (sheet == null)
			return result;
		Row row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		result = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			result[i] = getCellFormatValue(row.getCell(i));
		}
		return result;
	}

	public static String[] getTitle(InputStream is) {
		String result[] = null;
		Workbook wb = null;
		try {
			wb = ExcelUtil.build(is);
			Sheet sheet = wb.getSheetAt(0);
			result = getSheetTitle(sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取excel的所有sheet的标题
	 * 
	 * @param is
	 * @return
	 */
	public static String[][] getAllSheetTitle(InputStream is) {
		String[][] result = null;
		Workbook wb = null;
		try {
			wb = ExcelUtil.build(is);
			int sheetNum = wb.getNumberOfSheets();
			result = new String[sheetNum][];
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = wb.getSheetAt(i);
				result[i] = getSheetTitle(sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected static List<String[]> getSheetData(Sheet sheet) {
		List<String[]> result = null;
		try {
			int rowNum = sheet.getPhysicalNumberOfRows();
			Row row0 = sheet.getRow(0);
			int colNum = row0.getPhysicalNumberOfCells();
			for (int i = 1; i < rowNum; i++) {
				if (result == null)
					result = new LinkedList<String[]>();
				Row row = sheet.getRow(i);
				String[] rowData = new String[colNum];
				boolean b = false;
				for (int j = 0; j < colNum; j++) {
					rowData[j] = getCellFormatValue(row.getCell(j));
					if (StringUtils.isNotBlank(rowData[j]) && !b)
						b = true;
				}
				if (b)
					result.add(rowData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<Integer, List<String[]>> getAllSheetData(InputStream is) {
		Map<Integer, List<String[]>> result = null;
		Workbook wb = null;
		try {
			wb = ExcelUtil.build(is);
			int sheetNum = wb.getNumberOfSheets();
			if (sheetNum > 0)
				result = new HashMap<Integer, List<String[]>>();
			for (int i = 0; i < sheetNum; i++) {
				Sheet sheet = wb.getSheetAt(i);
				List<String[]> listdata = getSheetData(sheet);
				result.put(i, listdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String[]> getData(InputStream is) {
		List<String[]> result = null;
		Workbook wb = null;
		try {
			wb = ExcelUtil.build(is);
			Sheet sheet = wb.getSheetAt(0);
			result = getSheetData(sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_NUMERIC:
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// 方法1：这样子的data格式是带时分秒的：2016-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case Cell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	// private static String getDateCellValue(HSSFCell cell) {
	// String result = "";
	// try {
	// int cellType = cell.getCellType();
	// if (cellType == Cell.CELL_TYPE_NUMERIC) {
	// Date date = cell.getDateCellValue();
	// result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
	// + "-" + date.getDate();
	// } else if (cellType == Cell.CELL_TYPE_STRING) {
	// String date = getStringCellValue(cell);
	// result = date.replaceAll("[年月]", "-").replace("日", "").trim();
	// } else if (cellType == Cell.CELL_TYPE_BLANK) {
	// result = "";
	// }
	// } catch (Exception e) {
	// System.out.println("日期格式不正确!");
	// e.printStackTrace();
	// }
	// return result;
	// }

	// private static String getStringCellValue(HSSFCell cell) {
	// String strCell = "";
	// if (cell == null) {
	// return strCell;
	// }
	// switch (cell.getCellType()) {
	// case Cell.CELL_TYPE_STRING:
	// strCell = cell.getStringCellValue();
	// break;
	// case Cell.CELL_TYPE_NUMERIC:
	// strCell = String.valueOf(cell.getNumericCellValue());
	// break;
	// case Cell.CELL_TYPE_BOOLEAN:
	// strCell = String.valueOf(cell.getBooleanCellValue());
	// break;
	// case Cell.CELL_TYPE_BLANK:
	// strCell = "";
	// break;
	// default:
	// strCell = "";
	// break;
	// }
	// if (strCell == null || "".equals(strCell)) {
	// return "";
	// }
	// return strCell;
	// }

	public static void main(String[] args) {
		File file = null;
		InputStream fis = null;
		String excelFilePath = "F:\\222.xlsx";
		try {
			file = new File(excelFilePath);
			if (!file.exists()) {
				throw new RuntimeException("文件" + excelFilePath + "不存在");
			} else {
				fis = new FileInputStream(file);
				List<String[]> result = ExcelReadUtil.getData(fis);
				System.out.println(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
