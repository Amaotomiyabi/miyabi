import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行
 *
 * @author miyabi
 * @date 2021-04-01-13-57
 * @since 1.0
 **/


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
/*        var s = """
                T_DATA_ATMOSPHERE_INFO
                t_data_dust_daily
                T_DATA_LAMPBLACK_COMPANY
                T_DATA_MAP_SITE
                T_DATA_NOISE_FUNCTION_INFO
                T_DATA_NOISE_GENE_INFO
                t_data_noise_region_info
                t_data_noise_road_info
                T_DATA_WATER_INFO
                T_DATA_WATER_MANUAL_INFO
                T_P_CATERING_COMPANY_INFO
                T_P_CATERING_OIL_SEPARATION
                T_P_CATERING_REGION_INFO
                T_P_CATERING_RUN_MAINTENANCE
                T_P_CATERING_SOLID_WASTE
                T_P_CATERING_SYN_COMPANY_INFO
                T_P_CATERING_WASTE_GAS_PURIFY
                T_P_EMPHASIS_ENTERPRISE
                T_P_ENFORCEMENT_PEOPLE
                T_P_ENFORCEMENT_RECORD
                T_P_INDUSTRY
                T_P_INDUSTRY_BOILER
                T_P_INDUSTRY_DEVICE
                T_P_INDUSTRY_ENERGY
                T_P_INDUSTRY_ORGANIC
                T_P_INDUSTRY_OTHER_GAS
                T_P_INDUSTRY_PRODUCT
                T_P_INDUSTRY_RISK_DEVICE
                T_P_INDUSTRY_RISK_MATERIAL
                T_P_INDUSTRY_RISK_WASTE
                T_P_INDUSTRY_SOLID_WASTE
                T_P_INDUSTRY_WATER_OUTFALL
                T_P_INSDUSTRY_MATERIAL
                T_P_INSPECTION_CANTEEN
                T_P_INSPECTION_CAR
                T_P_INSPECTION_COMPRESSION
                T_P_INSPECTION_INFO
                T_P_INSPECTION_MARKET
                T_P_OTTFF_EVENT
                T_P_PUNISHMENT_RECORD
                T_P_WAPP_FLOW_HP_MAINFORM
                T_P_WAPP_FLOW_HP_REGISTRATION
                T_P_WASTE_INFO
                T_P_WASTE_LICENCE_INFO
                T_P_WASTE_LICENCE_STEP
                T_P_WASTE_MONTHLY
                T_P_WASTE_MONTHLY_MANAGE
                T_P_WASTE_MONTHLY_PRODUCE
                T_P_WASTE_MONTHLY_RECEIVE
                T_P_WASTE_MONTHLY_STORAGE
                T_P_WASTE_MONTHLY_TRANSFER
                T_P_WASTE_TRANSFER
                T_P_WASTE_WATER_OUTPUT
                T_P_WRYDJ
                T_W_PLAN_CONTENT
                T_W_PLAN_EXECUTE
                T_W_PLAN_MONITOR
                T_W_PLAN_MONITOR_ACCOUNTS
                T_W_PLAN_MONITOR_CONTENT
                T_W_PLAN_MONITOR_D_P
                T_W_PLAN_MONITOR_FACTOR
                T_W_PLAN_OBJ
                T_W_SPECIAL_ACTION   
                """;*/
        var s = """
                T_DATA_COUNTY_DAILY_REAL
                T_DATA_INDOOR_DAILY
                """;
        var needTable = s.split("\n");
        Map<String, String> needTM = new HashMap<>();
        for (String s1 : needTable) {
            needTM.put(s1, s1);
        }
        var tableList = new ArrayList<List<List<String>>>();
        Class.forName("dm.jdbc.driver.DmDriver");
        var conn = DriverManager.getConnection("test",
                "test",
                "test");
        var metaData = conn.getMetaData();
        var tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        while (tables.next()) {
            var tableName = tables.getString("TABLE_NAME");
            if (!needTM.containsKey(tableName)) {
                continue;
            }
            var tableDesc = tables.getString("REMARKS");
            var columnList = new ArrayList<List<String>>();
            var tableMetaData = metaData.getColumns(null, null, tableName, "%");
            var primaryKeyResult = metaData.getPrimaryKeys(null, null, tableName);
            var primaryKey = "";
            while (primaryKeyResult.next()) {
                primaryKey = primaryKeyResult.getString("COLUMN_NAME");
            }
            while (tableMetaData.next()) {
                var column = new ArrayList<String>();
                column.add(tableName);
                column.add(tableDesc);
                var name = tableMetaData.getString("COLUMN_NAME");

                var desc = tableMetaData.getString("REMARKS");

                column.add(desc);
                column.add(name);
                column.add(desc);

                var dataType = tableMetaData.getString("TYPE_NAME");

                column.add(dataType);

                var columnSize = tableMetaData.getString("COLUMN_SIZE");

                column.add(columnSize);
                column.add(columnSize);

                var digits = tableMetaData.getString("DECIMAL_DIGITS");

                column.add(digits == null || digits.isBlank() ? "0" : digits);
                column.add("NO");
                column.add(name.equalsIgnoreCase(primaryKey) ? "YES" : "NO");
                column.add("NO");
                column.add("NO");
                column.add("1一级");
                column.add("2有条件共享");
                column.add("依据申请场景共享");
                column.add("0否");

                var isNullAble = tableMetaData.getString("IS_NULLABLE");
                var nullAble = tableMetaData.getString("NULLABLE");
                column.add("NO".equalsIgnoreCase(isNullAble)
                        || "columnNoNulls".equalsIgnoreCase(nullAble)
                        || name.equalsIgnoreCase(primaryKey) ? "0否" : "1是");
                column.add(name.equalsIgnoreCase(primaryKey) ? "1是" : "0否");
                column.add("1是");
                column.add("1是");
                column.add("");
                column.add("");
                column.add("");
                columnList.add(column);
            }
            tableList.add(columnList);
        }
        toExcel(tableList);
    }

    private static void toExcel(List<List<List<String>>> tables) throws IOException {
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet();
        var r = 0;
        for (int j = 0; j < tables.size(); j++) {
            for (List<String> table : tables.get(j)) {
                var row = sheet.createRow(r);
                r++;
                var i = 0;
                row.createCell(i).setCellValue(j + 1);
                i++;
                for (String c : table) {
                    var cell = row.createCell(i);
                    cell.setCellValue(c);
                    i++;
                }
            }
        }
        var file = new File("C:\\Users\\sss\\Desktop\\" + System.currentTimeMillis() + ".xlsx");
        file.createNewFile();
        var fos = new FileOutputStream(file);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}
