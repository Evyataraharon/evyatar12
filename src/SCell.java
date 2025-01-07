



// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    private int order; // הוספתי משתנה order כדי לאחסן את סדר התאים

    public SCell(String s) {
        // Add your code here
        setData(s); // קריאה לפונקציה setData כדי להגדיר את הערך של התא
    }

    @Override
    public int getOrder() {
        // Add your code here
        return 0; // החזרת 0 כפי שנדרש בהערות
    }

    @Override
    public String toString() {
        return getData(); // מחזיר את הערך של התא (שורה)
    }

    @Override
    public void setData(String s) {
        // Add your code here
        line = s; // שומר את הערך שהתקבל במשתנה line
        // אם יש צורך בתיקול או עיבוד נוסף, אפשר להוסיף כאן
        // למשל: line = s.trim(); להסרת רווחים מיותרים
    }

    @Override
    public String getData() {
        return line; // מחזיר את הערך ששמור בשדה line
    }

    @Override
    public int getType() {
        return 0; // החזרת 0 כפי שנדרש בהערות
    }

    @Override
    public void setType(int t) {
        type = t; // מגדיר את סוג התא
    }

    @Override
    public void setOrder(int t) {
        // Add your code here
        order = t; // מגדיר את סדר התא (למשל אם יש צורך בסדר בתאים)
    }
}
