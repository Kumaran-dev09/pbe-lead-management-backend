package com.kumaran.phoenix_bakery;
import com.kumaran.phoenix_bakery.model.Lead;
import com.kumaran.phoenix_bakery.model.Worker; // <-- unused
import java.util.List;

public interface BackupService {

    void createAutomaticBackup(Lead lead);

    byte[] createSqlBackup();

    byte[] createLeadsExcelBackup();

    byte[] createWorkersExcelBackup();

    byte[] createPdfReport(List<Lead> leads);

    void createDailySqlBackup();

    List<String> listBackups();

}