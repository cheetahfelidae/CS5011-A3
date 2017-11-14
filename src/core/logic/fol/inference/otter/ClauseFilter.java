package core.logic.fol.inference.otter;

import java.util.Set;

import core.logic.fol.kb.data.Clause;

/**
 * @author Ciaran O'Reilly
 * 
 */
public interface ClauseFilter {
	Set<Clause> filter(Set<Clause> clauses);
}
