public class test254 {

    void logReport(boolean isCrashReport) {
        ConditionEvaluationReport report = this.reportSupplier.get();
        if (report == null) {
            this.logger.info("Unable to provide the condition evaluation report");
            return;
        }
        if (!report.getConditionAndOutcomesBySource().isEmpty()) {
            if (this.logLevel.equals(LogLevel.INFO)) {
                if (this.logger.isInfoEnabled()) {
                    logConditionEvaluationMessage(report, "info");
                }
                else if (isCrashReport) {
                    logMessage("info");
                }
            }
            else {
                if (this.logger.isDebugEnabled()) {
                    logConditionEvaluationMessage(report, "debug");
                }
                else if (isCrashReport) {
                    logMessage("debug");
                }
            }
        }
    }

    private void logConditionEvaluationMessage(ConditionEvaluationReport report, String level) {
        if ("info".equals(level)) {
            this.logger.info(new ConditionEvaluationReportMessage(report));
        } else {
            this.logger.debug(new ConditionEvaluationReportMessage(report));
        }
    }
}
