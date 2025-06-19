package com.izertis.techtestelliot.adapters.in.rest.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemDetail(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        List<Violation> violations // nullable
) {
    public record Violation(String field, String message) {}

    public static ProblemDetail withoutViolations(String type, String title, int status, String detail, String instance) {
        return new ProblemDetail(type, title, status, detail, instance, null);
    }

    public static ProblemDetail withViolations(String type, String title, int status, String detail, String instance, List<Violation> violations) {
        return new ProblemDetail(type, title, status, detail, instance, violations);
    }
}