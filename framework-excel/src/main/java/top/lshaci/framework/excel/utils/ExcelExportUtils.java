package top.lshaci.framework.excel.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import top.lshaci.framework.excel.entity.ExportSheetParam;
import top.lshaci.framework.excel.exception.ExcelHandlerException;
import top.lshaci.framework.excel.handle.ExportHandler;
import top.lshaci.framework.excel.service.ExportService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * <p>Excel 导出工具</p><br>
 *
 * <b>1.0.6:</b>添加自定义导出业务实现
 *
 * @author lshaci
 * @since 1.0.2
 * @version 1.0.6
 */
@Slf4j
public class ExcelExportUtils {
	
	/**
	 * 根据导出实体类信息和数据条数导出Excel WorkBook
	 *
	 * @param cls 导出实体类信息
	 * @param datas 需要导出的数据
	 * @param os 需要将Excel工作簿写到的输出流
	 */
	public static <E> void export(Class<E> cls, List<E> datas, OutputStream os) {
		export(cls, datas, os, null, null);
	}

	/**
	 * 根据导出实体类信息和数据条数导出Excel WorkBook
	 *
	 * @param cls 导出实体类信息
	 * @param datas 需要导出的数据
	 * @param os 需要将Excel工作簿写到的输出流
	 * @param exportService 生成Excel的业务类
	 */
	public static <E> void export(Class<E> cls, List<E> datas, OutputStream os, ExportService exportService) {
		export(cls, datas, os, null, exportService);
	}
	
	/**
	 * 根据导出实体类信息和数据条数导出Excel WorkBook
	 *
	 * @param cls 导出实体类信息
	 * @param datas 需要导出的数据
	 * @param sheetParam sheet中的参数
	 * @param os 需要将Excel工作簿写到的输出流
	 */
	public static <E> void export(Class<E> cls, List<E> datas, OutputStream os, ExportSheetParam sheetParam) {
		export(cls, datas, os, sheetParam, null);
	}

	/**
	 * 根据导出实体类信息和数据条数导出Excel WorkBook
	 *
	 * @param cls 导出实体类信息
	 * @param datas 需要导出的数据
	 * @param sheetParam sheet中的参数
	 * @param exportService 生成Excel的业务类
	 * @param os 需要将Excel工作簿写到的输出流
	 */
	public static <E> void export(Class<E> cls, List<E> datas, OutputStream os, ExportSheetParam sheetParam, ExportService exportService) {
		try (
				Workbook workbook = ExportHandler.export(cls, datas, sheetParam, exportService)
		) {
			workbook.write(os);
		} catch (IOException e) {
			log.error("导出Excel工作簿时发生错误", e);
			throw new ExcelHandlerException("导出Excel工作簿时发生错误", e);
		}
	}

}
