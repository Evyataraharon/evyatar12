public class SCell implements Cell {
    private String content;
    private int type;
    private int order;

    public SCell(String s) {
        content = "";
        type = Ex2Utils.NUMBER;
        order = 0;
        setData(s);
    }

    @Override
    public void setData(String s) {
        if (s == null) {
            content = "";
            type = Ex2Utils.NUMBER;
            return;
        }

        content = s.trim();

        // ברירת מחדל - מספר רגיל
        type = Ex2Utils.NUMBER;

        // אם זו נוסחה
        if (content.startsWith("=")) {
            // Ex2Sheet יקבע בהמשך אם זו:
            // - נוסחה תקינה (FORM - כחול)
            // - שגיאת נוסחה (ERR_FORM_FORMAT - אדום)
            // - שגיאה מעגלית (ERR_CYCLE_FORM - אדום)
            type = Ex2Utils.FORM;
        }
    }

    @Override
    public String getData() {
        return content;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        // מקבל את הטיפוס הסופי מ-Ex2Sheet אחרי הערכת הנוסחה
        type = t;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int t) {
        order = t;
    }

    @Override
    public String toString() {
        return getData();
    }
}