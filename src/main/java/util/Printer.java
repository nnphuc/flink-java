package util;

import org.apache.flink.table.api.TableColumn;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.utils.PrintUtils;
import org.apache.flink.types.Row;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.flink.table.utils.PrintUtils.rowToString;

public class Printer {
    public static void printAsTableauForm(TableSchema tableSchema, Row row, PrintWriter printWriter) {
        printAsTableauForm(tableSchema, row, printWriter, 30, "(NULL)", false, false);
    }

    public static void printAsTableauForm(TableSchema tableSchema, Row row,
                                          PrintWriter printWriter, int maxColumnWidth, String nullColumn,
                                          boolean deriveColumnWidthByType, boolean printRowKind) {
        List<TableColumn> columns = tableSchema.getTableColumns();
        String[] columnNames = (String[]) columns.stream().map(TableColumn::getName).toArray((x$0) -> {
            return new String[x$0];
        });
        if (printRowKind) {
            columnNames = (String[]) Stream.concat(Stream.of("op"), Arrays.stream(columnNames)).toArray((x$0) -> {
                return new String[x$0];
            });
        }

        int[] colWidths;
        if (deriveColumnWidthByType) {
            colWidths = PrintUtils.columnWidthsByType(columns, maxColumnWidth, nullColumn, printRowKind ? "op" : null);
        } else {
            List<org.apache.flink.types.Row> rows = new ArrayList();
            List<String[]> content = new ArrayList();
            rows.add(row);
            content.add(rowToString(row, nullColumn, printRowKind));

            colWidths = columnWidthsByContent(columnNames, content, maxColumnWidth);
        }

        String borderline = PrintUtils.genBorderLine(colWidths);
        printWriter.println(borderline);
        PrintUtils.printSingleRow(colWidths, columnNames, printWriter);
        printWriter.println(borderline);
        printWriter.flush();


        String[] cols = rowToString(row, nullColumn, printRowKind);
        PrintUtils.printSingleRow(colWidths, cols, printWriter);

        printWriter.flush();
    }

    private static int[] columnWidthsByContent(String[] columnNames, List<String[]> rows, int maxColumnWidth) {
        int[] colWidths = Stream.of(columnNames).mapToInt(String::length).toArray();
        Iterator var4 = rows.iterator();

        while (var4.hasNext()) {
            String[] row = (String[]) var4.next();

            for (int i = 0; i < row.length; ++i) {
                colWidths[i] = Math.max(colWidths[i], PrintUtils.getStringDisplayWidth(row[i]));
            }
        }

        for (int i = 0; i < colWidths.length; ++i) {
            colWidths[i] = Math.min(colWidths[i], maxColumnWidth);
        }

        return colWidths;
    }
}
