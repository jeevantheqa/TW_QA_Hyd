package tw;

        	import java.io.FileInputStream;

            import java.io.FileOutputStream;

            import org.apache.poi.xssf.usermodel.XSSFCell;

        	import org.apache.poi.xssf.usermodel.XSSFRow;

        	import org.apache.poi.xssf.usermodel.XSSFSheet;

        	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

    public class ExcelUtils {

        		private static XSSFSheet ExcelWSheet;

        		private static XSSFWorkbook ExcelWBook;

        		private static XSSFCell Cell;

        		private static XSSFRow Row;


    		public static void setExcelFile(String Path,String SheetName) throws Exception {

       			try {


					FileInputStream ExcelFile = new FileInputStream(Path);

					// Access the required test data sheet

					ExcelWBook = new XSSFWorkbook(ExcelFile);

					ExcelWSheet = ExcelWBook.getSheet(SheetName);

					} catch (Exception e){

						throw (e);

					}

			}

    	    public static String getCellData(int RowNum, int ColNum) throws Exception{

       			try{

          			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

          			String CellData = Cell.getStringCellValue();

          			return CellData;

          			}catch (Exception e){

						return"";

          			}

		    }

    		public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{

       			try{

          			Row  = ExcelWSheet.getRow(RowNum);

					Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);

					if (Cell == null) {

						Cell = Row.createCell(ColNum);

						Cell.setCellValue(Result);

						} else {

							Cell.setCellValue(Result);

						}

          				FileOutputStream fileOut = new FileOutputStream(".\\src\\test_data\\testdata.xlsx");

          				ExcelWBook.write(fileOut);

          				fileOut.flush();

 						fileOut.close();

						}catch(Exception e){

							throw (e);

					}

				}

	}