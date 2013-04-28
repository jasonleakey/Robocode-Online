package edu.utd.service;

import java.util.List;

import edu.utd.model.Summary;

public interface SummaryService {

	public void addSummary(Summary summary);
    public List<Summary> listSummary();
    public void removeSummary(String id);
    
}
