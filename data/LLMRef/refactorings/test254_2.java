public class test254 {

    void logReport(boolean isCrashReport) {
    		ConditionEvaluationReport report = this.reportSupplier.get();
    		if (report == null) {
    			logUnableToProvideReport();
    			return;
    		}
    		if (!report.getConditionAndOutcomesBySource().isEmpty()) {
    			if (shouldLogInfo()) {
    				logInfo(report);
    			}
    			else {
    				logDebug(report);
    			}
    		}
    	}

    private void logUnableToProvideReport() {
        this.logger.info("Unable to provide the condition evaluation report");
    }

    private boolean shouldLogInfo() {
        return this.logLevel.equals(LogLevel.INFO) && this.logger.isInfoEnabled();
    }

    private void logInfo(ConditionEvaluationReport report) {
        this.logger.info(new ConditionEvaluationReportMessage(report));
    }

    private void logDebug(ConditionEvaluationReport report) {
        this.logger.debug(new ConditionEvaluationReportMessage(report));
    }

    private void logMessage(String level) {
        if (level.equals("info")) {
            logInfo(new ConditionEvaluationReport(null));
        } else if (level.equals("debug")) {
            logDebug(new ConditionEvaluationReport(null));
        }
    }
}
