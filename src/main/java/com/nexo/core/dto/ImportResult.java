package com.nexo.core.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard DTO for bulk import operations across all Nexo modules.
 * Reports success count, failure count, and per-item errors.
 *
 * @param <T> the type of successfully imported items
 */
public class ImportResult<T> {

    private int totalProcessed;
    private int successCount;
    private int failureCount;
    private List<T> successfulItems;
    private List<ImportError> errors;

    public ImportResult() {
        this.successfulItems = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    public void addSuccess(T item) {
        this.successfulItems.add(item);
        this.successCount++;
        this.totalProcessed++;
    }

    public void addError(int row, String field, String message, Object rawValue) {
        this.errors.add(new ImportError(row, field, message, rawValue));
        this.failureCount++;
        this.totalProcessed++;
    }

    // --- Getters & Setters ---

    public int getTotalProcessed() {
        return totalProcessed;
    }

    public void setTotalProcessed(int totalProcessed) {
        this.totalProcessed = totalProcessed;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public List<T> getSuccessfulItems() {
        return successfulItems;
    }

    public void setSuccessfulItems(List<T> successfulItems) {
        this.successfulItems = successfulItems;
    }

    public List<ImportError> getErrors() {
        return errors;
    }

    public void setErrors(List<ImportError> errors) {
        this.errors = errors;
    }

    // --- Nested ---

    public static class ImportError {
        private int row;
        private String field;
        private String message;
        private Object rawValue;

        public ImportError() {
        }

        public ImportError(int row, String field, String message, Object rawValue) {
            this.row = row;
            this.field = field;
            this.message = message;
            this.rawValue = rawValue;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getRawValue() {
            return rawValue;
        }

        public void setRawValue(Object rawValue) {
            this.rawValue = rawValue;
        }
    }
}
