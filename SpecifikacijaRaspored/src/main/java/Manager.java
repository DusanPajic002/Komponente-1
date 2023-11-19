public class Manager {
    private static RasporedAC obj;

    public static RasporedAC getSpecRasporedImpl(){
        return obj;
    }

    public static void setObj(RasporedAC obj) {
        Manager.obj = obj;
    }
}
