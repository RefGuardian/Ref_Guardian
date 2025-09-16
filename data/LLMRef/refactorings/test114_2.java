public class test114 {

    @Test
    	void recordConditionEvaluations() {
    		recordConditionOutcomeAndEvaluation("a", this.condition1, this.outcome1);
    		recordConditionOutcomeAndEvaluation("a", this.condition2, this.outcome2);
    		recordConditionOutcomeAndEvaluation("b", this.condition3, this.outcome3);
    		Map<String, ConditionAndOutcomes> map = this.report.getConditionAndOutcomesBySource();
    		assertThat(map).hasSize(2);
    		Iterator<ConditionAndOutcome> iterator = map.get("a").iterator();
    		ConditionAndOutcome conditionAndOutcome = iterator.next();
    		assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition1);
    		assertThat(conditionAndOutcome.getOutcome()).isEqualTo(this.outcome1);
    		conditionAndOutcome = iterator.next();
    		assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition2);
    		assertThat(conditionAndOutcome.getOutcome()).isEqualTo(this.outcome2);
    		assertThat(iterator.hasNext()).isFalse();
    		iterator = map.get("b").iterator();
    		conditionAndOutcome = iterator.next();
    		assertThat(conditionAndOutcome.getCondition()).isEqualTo(this.condition3);
    		assertThat(conditionAndOutcome.getOutcome()).isEqualTo(this.outcome3);
    		assertThat(iterator.hasNext()).isFalse();
    	}

	private void recordConditionOutcomeAndEvaluation(String source, Condition condition, ConditionOutcome outcome) {
		this.report.recordConditionEvaluation(source, condition, outcome);
	}
}
