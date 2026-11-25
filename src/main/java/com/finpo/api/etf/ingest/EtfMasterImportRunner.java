package com.finpo.api.etf.ingest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class EtfMasterImportRunner implements CommandLineRunner {
    private final EtfMasterImporter importer;

    @Override
    public void run(String... args) throws Exception {
        importer.importFrom(Path.of("data/etf_master_20261125.csv"));
    }
}
