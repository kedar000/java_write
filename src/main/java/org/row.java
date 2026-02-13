package org;

public class RowValidator {

    private static final int HEADER_ROW_INDEX = 3;   // 4th row
    private static final int DATA_START_ROW = 4;     // data starts below header
    private static final int START_COLUMN_INDEX = 1; // column B

    public void validate(ValidationContext context, ValidationResult result) {

        Sheet sheet = context.dataSheet;
        Workbook workbook = context.workbook;

        CellStyle errorStyle = CellStyleToRed.createErrorCellStyle(workbook);
        Map<String, ColumnRule> schema = SchemaRegistry.userSchema();

        Row headerRow = sheet.getRow(HEADER_ROW_INDEX);

        // Map header -> column index
        Map<String, Integer> columnIndexMap = new HashMap<>();

        for (int colIdx = START_COLUMN_INDEX; colIdx < headerRow.getLastCellNum(); colIdx++) {
            Cell cell = headerRow.getCell(colIdx);
            if (cell == null) continue;

            String header = cell.toString().trim().toLowerCase();
            if (!header.isEmpty()) {
                columnIndexMap.put(header, colIdx);
            }
        }

        // Iterate data rows
        for (int rowIdx = DATA_START_ROW; rowIdx <= sheet.getLastRowNum(); rowIdx++) {

            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;
            if (isRowEmpty(row)) continue;

            boolean rowHasError = false;

            ParticipantRowDto dto = new ParticipantRowDto();

            for (String columnName : schema.keySet()) {

                int colIdx = columnIndexMap.get(columnName);
                ColumnRule rule = schema.get(columnName);

                Cell cell = row.getCell(colIdx);
                String value = (cell == null) ? "" : cell.toString().trim();

                // Required validation
                if (rule.isRequired() && value.isEmpty()) {
                    markError(cell, row, colIdx, errorStyle, sheet, workbook, rule.getRequiredMessage());
                    context.hasDataError = true;
                    rowHasError = true;
                    continue;
                }

                // Regex validation
                if (!value.isEmpty() && !value.matches(rule.getRegex())) {
                    markError(cell, row, colIdx, errorStyle, sheet, workbook, rule.getInvalidErrorMessage());
                    context.hasDataError = true;
                    rowHasError = true;
                    continue;
                }

                // Populate DTO only if valid
                if (!rowHasError) {
                    mapValueToDto(dto, columnName, value);
                }
            }

            // If entire row valid → store it
            if (!rowHasError) {
                result.getValidRows().add(dto);
            }
        }
    }