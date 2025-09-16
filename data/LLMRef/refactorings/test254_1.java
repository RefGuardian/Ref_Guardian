public class test254 {

    void logReport(boolean isCrashReport) {
        ConditionEvaluationReport report = this.reportSupplier.get();
        if (report == null) {
            this.logger.info("Unable to provide the condition evaluation report");
            return;
        }
        if (!report.getConditionAndOutcomesBySource().isEmpty()) {
            logConditionEvaluationReport(isCrashReport, report);
        }
    }

    private void logConditionEvaluationReport(boolean isCrashReport, ConditionEvaluationReport report) {
        if (this.logLevel.equals(LogLevel.INFO)) {
            if (this.logger.isInfoEnabled()) {
                this.logger.info(new ConditionEvaluationReportMessage(report));
            } else if (isCrashReport) {
                logMessage("info");
            }
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(new ConditionEvaluationReportMessage(report));
            } else if (isCrashReport) {
                logMessage("debug");
            }
        }
    }

}
