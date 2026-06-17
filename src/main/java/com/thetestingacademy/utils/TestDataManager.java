package com.thetestingacademy.utils;

import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.model.DataModel;

import java.util.*;

public class TestDataManager {

    private static TestDataManager instance;
    private ExcelReader excelReader;

    private final Map<String, Queue<DataModel>> suiteDataMap = new HashMap<>();

    private static final ThreadLocal<String> currentSuite = new ThreadLocal<>();
    private static final ThreadLocal<String> currentFlow = new ThreadLocal<>();

    private TestDataManager() {
        excelReader = new ExcelReader("Form_Fill_TestData.xlsx", "Sheet1");
        loadAllData();
    }

    public static TestDataManager getInstance() {
        if (instance == null) {
            instance = new TestDataManager();
        }
        return instance;
    }

    // =====================================================
    // 🔥 SINGLE NORMALIZATION METHOD (ONLY FIX POINT)
    // =====================================================
    private String key(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }

    // =====================================================
    // LOAD DATA
    // =====================================================
    private void loadAllData() {

        List<DataModel> allData = excelReader.getAllData();

        if (allData == null || allData.isEmpty()) {
            throw new RuntimeException("❌ Excel returned empty data");
        }

        Map<String, List<DataModel>> grouped = new HashMap<>();

        for (DataModel data : allData) {

            if (data.getSuiteType() == null || data.getSuiteType().trim().isEmpty()) {
                continue;
            }

            String suiteKey = key(data.getSuiteType());

            grouped.computeIfAbsent(suiteKey, k -> new ArrayList<>())
                    .add(data);
        }

        for (Map.Entry<String, List<DataModel>> entry : grouped.entrySet()) {
            Collections.shuffle(entry.getValue());
            suiteDataMap.put(entry.getKey(), new LinkedList<>(entry.getValue()));
        }
    }

    // =====================================================
    // INIT (FIXED)
    // =====================================================
    public void initForSuite(SuiteType suiteType, FlowType flowType) {

        String suiteKey = key(suiteType.name());
        String flowKey = key(flowType.getExcelValue());

        currentSuite.set(suiteKey);
        currentFlow.set(flowKey);

        if (!suiteDataMap.containsKey(suiteKey)) {
            throw new RuntimeException("❌ No data for suite: " + suiteKey);
        }
    }

    // =====================================================
    // GET DATA (UNCHANGED LOGIC)
    // =====================================================
    public synchronized DataModel getNextData() {

        String suite = currentSuite.get();
        String flow = currentFlow.get();

        Queue<DataModel> queue = suiteDataMap.get(suite);

        if (queue == null || queue.isEmpty()) {
            throw new RuntimeException("❌ Data exhausted for suite: " + suite);
        }

        int size = queue.size();
        List<DataModel> skipped = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            DataModel data = queue.poll();
            if (data == null) continue;

            String dataFlow = key(data.getFlowType());

            if (suite.equals("SMOKE") || suite.equals("SANITY")) {
                queue.addAll(skipped);
                return data;
            }

            if (dataFlow.equals(flow)) {
                queue.addAll(skipped);
                return data;
            }

            skipped.add(data);
        }

        queue.addAll(skipped);

        throw new RuntimeException(
                "❌ No matching flow data for: " + flow + " in suite: " + suite
        );
    }

    public int remainingDataCount() {
        String suite = currentSuite.get();
        Queue<DataModel> queue = suiteDataMap.get(suite);
        return queue == null ? 0 : queue.size();
    }

    public void resetSuite() {
        suiteDataMap.clear();
        loadAllData();
    }
}