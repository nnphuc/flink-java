package util;

import org.apache.flink.table.api.TableColumn;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.utils.EncodingUtils;
import org.apache.flink.table.utils.PrintUtils;
import org.apache.flink.types.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.apache.flink.table.utils.PrintUtils.rowToString;

public class Printer {
    public static void printAsTableauForm(TableSchema tableSchema, Row row, LoggerManager logger) {
        printAsTableauForm(tableSchema, row, logger, 30, "(NULL)", false, false);
    }

    public static void printAsTableauForm(TableSchema tableSchema, Row row,
                                          LoggerManager logger, int maxColumnWidth, String nullColumn,
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
        logger.trace(borderline);
        printSingleRow(colWidths, columnNames, logger);
        logger.trace(borderline);


        String[] cols = rowToString(row, nullColumn, printRowKind);
        printSingleRow(colWidths, cols, logger);
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

    public static void printSingleRow(int[] colWidths, String[] cols, LoggerManager logger) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        int idx = 0;
        String[] var5 = cols;
        int var6 = cols.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String col = var5[var7];
            sb.append(" ");
            int displayWidth = PrintUtils.getStringDisplayWidth(col);
            if (displayWidth <= colWidths[idx]) {
                sb.append(EncodingUtils.repeat(' ', colWidths[idx] - displayWidth));
                sb.append(col);
            } else {
                sb.append(truncateString(col, colWidths[idx] - "...".length()));
                sb.append("...");
            }

            sb.append(" |");
            ++idx;
        }

        logger.trace(sb.toString());
    }

    private static String truncateString(String col, int targetWidth) {
        int passedWidth = 0;

        int i;
        for (i = 0; i < col.length(); ++i) {
            if (PrintUtils.isFullWidth(Character.codePointAt(col, i))) {
                passedWidth += 2;
            } else {
                ++passedWidth;
            }

            if (passedWidth > targetWidth) {
                break;
            }
        }

        String substring = col.substring(0, i);
        int lackedWidth = targetWidth - PrintUtils.getStringDisplayWidth(substring);
        if (lackedWidth > 0) {
            substring = EncodingUtils.repeat(' ', lackedWidth) + substring;
        }

        return substring;
    }
}
