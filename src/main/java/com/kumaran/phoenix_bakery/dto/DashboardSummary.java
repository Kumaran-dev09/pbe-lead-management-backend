package com.kumaran.phoenix_bakery.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardSummary {

    private long todayLeads;
    private long weeklyLeads;
    private long monthlyLeads;
    private long totalLeads;
    private Map<String, Long> companyStats = new LinkedHashMap<>();
    private Map<String, Long> leadTypeStats = new LinkedHashMap<>();
    private Map<String, Long> workerStats = new LinkedHashMap<>();
    private List<LeadResponse> recentLeads;

    public long getTodayLeads() { return todayLeads; }
    public void setTodayLeads(long todayLeads) { this.todayLeads = todayLeads; }
    public long getWeeklyLeads() { return weeklyLeads; }
    public void setWeeklyLeads(long weeklyLeads) { this.weeklyLeads = weeklyLeads; }
    public long getMonthlyLeads() { return monthlyLeads; }
    public void setMonthlyLeads(long monthlyLeads) { this.monthlyLeads = monthlyLeads; }
    public long getTotalLeads() { return totalLeads; }
    public void setTotalLeads(long totalLeads) { this.totalLeads = totalLeads; }
    public Map<String, Long> getCompanyStats() { return companyStats; }
    public void setCompanyStats(Map<String, Long> companyStats) { this.companyStats = companyStats; }
    public Map<String, Long> getLeadTypeStats() { return leadTypeStats; }
    public void setLeadTypeStats(Map<String, Long> leadTypeStats) { this.leadTypeStats = leadTypeStats; }
    public Map<String, Long> getWorkerStats() { return workerStats; }
    public void setWorkerStats(Map<String, Long> workerStats) { this.workerStats = workerStats; }
    public List<LeadResponse> getRecentLeads() { return recentLeads; }
    public void setRecentLeads(List<LeadResponse> recentLeads) { this.recentLeads = recentLeads; }
}
