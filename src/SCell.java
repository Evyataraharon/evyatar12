
public class SCell implements Cell {
    private String line;
    private int type;
    private int order; // ׳”׳•׳¡׳₪׳×׳™ ׳׳©׳×׳ ׳” order ׳›׳“׳™ ׳׳׳—׳¡׳ ׳׳× ׳¡׳“׳¨ ׳”׳×׳׳™׳

    public SCell(String s) {
        // Add your code here
        setData(s); // ׳§׳¨׳™׳׳” ׳׳₪׳•׳ ׳§׳¦׳™׳” setData ׳›׳“׳™ ׳׳”׳’׳“׳™׳¨ ׳׳× ׳”׳¢׳¨׳ ׳©׳ ׳”׳×׳
    }

    @Override
    public int getOrder() {
        // Add your code here
        return 0; // ׳”׳—׳–׳¨׳× 0 ׳›׳₪׳™ ׳©׳ ׳“׳¨׳© ׳‘׳”׳¢׳¨׳•׳×
    }

    @Override
    public String toString() {
        return getData(); // ׳׳—׳–׳™׳¨ ׳׳× ׳”׳¢׳¨׳ ׳©׳ ׳”׳×׳ (׳©׳•׳¨׳”)
    }

    @Override
    public void setData(String s) {
        // Add your code here
        line = s; // ׳©׳•׳׳¨ ׳׳× ׳”׳¢׳¨׳ ׳©׳”׳×׳§׳‘׳ ׳‘׳׳©׳×׳ ׳” line
        // ׳׳ ׳™׳© ׳¦׳•׳¨׳ ׳‘׳×׳™׳§׳•׳ ׳׳• ׳¢׳™׳‘׳•׳“ ׳ ׳•׳¡׳£, ׳׳₪׳©׳¨ ׳׳”׳•׳¡׳™׳£ ׳›׳׳
        // ׳׳׳©׳: line = s.trim(); ׳׳”׳¡׳¨׳× ׳¨׳•׳•׳—׳™׳ ׳׳™׳•׳×׳¨׳™׳
    }

    @Override
    public String getData() {
        return line; // ׳׳—׳–׳™׳¨ ׳׳× ׳”׳¢׳¨׳ ׳©׳©׳׳•׳¨ ׳‘׳©׳“׳” line
    }

    @Override
    public int getType() {
        return 0; // ׳”׳—׳–׳¨׳× 0 ׳›׳₪׳™ ׳©׳ ׳“׳¨׳© ׳‘׳”׳¢׳¨׳•׳×
    }

    @Override
    public void setType(int t) {
        type = t; // ׳׳’׳“׳™׳¨ ׳׳× ׳¡׳•׳’ ׳”׳×׳
    }

    @Override
    public void setOrder(int t) {
        // Add your code here
        order = t; // ׳׳’׳“׳™׳¨ ׳׳× ׳¡׳“׳¨ ׳”׳×׳ (׳׳׳©׳ ׳׳ ׳™׳© ׳¦׳•׳¨׳ ׳‘׳¡׳“׳¨ ׳‘׳×׳׳™׳)
    }
}
