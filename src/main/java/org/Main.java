package org;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        IO.println(String.format("Hello and welcome!"));

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            IO.println("i = " + i);
        }
    }
}










public static void resetCell(Cell cell, Workbook workbook) {

    if (cell == null) return;

    /* ---------------- REMOVE COMMENT ---------------- */

    Comment comment = cell.getCellComment();
    if (comment != null) {
        Sheet sheet = cell.getSheet();
        Drawing<?> drawing = sheet.getDrawingPatriarch();

        // Remove the visual comment box
        if (drawing != null) {
            drawing.removeShape(comment);
        }

        // Detach from the cell
        cell.removeCellComment();
    }

    /* ---------------- RESET STYLE ---------------- */

    // Create a clean style (Excel default look)
    CellStyle defaultStyle = workbook.createCellStyle();

    // No fill color
    defaultStyle.setFillPattern(FillPatternType.NO_FILL);

    // Default font
    Font defaultFont = workbook.createFont();
    defaultFont.setBold(false);
    defaultFont.setColor(IndexedColors.BLACK.getIndex());
    defaultFont.setFontHeightInPoints((short) 11); // Excel default

    defaultStyle.setFont(defaultFont);

    // Remove borders
    defaultStyle.setBorderTop(BorderStyle.NONE);
    defaultStyle.setBorderBottom(BorderStyle.NONE);
    defaultStyle.setBorderLeft(BorderStyle.NONE);
    defaultStyle.setBorderRight(BorderStyle.NONE);

    // Apply
    cell.setCellStyle(defaultStyle);
}








public void addComment(Cell cell, Sheet sheet, Workbook workbook, String message) {

    System.out.println("Hover comment called");

    /* ---------- REMOVE OLD COMMENT ---------- */
    Comment oldComment = cell.getCellComment();
    if (oldComment != null) {
        cell.removeCellComment();
    }

    /* ---------- REUSE DRAWING LAYER ---------- */
    Drawing<?> drawing = sheet.getDrawingPatriarch();
    if (drawing == null) {
        drawing = sheet.createDrawingPatriarch();
    }

    CreationHelper helper = workbook.getCreationHelper();

    /* ---------- COMMENT POSITION ---------- */
    ClientAnchor anchor = helper.createClientAnchor();
    anchor.setCol1(cell.getColumnIndex());
    anchor.setCol2(cell.getColumnIndex() + 2);
    anchor.setRow1(cell.getRowIndex());
    anchor.setRow2(cell.getRowIndex() + 3);

    /* ---------- CREATE COMMENT ---------- */
    Comment comment = drawing.createCellComment(anchor);
    comment.setString(helper.createRichTextString(message));

    /* ---------- ATTACH TO CELL ---------- */
    cell.setCellComment(comment);
}
