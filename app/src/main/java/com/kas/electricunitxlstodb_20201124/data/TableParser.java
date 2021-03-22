package com.kas.electricunitxlstodb_20201124.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TableParser {
    List<List<String>> parseTable(InputStream inputStream) throws IOException;

}
