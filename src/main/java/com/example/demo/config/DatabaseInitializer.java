package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        addColumnIfNotExists("task", "image_path", "VARCHAR(255)");
        addColumnIfNotExists("task", "task_description", "TEXT");
    }

    private void addColumnIfNotExists(String tableName, String columnName, String columnDefinition) {
        try {
            String checkSql = "PRAGMA table_info(" + tableName + ")";
            List<Map<String, Object>> columns = jdbcTemplate.queryForList(checkSql);
            
            boolean columnExists = columns.stream()
                .anyMatch(col -> columnName.equalsIgnoreCase((String) col.get("name")));
            
            if (!columnExists) {
                String alterSql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnDefinition;
                jdbcTemplate.execute(alterSql);
                System.out.println("Added column " + columnName + " to table " + tableName);
            }
        } catch (Exception e) {
            System.err.println("Error checking/adding column " + columnName + ": " + e.getMessage());
        }
    }
}
